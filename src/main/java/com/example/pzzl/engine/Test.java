package com.example.pzzl.engine;


import com.example.pzzl.engine.convertion.PGNParser;
import com.example.pzzl.entities.Puzzle;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {

        Stockfish sf = new Stockfish();
        System.out.println("Stockfish status: " + sf.startEngine("src/main/java/com/example/pzzl/engine/stockfish/stockfish-windows-x86-64-avx2.exe"));

        List<String> fens = Arrays.asList(
                "rnbqkbnr/pppppppp/8/8/8/5P2/PPPPP1PP/RNBQKBNR b KQkq - 0 1",
                "rnbqkbnr/pppp1ppp/8/4p3/8/5P2/PPPPP1PP/RNBQKBNR w KQkq - 0 2",
                "rnbqkbnr/pppp1ppp/8/4p3/6P1/5P2/PPPPP2P/RNBQKBNR b KQkq g3 0 2",
                "rnbqkbnr/pppp1ppp/8/4p3/6Pq/5P2/PPPPP2P/RNBQKBNR w KQkq - 1 3"
        );

        System.out.println(PGNParser.parsePGNtoFEN("1. f3 e5 2. g4 Qh4#"));

    }

}