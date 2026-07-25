package com.secure.notes.controller;

import com.secure.notes.entity.Note;
import com.secure.notes.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    private NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note createNote(@RequestBody String content, @AuthenticationPrincipal UserDetails user) {
        log.info("USER DETAILS: {}", user.getUsername());
        return noteService.createNoteForUser(user.getUsername(), content);
    }

    @GetMapping
    public List<Note> fetchNotes(@AuthenticationPrincipal UserDetails user) {
        log.info("USER DETAILS: {}", user.getUsername());
        return noteService.getNotesForUser(user.getUsername());
    }

    @PutMapping("/{noteId}")
    public Note updateNote(@PathVariable Long noteId, @RequestBody String content, @AuthenticationPrincipal UserDetails user) {
        return noteService.updateNoteForUser(noteId, user.getUsername(), content);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable Long noteId, @AuthenticationPrincipal UserDetails user) {
        noteService.deleteNoteForUser(noteId, user.getUsername());
    }
}
