package com.danyliuk.testjob.railwaydata.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.RequestParam;

@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int shareCar;
    private int economCar;
    private int compartmentCar;
    private int superCompartmentCar;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShareCar() {
        return shareCar;
    }

    public void setShareCar(int shareCar) {
        this.shareCar = shareCar;
    }

    public int getEconomCar() {
        return economCar;
    }

    public void setEconomCar(int economCar) {
        this.economCar = economCar;
    }

    public int getCompartmentCar() {
        return compartmentCar;
    }

    public void setCompartmentCar(int compartmentCar) {
        this.compartmentCar = compartmentCar;
    }

    public int getSuperCompartmentCar() {
        return superCompartmentCar;
    }

    public void setSuperCompartmentCar(int superCompartmentCar) {
        this.superCompartmentCar = superCompartmentCar;
    }
}
