package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.tables.CarriageType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarriageTypeRepository extends CrudRepository<CarriageType, Integer> {
//    List<CarriageType> findByType(String type);
//    CarriageType findById(int id);
}
