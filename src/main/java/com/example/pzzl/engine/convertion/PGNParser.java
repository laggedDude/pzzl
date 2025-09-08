package com.example.pzzl.engine.convertion;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;

import java.util.ArrayList;
import java.util.List;

public class PGNParser {

    public static List<String> parsePGNtoFEN(String pgnMoves) throws MoveException {
        Board board = new Board();
        List<String> fens = new ArrayList<>();
        fens.add(board.getFen());

        String[] tokens = pgnMoves.replaceAll("\\d+\\.", "").trim().split("\\s+");

        for (String token : tokens) {
            if (token.equals("1-0") || token.equals("0-1") || token.equals("1/2-1/2")) break;
            Move move = new Move(token, board.getSideToMove());
            board.doMove(move);
            fens.add(board.getFen());
        }

        return fens;
    }
}
