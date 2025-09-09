package com.example.pzzl.engine.convertion;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

import java.util.ArrayList;
import java.util.List;

public class PGNParser {

    public static List<String> parsePGNtoFEN(String pgnMoves) throws Exception {
        PgnHolder pgn = new PgnHolder(filePath);
        pgn.loadPgn();
        Game game = pgn.getGame().get(0); // take first game
        List<String> fens = new ArrayList<>();

        Board board = new Board();
        fens.add(board.getFen());

        for (Move move : game.getHalfMoves()) {
            board.doMove(move.getLan()); // LAN works better here
            fens.add(board.getFen());
        }

        return fens;
    }
}
