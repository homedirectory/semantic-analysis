package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.meta_models.MetaModels;

class MetaModelsTest {

    @Test
    void test() {
        String personVehicleInsuranceCost = MetaModels.Person.vehicle.insurance.cost;
        assertEquals("vehicle.insurance.cost", personVehicleInsuranceCost);

        String personHouseInsuranceCost = MetaModels.Person.house.insurance.cost;
        assertEquals("house.insurance.cost", personHouseInsuranceCost);

        String personHouse = MetaModels.Person.house.toString();
        assertEquals("house", personHouse);
    }

}
