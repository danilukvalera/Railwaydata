package com.danyliuk.testjob.railwaydata;

import com.danyliuk.testjob.railwaydata.entitys.Carriage;
import com.danyliuk.testjob.railwaydata.entitys.CarriageType;
import com.danyliuk.testjob.railwaydata.entitys.Train;
import com.danyliuk.testjob.railwaydata.repository.CarriageRepository;
import com.danyliuk.testjob.railwaydata.repository.CarriageTypeRepository;
import com.danyliuk.testjob.railwaydata.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;

import static com.danyliuk.testjob.railwaydata.constants.Constants.*;

@SpringBootApplication
public class RailwaydataApplication {
    private static final Logger log = LoggerFactory.getLogger(RailwaydataApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RailwaydataApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CarriageTypeRepository carriageTypeRepository, CarriageRepository carriageRepository, TrainRepository trainRepository) {
        return (args) -> {
            Map<String, Integer> typesMap = new HashMap<>();
            typesMap.put(TYPE_SHARED, 81);
            typesMap.put(TYPE_ECONOM, 54);
            typesMap.put(TYPE_COMP, 36);
            typesMap.put(TYPE_SUPER_COMP, 18);
            ArrayList<CarriageType> listType = new ArrayList<>();

            for (Map.Entry<String, Integer> item : typesMap.entrySet()) {
                CarriageType carriageType = new CarriageType();
                carriageType.setNameCar(item.getKey());
                carriageType.setNumberSeats(item.getValue());
                listType.add(carriageType);
            }

            carriageTypeRepository.saveAll(listType);

            ArrayList<Carriage> listCar = new ArrayList<>();
            Iterable<CarriageType> typesIter = carriageTypeRepository.findAll();

            StreamSupport.stream(typesIter.spliterator(), false).forEach(carriageType -> {   //конвертация Iterable в Stream
                for (int i = 0; i < INIT_NUM_CAR; i++) {
                    Carriage carriage = new Carriage();
                    carriage.setTypeCar(carriageType);
                    listCar.add(carriage);
                }
            });

            carriageRepository.saveAll(listCar);

			Train train = new Train();
			train.setName("Украина-Победа");
            train.setListCarriages(listCar);
            trainRepository.save(train);

        };
    }
}
