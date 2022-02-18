package org.homedirectory;

import static org.junit.Assert.assertEquals;

import org.homedirectory.meta_models.MetaModels;
import org.junit.Test;

public class MetaModelsTest {

    @Test
    public void test() {
        String personVehicleInsuranceCost = MetaModels.Person.vehicle.insurance.cost;
        assertEquals("vehicle.insurance.cost", personVehicleInsuranceCost);

        String personHouseInsuranceCost = MetaModels.Person.house.insurance.cost;
        assertEquals("house.insurance.cost", personHouseInsuranceCost);

        String personHouse = MetaModels.Person.house.toString();
        assertEquals("house", personHouse);
    }

}
