package com.example.pzzl.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer attempted = 0;
    private Integer solved = 0;
    private Integer accuracy = 0;

    @OneToMany
    private List<Puzzle> puzzles;

}
