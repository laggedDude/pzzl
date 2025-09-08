package com.example.pzzl.services;

import com.example.pzzl.engine.Stockfish;
import com.example.pzzl.entities.Move;
import com.example.pzzl.entities.Puzzle;
import com.example.pzzl.repos.MoveRepo;
import com.example.pzzl.repos.PuzzleRepo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PuzzleService {

    private final PuzzleRepo puzzleRepo;
    private final MoveRepo moveRepo;

    public PuzzleService(PuzzleRepo puzzleRepo, MoveRepo moveRepo) {
        this.puzzleRepo = puzzleRepo;
        this.moveRepo = moveRepo;
    }

    public Puzzle createPuzzle(Puzzle puzzle) {

        return puzzleRepo.save(puzzle);

    }

    public List<Puzzle> createPuzzles(List<String> fens) throws Exception {

        Stockfish sf = new Stockfish();
        sf.startEngine("src/main/java/com/example/pzzl/engine/stockfish/stockfish-windows-x86-64-avx2.exe");

        List<String> mistakes = sf.detectMistakes(fens);

        List<Puzzle> puzzles = new ArrayList<>();

        for (String fen : mistakes) {
            Puzzle puzzle = new Puzzle();
            puzzle.setFen(fen);
            puzzle.setNumberOfMoves(1);
            if (fen.endsWith("B")) {
                puzzle.setDifficulty(sf.getPuzzleDifficulty(fen, ));
            }

            Move move = new Move();
            move.setMoveNumber(0);
            move.setIsCorrect(true);
            move.setNotation(sf.getBestMove(fen));
            move.setFenAfterMove(sf.getFenAfterMove(fen, move.getNotation()));
            moveRepo.save(move);
            puzzle.setCorrectMoves(List.of(move));
            puzzleRepo.save(puzzle);
        }

        return null;

    }

}
