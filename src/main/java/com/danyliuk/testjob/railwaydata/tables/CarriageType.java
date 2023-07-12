package com.danyliuk.testjob.railwaydata.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarriageType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String typeCar;
    private int numberSeats;

    public CarriageType() {
    }

    public CarriageType(String typeCar, int numberSeats) {
        this.typeCar = typeCar;
        this.numberSeats = numberSeats;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return typeCar;
    }

    public void setType(String typeCar) {
        this.typeCar = typeCar;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

}
