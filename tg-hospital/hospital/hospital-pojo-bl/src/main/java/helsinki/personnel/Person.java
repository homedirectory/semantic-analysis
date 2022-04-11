package helsinki.personnel;

import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import helsinki.commodities.House;
import helsinki.commodities.Vehicle;
import helsinki.security.tokens.persistent.Person_CanModify_user_Token;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.SkipEntityExistsValidation;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.Unique;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.property.validator.EmailValidator;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.utils.Pair;

/**
 * An entity type representing a person.
 *
 * @author Generated
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle(value = "Email", desc = "Uniquely identifies a person.")
@DescTitle(value = "Full Name", desc = "Person's full name - e.g. the first name followed by the middle initial followed by the surname.")
@MapEntityTo
@CompanionObject(PersonCo.class)
@DescRequired
@DisplayDescription
public class Person extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = getEntityTitleAndDesc(Person.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
    @CompositeKeyMember(1)
    @MapTo
    @Title(value = "Email", desc = "Uniquely identifies a person.")
    @BeforeChange(@Handler(EmailValidator.class))
    private String email;
    
    @IsProperty
    @MapTo
    @Title(value = "Name")
    private String name;
    
    @IsProperty
    @MapTo
    @Title(value = "Age")
    private int age;
    
    @IsProperty
    @MapTo
    @Title(value = "House", desc = "A house belonging to this person")
    private House house;
    
    @IsProperty
    @MapTo
    @Title(value = "Vehicle", desc = "A vehicle belonging to this person")
    private Vehicle vehicle;

    @Observable
    public Person setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Observable
    public Person setHouse(final House house) {
        this.house = house;
        return this;
    }

    public House getHouse() {
        return house;
    }

    @Observable
    public Person setAge(final int age) {
        this.age = age;
        return this;
    }

    public int getAge() {
        return age;
    }
    
    @Observable
    public Person setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @IsProperty
    @Unique
    @MapTo
    @Title(value = "User", desc = "An application user associated with the current person.")
    @SkipEntityExistsValidation(skipActiveOnly = true)
    private User user;

    @Override
    @Observable
    public Person setDesc(final String desc) {
        return (Person) super.setDesc(desc);
    }

    @Observable
    public Person setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Override
    @Observable
    public Person setActive(final boolean active) {
        super.setActive(active);
        return this;
    }

    @Observable
    @Authorise(Person_CanModify_user_Token.class)
    public Person setUser(final User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

    /** A convenient method to identify whether the current person instance is an application user. */
    public boolean isAUser() {
        return getUser() != null;
    }

}