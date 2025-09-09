package com.example.pzzl.engine.convertion;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveException;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PGNParser {


    public static List<String> parsePGNtoFEN(String pgnText) throws Exception {
        if (pgnText == null) throw new IllegalArgumentException("pgnText cannot be null");

        // 1) Remove tag-pairs like [Event "..."] lines
        String movesText = pgnText.replaceAll("(?m)^\\[.*\\]\\s*$", "");

        // 2) Remove comments in braces { ... }
        movesText = movesText.replaceAll("\\{[^}]*\\}", " ");

        // 3) Remove variations in parentheses ( ... ), loop to remove nested-ish variations
        while (movesText.matches(".*\\([^()]*\\).*")) {
            movesText = movesText.replaceAll("\\([^()]*\\)", " ");
        }

        // 4) Remove NAGs like $1, $12
        movesText = movesText.replaceAll("\\$\\d+", " ");

        // 5) Remove move numbers like "1." or "12..." (keeps SAN tokens)
        movesText = movesText.replaceAll("\\d+\\.+", " ");

        // Normalize whitespace
        movesText = movesText.replaceAll("\\s+", " ").trim();

        // 6) Split and filter out results
        String[] tokens = movesText.isEmpty() ? new String[0] : movesText.split("\\s+");
        List<String> sanMoves = new ArrayList<>();
        for (String t : tokens) {
            if (t == null || t.isBlank()) continue;
            t = t.trim();
            // skip game result markers
            if (t.equals("1-0") || t.equals("0-1") || t.equals("1/2-1/2") || t.equals("*")) continue;
            // strip trailing check/mate/annotation symbols like +, #, !, ?
            t = t.replaceAll("[+#\\?!]+$", "");
            if (!t.isEmpty()) sanMoves.add(t);
        }

        // 7) Apply on the board one by one
        List<String> fens = new ArrayList<>();
        Board board = new Board();
        fens.add(board.getFen()); // initial

        int moveIndex = 0;
        for (String san : sanMoves) {
            moveIndex++;
            try {
                // chesslib accepts SAN strings in board.doMove(...)
                board.doMove(san);
                fens.add(board.getFen());
            } catch (Exception e) {
                // helpful error: include the SAN that failed and the 1-based ply index
                throw new Exception("Failed to apply SAN move '" + san + "' at ply " + moveIndex + ": " + e.getMessage(), e);
            }
        }

        return fens;
    }
}
