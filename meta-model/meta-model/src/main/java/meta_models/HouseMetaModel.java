package main.java.meta_models;

public final class HouseMetaModel extends MetaModel {
    private static final String area_ = "area";
    private static final String insurance_ = "insurance";

    public final String area;
    public final InsuranceMetaModel insurance;

    public HouseMetaModel(String context) {
        super(context);
        this.area = joinContext(area_);
        this.insurance = new InsuranceMetaModel(joinContext(insurance_));
    }

    public HouseMetaModel() {
        this("");
    }
}
