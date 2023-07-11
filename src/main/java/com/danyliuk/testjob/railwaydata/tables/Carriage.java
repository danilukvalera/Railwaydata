package com.danyliuk.testjob.railwaydata.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int typeId;

    public Integer getId() {
        return id;
    }

    public int getType() {
        return typeId;
    }

    public void setType(int typeId) {
        this.typeId = typeId;
    }

}
