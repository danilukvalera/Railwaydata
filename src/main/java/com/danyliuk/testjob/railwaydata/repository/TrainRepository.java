package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.tables.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Integer> {
}
