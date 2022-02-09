package main.java;

import main.java.meta_models.MetaModels;

public class Main {
    public static void main(String[] args) {
        String personVehicleInsuranceCost = MetaModels.Person.vehicle.insurance.cost;
        System.out.println(personVehicleInsuranceCost);

        String personHouseInsuranceCost = MetaModels.Person.house.insurance.cost;
        System.out.println(personHouseInsuranceCost);

        String personHouse = MetaModels.Person.house.toString();
        System.out.println(personHouse);
    }
}
