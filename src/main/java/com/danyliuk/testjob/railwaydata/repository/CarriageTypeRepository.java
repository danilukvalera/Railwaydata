package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.tables.CarriageType;
import org.springframework.data.repository.CrudRepository;

public interface CarriageTypeRepository extends CrudRepository<CarriageType, Integer> {
}
