package com.secure.notes.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "NOTE")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long id;
    @Lob
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "OWNER_USER_NAME")
    private String ownerUsername;
}
