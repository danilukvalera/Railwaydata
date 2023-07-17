package com.danyliuk.testjob.railwaydata.constants;

public class Constants {
    //------------------типы вагонов------------------
    public static final String TYPE_SHARED = "shared";
    public static final String TYPE_ECONOM = "econom";
    public static final String TYPE_COMP = "compartment";
    public static final String TYPE_SUPER_COMP = "super_compartment";
    public static final int INIT_NUM_CAR = 10;

    //Создать строку информации о поезде
    public static String createInfoTrain(String name, int share, int econom, int compartment, int superCompartment) {
        String builder = "\n" +
                "-------------------------------------------------------------------------" +
                "\n" +
                String.format("Название поезда: %s", name) +
                "\n" +
                String.format("Общих вагонов = %d", share) +
                "\n" +
                String.format("Плацкартных вагонов = %d", econom) +
                "\n" +
                String.format("Купейных вагонов = %d", compartment) +
                "\n" +
                String.format("СВ вагонов = %d", superCompartment) +
                "\n";
        return builder;
    }
}
