package main.java.meta_models;

public final class InsuranceMetaModel extends MetaModel {
    private static final String cost_ = "cost";
    private static final String expirationDate_ = "expirationDate";
    
    public final String cost;
    public final String expirationDate;

    protected InsuranceMetaModel(String context) {
        super(context);
        this.cost = joinContext(cost_);
        this.expirationDate = joinContext(expirationDate_);
    }

    protected InsuranceMetaModel() {
        this("");
    }
}
