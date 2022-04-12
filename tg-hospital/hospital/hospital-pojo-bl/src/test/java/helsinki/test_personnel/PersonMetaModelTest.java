package helsinki.test_personnel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import helsinki.commodities.meta.VehicleMetaModel;
import helsinki.personnel.meta.PersonMetaModel;
import meta_models.MetaModels;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.processors.meta_model.MetaModelProcessor;
import ua.com.fielden.platform.reflection.Finder;


/**
 *
 * These tests cover the entity meta-model and its use for entity graph traversal in order to reference entity properties.
 *
 * @author TG Team
 *
 */
public class PersonMetaModelTest {
    
    private PersonMetaModel Person_ = MetaModels.Person;
    private VehicleMetaModel Vehicle_ = MetaModels.Vehicle;

    @Test
    public void general_property_name_correctness_test() {
        List<String> includedInheritedPropertiesNames = MetaModelProcessor.includedInheritedPropertiesNames();
        
        Class<?> metaModelClazz = Person_.getClass();
        Class<? extends AbstractEntity<?>> entityClazz = (Class<? extends AbstractEntity<?>>) Person_.getModelClass();

        List<Field> entityInheritedProperties = Finder.findRealProperties((Class<? extends AbstractEntity<?>>) entityClazz.getSuperclass()).stream()
                .filter(field -> includedInheritedPropertiesNames.contains(field.getName()))
                .toList();
        Set<Field> entityProperties = Arrays.asList(entityClazz.getDeclaredFields()).stream()
                .filter(field -> field.isAnnotationPresent(IsProperty.class))
                .collect(Collectors.toSet());
        entityProperties.addAll(entityInheritedProperties);

        entityProperties.forEach(prop -> {
            String propName = prop.getName();

            Method method = null;
            try {
                method = metaModelClazz.getDeclaredMethod(propName);
            } catch (Exception ex) {
                System.out.println(String.format("Class %s has no method %s", metaModelClazz.getSimpleName(), propName));
                return;
            }

            // PropertyMetaModel or EntityMetaModel
            Object metaModel = null;
            try {
                metaModel = method.invoke(Person_);
            } catch (Exception ex) {
                fail(String.format("Can't invoke %s.%s()\n", Person_.getClass().getSimpleName(), method.getName()) + ex.getMessage());
            }
            Class<?> propertyClazz = metaModel.getClass();

            Method toPathMethod = null;
            try {
                toPathMethod = propertyClazz.getMethod("toPath");
            } catch (Exception ex) {
                fail(String.format("Class %s has no method %s", propertyClazz, "toPath"));
            }

            String path = null;
            try {
                path = (String) toPathMethod.invoke(metaModel);
            } catch (Exception ex) {
                fail(String.format("Can't invoke %s.s()\n", Person_.getClass().getSimpleName(), "toPath") + ex.getMessage());
            }
            
            assertEquals(propName, path);
        });
    }
    
    @Test
    public void cyclic_property_reference_is_handled() {
        assertEquals("owner.age", Vehicle_.owner().age().toPath());
        assertEquals("vehicle.cost", Person_.vehicle().cost().toPath());
    }

}
