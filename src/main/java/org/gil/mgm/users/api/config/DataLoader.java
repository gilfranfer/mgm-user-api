package org.gil.mgm.users.api.config;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.gil.mgm.users.api.entity.UserEntity;
import org.gil.mgm.users.api.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DataLoader {

    private final Logger logger = Logger.getLogger(DataLoader.class.getName());
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData()  throws IOException, CsvValidationException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.csv");
        if(inputStream == null) {
            logger.log(Level.SEVERE, "No data found");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVReader csvReader = new CSVReader(reader)
        ) {
            List<UserEntity> users = new ArrayList<>();
            String[] data;

            csvReader.readNext(); // Skip header row
            logger.log(Level.INFO, "Reading User data...");
            while ((data = csvReader.readNext()) != null) {
                users.add( buildUserEntity(data));
            }
            userRepository.saveAll(users);
            logger.log(Level.INFO, "User data Loaded into H2.");
        }
    }

    private UserEntity buildUserEntity(String[] data) {
        int ID_COLUMN_INDEX = 0;
        int FIRSTNAME_COLUMN_INDEX = 1;
        int LASTNAME_COLUMN_INDEX = 2;
        int EMAIL_COLUMN_INDEX = 3;
        int PROFESSION_COLUMN_INDEX = 4;
        int DATE_CREATED_COLUMN_INDEX = 5;
        int COUNTRY_COLUMN_INDEX = 6;
        int CITY_COLUMN_INDEX = 7;

        return new UserEntity(
                Long.parseLong(data[ID_COLUMN_INDEX]),
                data[FIRSTNAME_COLUMN_INDEX],
                data[LASTNAME_COLUMN_INDEX],
                data[EMAIL_COLUMN_INDEX],
                data[PROFESSION_COLUMN_INDEX],
                LocalDate.parse(data[DATE_CREATED_COLUMN_INDEX]),
                data[COUNTRY_COLUMN_INDEX],
                data[CITY_COLUMN_INDEX]
        );
    }

}
