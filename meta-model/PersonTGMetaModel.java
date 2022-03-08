/**
 * Key: Email. Uniquely identifies a person.
 * Desc: Full Name. Person's full name - e.g. the first name followed by the middle initial followed by the surname.
 */
public final class PersonMetaModel extends MetaModel {
    private static final String name_ = "name";
    private static final String vehicle_ = "vehicle";
    private static final String email_ = "email";
    private static final String birthDate_ = "birthDate";
    private static final String desc_ = "desc";
    private static final String house_ = "house";
    
    /**
     * Name
     *
     * Required
     */
    public final String name;

    /**
     * Vehicle. A vehicle belonging to this person
     * {@link Vehicle}
     */
    public final VehicleMetaModel vehicle;

    /**
     * Birth date. Extended_description
     *
     * DateOnly
     */
    public final String birthDate;

    /**
     * Full Name
     *
     * ReadOnly
     * Calculated
     */
    public final String desc;

    /**
     * Email. Uniquely identifies a person.
     *
     * CompositeKeyMember(1)
     */
    public final String email;

    /**
     * House
     * {@link House}
     */
    public final HouseMetaModel house;

    public PersonMetaModel(String context) {
        super(context);
        this.vehicle = new VehicleMetaModel(joinContext(vehicle_));
        this.name = joinContext(name_);
        this.email = joinContext(email_);
        this.birthDate = joinContext(birthDate_);
        this.desc = joinContext(desc_);
        this.house = new HouseMetaModel(joinContext(house_));
    }

    public PersonMetaModel() {
        this("");
    }
}
