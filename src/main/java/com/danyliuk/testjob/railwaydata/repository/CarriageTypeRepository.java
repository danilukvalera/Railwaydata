package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.entitys.CarriageType;
import org.springframework.data.repository.CrudRepository;

public interface CarriageTypeRepository extends CrudRepository<CarriageType, Integer> {
    CarriageType findByNameCar(String typeCar);
}
