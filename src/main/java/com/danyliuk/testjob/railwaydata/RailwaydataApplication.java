package com.danyliuk.testjob.railwaydata;

import com.danyliuk.testjob.railwaydata.controlers.SqlCommands;
import com.danyliuk.testjob.railwaydata.repository.CarriageTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.danyliuk.testjob.railwaydata.controlers.SqlCommands.*;
import static com.danyliuk.testjob.railwaydata.controlers.SqlCommands.CREATE_TABLE_TRAIN;

//http://localhost:8080/demo/add -d%20type=shared -d numberSeats=81

@SpringBootApplication
//@RestController
public class RailwaydataApplication {
	private static final Logger log = LoggerFactory.getLogger(RailwaydataApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RailwaydataApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CarriageTypeRepository repository) {
		return (args) -> {
			//Создание и инициализация таблиц кодом SQL
			try {
				String url = "jdbc:postgresql://localhost:5433/railway";
				String username = "root";
				String password = "dev";
				Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
				try (Connection connection = DriverManager.getConnection(url, username, password)) {
					Statement statement = connection.createStatement();
					statement.executeUpdate(REMOVE_TABLE_CARRIAGE_TYPE);
					statement.executeUpdate(CREATE_TABLE_CARRIAGE_TYPE);
					statement.executeUpdate(getSqlInsertToCarriageType(TYPE_SHARED, 81));
					statement.executeUpdate(getSqlInsertToCarriageType(TYPE_ECONOM, 54));
					statement.executeUpdate(getSqlInsertToCarriageType(TYPE_COMP, 36));
					statement.executeUpdate(getSqlInsertToCarriageType(TYPE_SUPER_COMP, 18));

					statement.executeUpdate(REMOVE_TABLE_CARRIAGE);
					statement.executeUpdate(CREATE_TABLE_CARRIAGE);
					for(int i=0; i<INIT_NUM_CAR; i++) {
						statement.executeUpdate(getSqlInsertToCarriage(TYPE_SHARED));
						statement.executeUpdate(getSqlInsertToCarriage(TYPE_ECONOM));
						statement.executeUpdate(getSqlInsertToCarriage(TYPE_COMP));
						statement.executeUpdate(getSqlInsertToCarriage(TYPE_SUPER_COMP));
					}

					statement.executeUpdate(REMOVE_TABLE_TRAIN);
					statement.executeUpdate(CREATE_TABLE_TRAIN);
					statement.executeUpdate(getSqlInsertToTrain("Украина-Победа", 0, 0, 5, 5));

					ResultSet result = statement.executeQuery(GET_ALL_TRAINS);
					StringBuilder builder = new StringBuilder();
					while (result.next()) {
						String name = result.getString("name");
						int share = result.getInt("share_car");
						int econom = result.getInt("econom_car");
						int compartment = result.getInt("compartment_car");
						int super_compartment = result.getInt("super_compartment_car");
						builder.append("""

----------------------------------------------------------
Таблицы успешно созданы.
								""");
						builder.append("\n");
						builder.append(createReportTrain(name, share, econom, compartment, super_compartment));
					}

					connection.close();
					log.info(builder.toString());
				}
			} catch (Exception ex) {
				log.error("Ошибка создания таблиц. Перезапустите приложение");
				log.error("Ошибка Ошибка возникает из-за случайного изменения порядка столбцов в программе (похоже на баг Postgres)");
				log.error(ex.toString());
			}
		};
	}
}
