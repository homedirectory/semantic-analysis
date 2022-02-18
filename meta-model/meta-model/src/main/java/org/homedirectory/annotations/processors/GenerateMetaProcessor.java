package org.homedirectory.annotations.processors;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
@SupportedAnnotationTypes("org.homedirectory.annotations.GenerateMeta")
@SupportedSourceVersion(SourceVersion.RELEASE_16)
public class GenerateMetaProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation: annotations) {
            final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
                for (Element element: annotatedElements) {
                    final String qualifiedName = ((TypeElement) element).getQualifiedName().toString();

                    List<? extends Element> allEnclosedElements = getAllEnclosedElements((TypeElement) element);
                    final List<VariableElement> fieldElements = ElementFilter.fieldsIn(allEnclosedElements);

                    final Map<String, String> fieldTypeMap = fieldElements.stream().collect(Collectors.toMap(
                            el -> el.getSimpleName().toString(), 
                            el -> el.asType().toString()
                            ));

                    // test
//                    String testText = "";
//                    try {
//                        writeTest(testText, "test.txt");
//                    } catch (IOException e) {
//                        System.out.println("IOException!!! " + e.getMessage());
//                    }
                    
                    final String packageName = "org.homedirectory.meta_models";
                    final String className = qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
                    
                    final List<String> annotatedElementsNames = annotatedElements.stream().map(el -> el.toString()).toList();
                    try {
                        writeMetaModel(packageName, className, fieldTypeMap, annotatedElementsNames);
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

    private void writeMetaModel(final String packageName, final String className, final Map<String, String> fieldTypeMap, final List<String> annotatedElementsNames) throws IOException {
        List<FieldSpec> fields = new ArrayList<>();
        for (var entry : fieldTypeMap.entrySet()) {
            final String fieldName = entry.getKey();
            final String fieldType = entry.getValue();
            final String fieldTypeSimpleName = fieldType.substring(fieldType.lastIndexOf(".") + 1);

            // private static final String NAME_ = "NAME";
            fields.add(FieldSpec.builder(String.class, fieldName + "_")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", fieldName)
                    .build());

            if (annotatedElementsNames.contains(fieldType)) {
                ClassName fieldTypeMetaModelName = ClassName.get(packageName, fieldTypeSimpleName + "MetaModel");
                fields.add(FieldSpec.builder(fieldTypeMetaModelName, fieldName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .build());
            } else {
                fields.add(FieldSpec.builder(String.class, fieldName)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .build());
            }
        }

        /*
        public NAMEMetaModel(String context) {
            super(context);
            // for each field
            // if field type is a declared one and also annotated (with GenerateMeta)
            this.FIELDNAME = new FIELDTYPEMetaModel(joinContext(FIELDNAME_));
            // else
            this.FIELDNAME = joinContext(FIELDNAME_);
        }
        */
        com.squareup.javapoet.MethodSpec.Builder constructorBuilder =  MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "context")
                .addStatement("super(context)");
//        List<CodeBlock> constructorStatements = new ArrayList<>();
        com.squareup.javapoet.CodeBlock.Builder constructorStatementsBuilder = CodeBlock.builder();
        
        for (var entry : fieldTypeMap.entrySet()) {
            final String fieldName = entry.getKey();
            final String fieldType = entry.getValue();
            final String fieldTypeSimpleName = fieldType.substring(fieldType.lastIndexOf(".") + 1);
            
            if (annotatedElementsNames.contains(fieldType)) {
                ClassName fieldTypeMetaModelName = ClassName.get(packageName, fieldTypeSimpleName + "MetaModel");
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
                
        // empty constructor
        /*
        public NAMEMetaModel() {
            this("");
        }
        */
        MethodSpec emptyConstructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("this(\"\")")
                .build();

        final ClassName metaModelSuperClass = ClassName.get(packageName, "MetaModel");
        final String metaModelName = className + "MetaModel";

        // public final class NAMEMetaModel extends MetaModel
        TypeSpec metaModel = TypeSpec.classBuilder(metaModelName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(metaModelSuperClass)
                .addFields(fields)
                .addMethod(constructorBuilder.build())
                .addMethod(emptyConstructor)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, metaModel).build();
        javaFile.writeTo(processingEnv.getFiler());
    }
    
    private void writeTest(String text, String filename) throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filename));
        printWriter.println(text);
        printWriter.close();
    }
}


