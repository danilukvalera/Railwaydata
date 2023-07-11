package com.danyliuk.testjob.railwaydata.controlers;

public class SqlCommands {
//------------------типы вагонов------------------
    public static final String TYPE_SHARED = "shared";
    public static final String TYPE_ECONOM = "econom";
    public static final String TYPE_COMP = "compartment";
    public static final String TYPE_SUPER_COMP = "super_compartment";
    public static final int INIT_NUM_CAR = 10;

//------------------carriage_type-----------------
    public static final String REMOVE_TABLE_CARRIAGE_TYPE = "DROP TABLE IF EXISTS carriage_type;";
    public static final String CREATE_TABLE_CARRIAGE_TYPE =
"""
CREATE TABLE IF NOT EXISTS carriage_type
(
    id SERIAL PRIMARY KEY,
    type_car VARCHAR(30) NOT NULL,
    number_seats INTEGER NOT NULL
);
""";

    public static String getSqlInsertToCarriageType(String type, int seats) {
        String res = String.format(
 """
 INSERT INTO carriage_type(
    type_car, number_seats)
    VALUES ('%s', %d);
 """, type, seats);
        return res;
    }
//------------------carriage----------------------
    public static final String REMOVE_TABLE_CARRIAGE = "DROP TABLE IF EXISTS carriage;";
    public static final String CREATE_TABLE_CARRIAGE =
"""
CREATE TABLE IF NOT EXISTS carriage
(
    id SERIAL PRIMARY KEY,
    type_id INTEGER NOT NULL
);
""";

    public static String getSqlInsertToCarriage(String typeCar) {
        String res = String.format(
                """
INSERT INTO carriage (type_id)
VALUES((SELECT id FROM carriage_type WHERE type_car='%s'));
                """, typeCar);
        return res;
    }
//------------------train-------------------------
    public static final String REMOVE_TABLE_TRAIN = "DROP TABLE IF EXISTS train;";
    public static final String CREATE_TABLE_TRAIN =
            """
            CREATE TABLE IF NOT EXISTS train
            (
                id SERIAL PRIMARY KEY,
                name VARCHAR(30) NOT NULL,
                share_car INTEGER NOT NULL,
                econom_car INTEGER NOT NULL,
                compartment_car INTEGER NOT NULL,
                super_compartment_car INTEGER NOT NULL
            );
            """;

    public static String getSqlInsertToTrain(String name, int share, int econom, int compartment, int sCompartment) {
        if(share > INIT_NUM_CAR) share = INIT_NUM_CAR;
        if(econom > INIT_NUM_CAR) econom = INIT_NUM_CAR;
        if(compartment > INIT_NUM_CAR) compartment = INIT_NUM_CAR;
        if(sCompartment > INIT_NUM_CAR) sCompartment = INIT_NUM_CAR;

        String res = String.format(
"""
INSERT INTO train (name, share_car, econom_car, compartment_car, super_compartment_car)
VALUES('%s', %d, %d, %d, %d);
""", name, share, econom, compartment, sCompartment);
        return res;
    }

    public static final String GET_ALL_TRAINS = "SELECT * FROM train;";


}
