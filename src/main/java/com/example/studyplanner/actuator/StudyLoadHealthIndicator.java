package com.example.studyplanner.actuator;

import com.example.studyplanner.repository.StudySessionRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class StudyLoadHealthIndicator implements HealthIndicator {

    private final StudySessionRepository repository;

    public StudyLoadHealthIndicator(StudySessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        // US #7 - Monitoring de la charge
        long count = repository.count();

        // Si plus de 50 sessions, l'état est DOWN (Surcharge)
        Health.Builder status = count > 50 ? Health.down() : Health.up();

        return status.withDetail("totalSessions", count)
                .withDetail("maxThreshold", 50)
                .withDetail("message", count > 50 ? "Surcharge de travail détectée !" : "Charge normale")
                .build();
    }
}