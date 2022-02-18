package org.homedirectory.meta_models.examples;

import org.homedirectory.meta_models.MetaModel;

public final class InsuranceMetaModel extends MetaModel {
    private static final String cost_ = "cost";
    private static final String expirationDate_ = "expirationDate";
    
    public final String cost;
    public final String expirationDate;

    public InsuranceMetaModel(String context) {
        super(context);
        this.cost = joinContext(cost_);
        this.expirationDate = joinContext(expirationDate_);
    }

    public InsuranceMetaModel() {
        this("");
    }
}
