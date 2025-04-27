package com.example.wellnesstracker;

import com.example.wellnesstracker.model.Supplement;
import com.example.wellnesstracker.model.WellnessRecord;
import com.example.wellnesstracker.repository.SupplementRepository;
import com.example.wellnesstracker.repository.WellnessRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class WellnessTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WellnessTrackerApplication.class, args);
    }

}
