package com.danyliuk.testjob.railwaydata.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CarriageType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private int numberSeats;

    protected CarriageType() {
    }

    public CarriageType(String type, int numberSeats) {
        this.type = type;
        this.numberSeats = numberSeats;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    @Override
    public String toString(){
        return String.format(
                "Тип вагона[id=%d, Тип ='%s', Кол-во мест ='%d']",
                id, type, numberSeats);
    }
}
