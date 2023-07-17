package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.entitys.Carriage;
import com.danyliuk.testjob.railwaydata.entitys.CarriageType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface CarriageRepository  extends CrudRepository<Carriage, Integer> {
    Iterable<Carriage> findByTypeCar(CarriageType typeCar);

//    @Transactional
//    @Modifying
//    @Query("update Carriage c set c.numCar = :numCar where c = :typeCar")
//    void updateNumCar(@Param("typeCar") CarriageType typeCar, @Param("numCar") int numCar);

}
