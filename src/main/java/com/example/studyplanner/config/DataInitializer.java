package com.example.studyplanner.config;

import com.example.studyplanner.entity.StudySession;
import com.example.studyplanner.repository.StudySessionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StudySessionRepository repository) {
        return args -> {
            // Création d'une session pour Alice (Déjà OK)
            StudySession s1 = new StudySession();
            s1.setSubject("Mathématiques");
            s1.setStartTime(LocalDateTime.now().plusDays(1)); // OK : C'est le futur
            s1.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
            s1.setStudentName("alice");
            repository.save(s1);

            // MODIFICATION POUR BOB ICI :
            StudySession s2 = new StudySession();
            s2.setSubject("Informatique");
            s2.setStartTime(LocalDateTime.now().plusDays(2)); // CHANGÉ : .plusDays(2) pour être dans le futur
            s2.setEndTime(LocalDateTime.now().plusDays(2).plusHours(3));
            s2.setStudentName("bob");
            repository.save(s2);

            System.out.println("Base de données initialisée avec succès !");
        };
    }
}