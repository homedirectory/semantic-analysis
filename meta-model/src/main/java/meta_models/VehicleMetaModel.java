package main.java.meta_models;

public final class VehicleMetaModel extends MetaModel {
    private static final String color_ = "color";
    private static final String insurance_ = "insurance";

    public final String color;
    public final InsuranceMetaModel insurance;

    protected VehicleMetaModel(String context) {
        super(context);
        this.color = joinContext(color_);
        this.insurance = new InsuranceMetaModel(joinContext(insurance_));
    }

    protected VehicleMetaModel() {
        this("");
    }
}
