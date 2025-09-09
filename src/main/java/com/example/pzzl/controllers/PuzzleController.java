package com.example.pzzl.controllers;

import com.example.pzzl.dtos.PuzzleRequest;
import com.example.pzzl.dtos.PuzzleResponse;
import com.example.pzzl.dtos.RequestGames;
import com.example.pzzl.engine.convertion.PGNParser;
import com.example.pzzl.entities.Puzzle;
import com.example.pzzl.services.PuzzleService;
import com.example.pzzl.services.UserService;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;
    private final UserService userService;

    public PuzzleController(PuzzleService puzzleService, UserService userService) {
        this.puzzleService = puzzleService;
        this.userService = userService;
    }

    @GetMapping("/nextPuzzle")
    public PuzzleResponse nextPuzzle(@RequestBody PuzzleRequest request) throws Exception {

        List<RequestGames> requestGames = request.getGames();
        List<Puzzle> puzzles = null;

        if (requestGames != null) {

            List<String> fens = new ArrayList<>();

            for (RequestGames requestGame : requestGames) {
                fens.addAll(PGNParser.parsePGNtoFEN(requestGame.getPgn()));
            }

            puzzles = puzzleService.createPuzzles(fens);


        }

        List<Puzzle> allPuzzles;

        if (userService.userExists(request.getUser())) {
            if (puzzles != null) {
                allPuzzles = userService.addPuzzles(puzzles, request.getUser());
            } else {
                allPuzzles = puzzles;
            }
        } else {
            userService.registerUser(request.getUser(), puzzles);
            allPuzzles = puzzles;
        }


        PuzzleResponse puzzleResponse = new PuzzleResponse();
        puzzleResponse.setPuzzle(puzzleService.getPuzzle(userService.getUserLevel(request.getUser()),  allPuzzles));
        puzzleResponse.setMoreGames(requestGames.size()<3);

        return puzzleResponse;

    }

}
