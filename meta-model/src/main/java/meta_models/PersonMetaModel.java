package main.java.meta_models;

public final class PersonMetaModel extends MetaModel {
    private static final String name_ = "name";
    private static final String vehicle_ = "vehicle";
    private static final String age_ = "age";
    private static final String house_ = "house";
    
    public final String name;
    public final VehicleMetaModel vehicle;
    public final String age;
    public final HouseMetaModel house;

    protected PersonMetaModel(String context) {
        super(context);
        this.vehicle = new VehicleMetaModel(joinContext(vehicle_));
        this.name = joinContext(name_);
        this.age = joinContext(age_);
        this.house = new HouseMetaModel(joinContext(house_));
    }

    protected PersonMetaModel() {
        this("");
    }
}
