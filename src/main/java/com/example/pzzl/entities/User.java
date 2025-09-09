package com.example.pzzl.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private Integer attempted = 0;
    private Integer solved = 0;
    private Integer accuracy = 0;

    @OneToMany
    private List<Puzzle> puzzles;

}
