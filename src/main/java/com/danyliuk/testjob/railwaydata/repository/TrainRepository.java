package com.danyliuk.testjob.railwaydata.repository;

import com.danyliuk.testjob.railwaydata.entitys.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, Integer> {
}
