package com.example.studyplanner.service;

import com.example.studyplanner.entity.StudySession;
import com.example.studyplanner.repository.StudySessionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudySessionService {

    private final StudySessionRepository repository;

    public StudySessionService(StudySessionRepository repository) {
        this.repository = repository;
    }

    // US #1 : Créer une session [cite: 25, 200]
    public StudySession createSession(StudySession session, String username) {
        session.setStudentName(username); // Affecter l'utilisateur connecté [cite: 42, 201]

        // Validation métier : endTime après startTime [cite: 33, 42, 202]
        if (session.getEndTime().isBefore(session.getStartTime())) {
            throw new RuntimeException("L'heure de fin doit être après l'heure de début");
        }

        return repository.save(session);
    }

    // US #2 : Lister les sessions de l'utilisateur [cite: 27, 206]
    public List<StudySession> getSessionsForUser(String username) {
        return repository.findByStudentName(username); // Utilise le filtre par nom [cite: 189, 206]
    }

    // US #2 (Détails) : Récupérer une session spécifique avec vérification d'identité [cite: 110, 207]
    public StudySession getSessionById(Long id, String username) {
        return repository.findById(id)
                .filter(s -> s.getStudentName().equals(username)) // Vérifie que la session appartient à l'utilisateur [cite: 207, 210]
                .orElseThrow(() -> new RuntimeException("Session non trouvée ou accès refusé")); // Lance une exception si non trouvé [cite: 208, 215]
    }

    // US #3 : Modifier une session existante [cite: 29, 114]
    public StudySession updateSession(Long id, StudySession details, String username) {
        // On récupère la session existante (vérifie aussi l'appartenance) [cite: 210]
        StudySession session = getSessionById(id, username);

        // Mise à jour des champs [cite: 115]
        session.setSubject(details.getSubject());
        session.setDescription(details.getDescription());
        session.setStartTime(details.getStartTime());
        session.setEndTime(details.getEndTime());

        // Validation métier sur les nouvelles dates
        if (session.getEndTime().isBefore(session.getStartTime())) {
            throw new RuntimeException("L'heure de fin doit être après l'heure de début");
        }

        return repository.save(session);
    }

    // US #4 : Supprimer une session [cite: 31, 119]
    public void deleteSession(Long id, String username) {
        // On vérifie que la session existe et appartient à l'utilisateur avant de supprimer [cite: 210]
        StudySession session = getSessionById(id, username);
        repository.delete(session);
    }
}