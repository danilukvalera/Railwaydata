package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.tables.Carriage;
import com.danyliuk.testjob.railwaydata.tables.CarriageType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarriageRepository  extends CrudRepository<Carriage, Integer> {
    List<Optional<Carriage>> findByTypeId(int typeId);
}
