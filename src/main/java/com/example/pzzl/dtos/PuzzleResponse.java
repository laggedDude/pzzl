package com.example.pzzl.dtos;

import com.example.pzzl.entities.Puzzle;
import lombok.Data;

@Data
public class PuzzleResponse {

    private Puzzle puzzle;
    private boolean moreGames;

}
