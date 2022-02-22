package org.homedirectory.annotations.processors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.homedirectory.annotations.GenerateMetaModel")
@SupportedSourceVersion(SourceVersion.RELEASE_16)
public class MetaModelProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "org.homedirectory.meta_models";
    private static final String META_MODEL_SUPERCLASS_SIMPLE_NAME = "MetaModel";
    private static final String META_MODEL_NAME_SUFFIX = "MetaModel";
    private static final String INDENT = "    ";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation: annotations) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            final List<String> annotatedElementsNames = annotatedElements.stream().map(el -> el.asType().toString()).toList();
                    
                for (Element element: annotatedElements) {
                    final String simpleName = element.getSimpleName().toString();

                    final List<? extends Element> allEnclosedElements = getAllEnclosedElements((TypeElement) element);
                    final List<VariableElement> fieldElements = ElementFilter.fieldsIn(allEnclosedElements);

                    final Map<String, String> fieldNameTypeMap = fieldElements.stream().collect(Collectors.toMap(
                            el -> el.getSimpleName().toString(), 
                            el -> el.asType().toString() // using TypeMirror here directly instead of turning it into Element
                            ));

                    try {
                        writeMetaModel(simpleName, fieldNameTypeMap, annotatedElementsNames);
                    } catch (IOException e) {
                        System.out.println("IOException!!! " + e.getMessage());
                    }
                }
        }
        
        return true;
    }
    
    private List<? extends Element> getAllEnclosedElements(TypeElement element) {
        final String objectClassName = "java.lang.Object";
        List<Element> enclosedElements = new ArrayList<>();
        // add enclosed elements of this class
        enclosedElements.addAll(element.getEnclosedElements());
        
        // recurse into superclass
        // TODO handle static fields?
        TypeElement superClass = (TypeElement) ((DeclaredType) element.getSuperclass()).asElement();
        if (!superClass.getQualifiedName().toString().equals(objectClassName)) {
            enclosedElements.addAll(getAllEnclosedElements((TypeElement) superClass));
        }
        
        return enclosedElements;
    }

    private void writeMetaModel(final String className, final Map<String, String> fieldNameTypeMap, final List<String> annotatedElementsNames) throws IOException {
        // == Class members ==

        List<FieldSpec> fields = new ArrayList<>();

        for (var entry : fieldNameTypeMap.entrySet()) {
            final String fieldName = entry.getKey();
            final String fieldType = entry.getValue();

            // private static final String NAME_ = "NAME";
            fields.add(FieldSpec.builder(String.class, fieldName + "_")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", fieldName)
                    .build());

            // if this field's type is also annotated, then the generated field must refer to another meta-model
            if (annotatedElementsNames.contains(fieldType)) {
                final String fieldTypeSimpleName = fieldType.substring(fieldType.lastIndexOf(".") + 1);
                ClassName fieldTypeMetaModelName = ClassName.get(PACKAGE_NAME, fieldTypeSimpleName + META_MODEL_NAME_SUFFIX);
                fields.add(FieldSpec.builder(fieldTypeMetaModelName, fieldName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .build());
            } else {
                fields.add(FieldSpec.builder(String.class, fieldName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .build());
            }
        }

        // == Constructors ==

        /*
        public NAMEMetaModel(String context) {
            super(context);
               for each field
                   if field's type is also annotated
                       this.FIELDNAME = new FIELDTYPENAMEMetaModel(joinContext(FIELDNAME_));
                   else
                       this.FIELDNAME = joinContext(FIELDNAME_);
        }
        */
        com.squareup.javapoet.MethodSpec.Builder constructorBuilder =  MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "context")
                .addStatement("super(context)");

        com.squareup.javapoet.CodeBlock.Builder constructorStatementsBuilder = CodeBlock.builder();
        
        for (var entry : fieldNameTypeMap.entrySet()) {
            final String fieldName = entry.getKey();
            final String fieldType = entry.getValue();
            
            if (annotatedElementsNames.contains(fieldType)) {
                final String fieldTypeSimpleName = fieldType.substring(fieldType.lastIndexOf(".") + 1);
                ClassName fieldTypeMetaModelName = ClassName.get(PACKAGE_NAME, fieldTypeSimpleName + META_MODEL_NAME_SUFFIX);
                constructorStatementsBuilder = constructorStatementsBuilder.addStatement(
                        "this.$L = new $T(joinContext($L_))", 
                        fieldName, fieldTypeMetaModelName, fieldName 
                        );
            } else {
                constructorStatementsBuilder = constructorStatementsBuilder.addStatement(
                        "this.$L = joinContext($L_)", 
                        fieldName, fieldName 
                        );
            }
        }

        constructorBuilder = constructorBuilder.addCode(constructorStatementsBuilder.build());
                
        // The empty constructor

        /*
        public NAMEMetaModel() {
            this("");
        }
        */
        MethodSpec emptyConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this(\"\")")
                .build();

        final ClassName metaModelSuperClassName = ClassName.get(PACKAGE_NAME, META_MODEL_SUPERCLASS_SIMPLE_NAME);
        final String metaModelName = className + META_MODEL_NAME_SUFFIX;

        // public final class NAMEMetaModel extends MetaModel
        TypeSpec metaModel = TypeSpec.classBuilder(metaModelName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(metaModelSuperClassName)
                .addFields(fields)
                .addMethod(constructorBuilder.build())
                .addMethod(emptyConstructor)
                .build();

        // == Write to file ==

        JavaFile javaFile = JavaFile.builder(PACKAGE_NAME, metaModel).indent(INDENT).build();
        javaFile.writeTo(processingEnv.getFiler());
    }
}


