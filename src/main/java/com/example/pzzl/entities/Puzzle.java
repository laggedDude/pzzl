package com.example.pzzl.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Puzzle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fen;

    @OneToMany
    private List<Move> correctMoves;
    private boolean done = false;
    private Integer numberOfMoves; // total number of moves in the puzzle
    private Integer difficulty; // 1-5 scale

}
