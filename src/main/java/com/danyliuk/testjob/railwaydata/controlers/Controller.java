package com.danyliuk.testjob.railwaydata.controlers;

import com.danyliuk.testjob.railwaydata.RailwaydataApplication;
import com.danyliuk.testjob.railwaydata.repository.CarriageRepository;
import com.danyliuk.testjob.railwaydata.repository.CarriageTypeRepository;
import com.danyliuk.testjob.railwaydata.repository.TrainRepository;
import com.danyliuk.testjob.railwaydata.tables.Carriage;
import com.danyliuk.testjob.railwaydata.tables.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danyliuk.testjob.railwaydata.controlers.SqlCommands.*;


@org.springframework.stereotype.Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(RailwaydataApplication.class);

    @Autowired // This means to get the bean called userRepository Which is auto-generated by Spring, we will use it to handle the data
    private CarriageTypeRepository carriageTypeRepository;
    @Autowired
    private CarriageRepository carriageRepository;
    @Autowired
    private TrainRepository trainRepository;

    // Тестовый эндпоинт
    // curl http://localhost:8080/demo/test
    @GetMapping(path="/test")
    public @ResponseBody String test() {
        return "Тестовый эндпоинт";
    }

    // Эндпоинт добавить вагоны
    // curl http://localhost:8080/demo/addCarriage -d shareCar=5 -d economCar=5 -d compartmentCar=3 -d superCompartmentCar=3
    @PostMapping(path="/addCarriage")
    public @ResponseBody String addNewCarriage (
            @RequestParam int shareCar,
            @RequestParam int economCar,
            @RequestParam int compartmentCar,
            @RequestParam int superCompartmentCar
    ) {
        ArrayList<Carriage> listCarriages = new ArrayList<>();
        listCarriages.addAll(createCarriages(TYPE_SHARED, shareCar));
        listCarriages.addAll(createCarriages(TYPE_ECONOM, economCar));
        listCarriages.addAll(createCarriages(TYPE_COMP, compartmentCar));
        listCarriages.addAll(createCarriages(TYPE_SUPER_COMP, superCompartmentCar));

        carriageRepository.saveAll(listCarriages);

        return createReportAddCarriage(shareCar, economCar, compartmentCar, superCompartmentCar);
    }

    // Эндпоинт создание поезда
    // curl http://localhost:8080/demo/createtrain -d name=number1 -d shareCar=3 -d economCar=3 -d compartmentCar=3 -d superCompartmentCar=3
    @PostMapping(path="/createtrain") // Map ONLY POST Requests
    public @ResponseBody String createtrain (
            @RequestParam String name,
            @RequestParam int shareCar,
            @RequestParam int economCar,
            @RequestParam int compartmentCar,
            @RequestParam int superCompartmentCar
    ) {
        //проверка наличия вагонов
        shareCar = checkNumCars(TYPE_SHARED, shareCar);
        economCar = checkNumCars(TYPE_ECONOM, economCar);
        compartmentCar = checkNumCars(TYPE_COMP, compartmentCar);
        superCompartmentCar = checkNumCars(TYPE_SUPER_COMP, superCompartmentCar);

        //удаление из базы свободных вагонов для создания поезда
        if (shareCar > 0) {
            removeCars(TYPE_SHARED, shareCar);
        }
        if (economCar > 0) {
            removeCars(TYPE_ECONOM, economCar);
        }
        if (compartmentCar > 0) {
            removeCars(TYPE_COMP, compartmentCar);
        }
        if (superCompartmentCar > 0) {
            removeCars(TYPE_SUPER_COMP, superCompartmentCar);
        }

        //создание поезда
        if ((shareCar | economCar | compartmentCar | superCompartmentCar) > 0) {
            Train train = new Train();
            train.setName(name);
            train.setShareCar(shareCar);
            train.setEconomCar(economCar);
            train.setCompartmentCar(compartmentCar);
            train.setSuperCompartmentCar(superCompartmentCar);
            trainRepository.save(train);
            return createReportTrain(name, shareCar, economCar, compartmentCar, superCompartmentCar);
        } else {
            return "Поезд не сформирован. В базе отсутствуют вагоны.";
        }
    }

    // Эндпоинт получение информации о всех поездах поездах
    // curl http://localhost:8080/demo/infotrains
    @GetMapping(path="/infotrains")
    public @ResponseBody String getAllTrains() {
        Iterable<Train> trains = trainRepository.findAll();
        StringBuilder builder = new StringBuilder();
        for (Train train : trains) {
            String name = train.getName();
            int share = train.getShareCar();
            int econom = train.getEconomCar();
            int comp = train.getCompartmentCar();
            int sComp = train.getSuperCompartmentCar();

            builder.append(createInfoTrain(name, share, econom, comp, sComp));
        }
        if(! builder.isEmpty()) {
            return builder.toString();
        }else{
            return "Поездов в базе нет";
        }
    }

    // Эндпоинт получение списка поездов
    // curl http://localhost:8080/demo/listtrains
    @GetMapping(path="/listtrains")
    public @ResponseBody String getListTrains() {
        Iterable<Train> trains = trainRepository.findAll();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Train train : trains) {
            String name = train.getName();
            int numCar = train.getShareCar() + train.getEconomCar() + train.getCompartmentCar() + train.getSuperCompartmentCar();
            i++;
            builder.append(String.format("%d. %s, ID = %d\n", i, train.getName(), train.getId()));
        }
        if(! builder.isEmpty()) {
            return "\nСписок поездов:\n" + builder.toString();
        }else{
            return "Поездов в базе нет";
        }
    }

    // Эндпоинт получение информации по конкретному поезду по ID поезда (выводится в списке поездов)
    // curl http://localhost:8080/demo/infoabouttrain -d idCar=1
    @PostMapping(path="/infoabouttrain")
    public @ResponseBody String getInfoTrain( @RequestParam int idCar ) {
        Optional<Train> train = trainRepository.findById(idCar);
        if(train.isPresent()) {
            String name = train.get().getName();
            int share = train.get().getShareCar();
            int econom = train.get().getEconomCar();
            int comp = train.get().getCompartmentCar();
            int sComp = train.get().getSuperCompartmentCar();

            return createInfoTrain(name, share, econom, comp, sComp);
        }
            return "Такого поезда в базе нет\n";
    }

    //удаление вагонов из базы
    void removeCars(String typeCar, int numCar){
        int typeId = carriageTypeRepository.findByTypeCar(typeCar).getId();
        for (int i=0; i < numCar; i++) {
            List<Optional<Carriage>> cars = carriageRepository.findByTypeId(typeId);
            if(!cars.isEmpty() && cars.get(0).isPresent()) {
                carriageRepository.deleteById(cars.get(0).get().getId());
            }
        }
    }

    //проверка наличия вагонов в базе
    int checkNumCars(String typeCar, int numCar){
        int typeId = carriageTypeRepository.findByTypeCar(typeCar).getId();
        int freeCar = carriageRepository.findByTypeId(typeId).size();
        return Math.min(numCar, freeCar);
    }

    //Создание вагонов
    ArrayList<Carriage> createCarriages(String typeCar, int numCar) {
        ArrayList<Carriage> listCarriages = new ArrayList<>();
        int typeId = carriageTypeRepository.findByTypeCar(typeCar).getId();
        for (int i=0; i<numCar; i++) {
            Carriage carriage = new Carriage();
            carriage.setType(typeId);
            listCarriages.add(carriage);
        }
        return listCarriages;
    }
}

