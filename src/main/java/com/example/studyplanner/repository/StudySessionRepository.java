package com.example.studyplanner.repository; // Vérifie que ce nom correspond à tes dossiers

import com.example.studyplanner.entity.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {

    // US #2 & #6 : Pour récupérer uniquement les sessions de l'étudiant connecté
    List<StudySession> findByStudentName(String studentName);

    // Étape 8 : Pour compter les sessions (Health Indicator)
    long countByStudentName(String studentName);
}