package com.danyliuk.testjob.railwaydata;

import com.danyliuk.testjob.railwaydata.repository.CarriageTypeRepository;
import com.danyliuk.testjob.railwaydata.tables.CarriageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// "scada_user" --dP "simple_pass"
//http://localhost:8080/demo/add%20-d%20type=shared%20-d%20numberSeats=81

@SpringBootApplication
//@RestController
public class RailwaydataApplication {
	private static final Logger log = LoggerFactory.getLogger(RailwaydataApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwaydataApplication.class, args);
	}
/*
//---------------для Н2--------------------
	private CarriageTypeRepository repository;

	@Bean
	public CommandLineRunner demo(CarriageTypeRepository repository) {
		this.repository = repository;
		return (args) -> {
			// save a few customers
			repository.save(new CarriageType("shared", 81));
			repository.save(new CarriageType("econom", 54));
			repository.save(new CarriageType("compartment", 36));
			repository.save(new CarriageType("super_compartment", 18));

		};
	}

	@GetMapping("/printtypes")
	public String printtypes() {
		List<CarriageType> res = (List<CarriageType>) repository.findAll();
		StringBuilder builder = new StringBuilder();
		for (CarriageType type : res) {
			builder.append(String.format("Тип вагона = %s, кол-во мест = %d", type.getType(), type.getNumberSeats()));
			builder.append("<br>");
		}
		return builder.toString();
	}
//-----------------------------------------------------------------------
*/
}