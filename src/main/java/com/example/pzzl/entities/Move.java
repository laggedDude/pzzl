package com.example.pzzl.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Move {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String notation;
    private Integer moveNumber;
    private String fenAfterMove; // FEN after this move
    private Boolean isCorrect;

}
