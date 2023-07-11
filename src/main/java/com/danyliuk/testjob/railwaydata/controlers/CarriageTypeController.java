package com.danyliuk.testjob.railwaydata.controlers;

import com.danyliuk.testjob.railwaydata.RailwaydataApplication;
import com.danyliuk.testjob.railwaydata.repository.CarriageTypeRepository;
import com.danyliuk.testjob.railwaydata.tables.CarriageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;


@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class CarriageTypeController {
    private static final Logger log = LoggerFactory.getLogger(RailwaydataApplication.class);

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private CarriageTypeRepository carriageTypeRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewCarriageType (@RequestParam String typeCar
            , @RequestParam int numberSeats) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        CarriageType carriageType = new CarriageType();
        carriageType.setType(typeCar);
        carriageType.setNumberSeats(numberSeats);
        carriageType = carriageTypeRepository.save(carriageType);
//        Iterable<CarriageType> ccc = carriageTypeRepository.findAll();
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<CarriageType> getAllCarriageTypes() {
        // This returns a JSON or XML with the users
        return carriageTypeRepository.findAll();
    }

    @GetMapping(path="/test")
    public @ResponseBody String test() {
        return new String("Privet");
    }

    @GetMapping(path="/sqlall")
    public @ResponseBody String sqlall() throws SQLException {

        try{
            String url = "jdbc:postgresql://localhost:5433/railway";
            String username = "root";
            String password = "dev";
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)){
                Statement statement = connection.createStatement();
                statement.executeUpdate(SqlCommands.CREATE_TABLE_CARRIAGE_TYPE);
                statement.executeUpdate(SqlCommands.getSqlInsertToCarriageType("shared", 81));
                statement.executeUpdate(SqlCommands.getSqlInsertToCarriageType("econom", 54));
                statement.executeUpdate(SqlCommands.getSqlInsertToCarriageType("compartment", 36));
                statement.executeUpdate(SqlCommands.getSqlInsertToCarriageType("super_compartment", 18));

/*
                ResultSet result = statement.executeQuery(SqlCommands.CREATE_TABLE_CARRIAGE_TYPE);
                StringBuilder builder = new StringBuilder();
                while (result.next()) {
                    String type = result.getString("type");
                    int seats = result.getInt("number_seats");
                    builder.append(String.format("Тип вагона = %s, кол-во мест = %d", type, seats));
//                    builder.append("<br>");
                    builder.append("\n");
                }
*/
                connection.close();
                log.info("Таблицы успешно созданы");
                return new String("Таблицы успешно созданы");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
            return new String("Error");
        }
    }
}

