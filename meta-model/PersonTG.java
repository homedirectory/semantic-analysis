@KeyType(DynamicEntityKey.class)
@KeyTitle(value = "Email", desc = "Uniquely identifies a person.")
@DescTitle(value = "Full Name", desc = "Person's full name - e.g. the first name followed by the middle initial followed by the surname.")
@MapEntityTo
@CompanionObject(PersonCo.class)
@DisplayDescription
@GenerateMetaModel
public class Person extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = getEntityTitleAndDesc(Person.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Name")
    private String name;

    @IsProperty
    @MapTo
    @Title(value = "Vehicle")
    private Vehicle vehicle;

    @IsProperty
    @MapTo
    @Title(value = "House")
    private House house;
    
    @IsProperty
    @MapTo
    @DateOnly
    @Title(value = "Birth date", desc = "Extended_description")
    private Date birthDate;

    @IsProperty
    @Readonly
    @Calculated
    @Title(value = "Full Name")
    private String desc;
    protected static final ExpressionModel desc_ = 
            expr().concat().prop("name").with().val(" ")
            .with().prop("surname").end().model();

    @IsProperty
    @CompositeKeyMember(1)
    @MapTo
    @Title(value = "Email", desc = "Uniquely identifies a person.")
    @BeforeChange(@Handler(EmailValidator.class))
    private String email;

    @IsProperty
    @Unique
    @MapTo
    @Title(value = "User", desc = "An application user associated with the current person.")
    @SkipEntityExistsValidation(skipActiveOnly = true)
    private User user;

    public String getDesc() {
        return this.desc;
    }

    @Override
    @Observable
    public Person setDesc(String desc) {
        this.desc = desc;
        return this;
    }
    
    public String getName() {
        return this.name;
    }

    @Observable
    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return this.surname;
    }

    @Observable
    public Person setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Observable
    public Person setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Observable
    public Person setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Observable
    public Person setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
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
