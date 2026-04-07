package com.example.studyplanner.controller;

import com.example.studyplanner.entity.StudySession;
import com.example.studyplanner.service.StudySessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class StudySessionController {

    private final StudySessionService service;

    public StudySessionController(StudySessionService service) {
        this.service = service;
    }

    // 4.1.2. GET /api/sessions - Lister toutes les sessions de l'utilisateur connecté
    @GetMapping
    public List<StudySession> getAll(Principal principal) {
        return service.getSessionsForUser(principal.getName());
    }

    // 4.1.3. GET /api/sessions/{id} - Obtenir les détails d'une session spécifique
    @GetMapping("/{id}")
    public StudySession getOne(@PathVariable Long id, Principal principal) {
        return service.getSessionById(id, principal.getName());
    }

    // 4.1.1. POST /api/sessions - Créer une nouvelle session
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudySession create(@Valid @RequestBody StudySession session, Principal principal) {
        return service.createSession(session, principal.getName());
    }

    // 4.1.4. PUT /api/sessions/{id} - Modifier une session existante
    @PutMapping("/{id}")
    public StudySession update(@PathVariable Long id, @Valid @RequestBody StudySession session, Principal principal) {
        return service.updateSession(id, session, principal.getName());
    }

    // 4.1.5. DELETE /api/sessions/{id} - Supprimer une session
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Réponse 204 No Content comme demandé
    public void delete(@PathVariable Long id, Principal principal) {
        service.deleteSession(id, principal.getName());
    }
}