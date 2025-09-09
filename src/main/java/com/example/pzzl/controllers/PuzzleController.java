package com.example.pzzl.controllers;

import com.example.pzzl.dtos.PuzzleRequest;
import com.example.pzzl.dtos.PuzzleResponse;
import com.example.pzzl.services.PuzzleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;

    public PuzzleController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    public PuzzleResponse nextPuzzle(@RequestBody PuzzleRequest request) {


        return null;

    }

}
