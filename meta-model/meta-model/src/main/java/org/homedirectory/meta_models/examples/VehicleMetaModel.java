package org.homedirectory.meta_models.examples;

import org.homedirectory.meta_models.MetaModel;

public final class VehicleMetaModel extends MetaModel {
    private static final String color_ = "color";
    private static final String insurance_ = "insurance";

    public final String color;
    public final InsuranceMetaModel insurance;

    public VehicleMetaModel(String context) {
        super(context);
        this.color = joinContext(color_);
        this.insurance = new InsuranceMetaModel(joinContext(insurance_));
    }

    public VehicleMetaModel() {
        this("");
    }
}
