package com.example.pzzl.engine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stockfish {
    private Process engine;
    private BufferedReader reader;
    private BufferedWriter writer;

    public boolean startEngine(String path) throws IOException {
        engine = Runtime.getRuntime().exec(path);
        reader = new BufferedReader(new InputStreamReader(engine.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(engine.getOutputStream()));
        return true;
    }

    public void sendCommand(String command) throws IOException {
        writer.write(command + "\n");
        writer.flush();
    }

    private Integer evaluatePosition(String fen) throws IOException {
        sendCommand("position fen " + fen);
        sendCommand("go depth 15");

        String line;
        Integer evaluation = null;

        while ((line = reader.readLine()) != null) {
            if (line.contains("score cp")) {
                String[] parts = line.split("score cp ");
                String[] after = parts[1].split(" ");
                evaluation = Integer.parseInt(after[0]);
            } else if (line.contains("score mate")) {
                evaluation = 10000;
            }
            if (line.startsWith("bestmove")) {
                break;
            }
        }

        // normalize: if it's black to move, flip eval
        if (fen.split(" ")[1].equals("b") && evaluation != null) {
            evaluation = -evaluation;
        }

        return evaluation;
    }

    public List<String> detectMistakes(List<String> fens) throws IOException {
        List<String> mistakes = new ArrayList<>();
        Integer prevEval = null;

        for (int i = 0; i < fens.size(); i++) {
            Integer eval = evaluatePosition(fens.get(i));

            if (prevEval != null && eval != null) {
                int diff = eval - prevEval;
                int absDiff = Math.abs(diff);

                if (absDiff >= 150) {
                    mistakes.add(fens.get(i) + " B");
                } else if (absDiff >= 80) {
                    mistakes.add(fens.get(i) + " M");
                }
            }
            prevEval = eval;
        }
        return mistakes;
    }


    public String getOutput(int waitTime) throws Exception {
        Thread.sleep(waitTime);
        StringBuilder sb = new StringBuilder();
        while (reader.ready()) {
            sb.append(reader.readLine()).append("\n");
        }
        return sb.toString();
    }

    public String getBestMove(String fen) throws Exception {
        sendCommand("position fen " + fen);
        sendCommand("go depth 12");
        String output;
        while (true) {
            output = reader.readLine();
            if (output.startsWith("bestmove")) {
                return output.split(" ")[1];
            }
        }
    }

    public int getPuzzleDifficulty(String fenBefore, String playedMove) throws IOException {

        int evalBefore = evaluatePosition(fenBefore);

        sendCommand("position fen " + fenBefore);
        sendCommand("go depth 15");

        String bestMove = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("bestmove")) {
                bestMove = line.split(" ")[1]; // e.g. "g1f3"
                break;
            }
        }

        String fenAfterPlayed = getFenAfterMove(fenBefore, playedMove);
        int evalAfterPlayed = evaluatePosition(fenAfterPlayed);

        String fenAfterBest = getFenAfterMove(fenBefore, bestMove);
        int evalAfterBest = evaluatePosition(fenAfterBest);

        int diff = (evalAfterBest - evalAfterPlayed);
        int absDiff = Math.abs(diff);

        if (absDiff >= 500) {
            return 1;
        } else if (absDiff >= 200) {
            return 2;
        } else {
            return 3;
        }
    }


    public String getFenAfterMove(String fenBefore, String move) throws IOException {

        sendCommand("position fen " + fenBefore + " moves " + move);

        sendCommand("d");

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Fen:")) {
                return line.substring(5).trim(); // Remove "Fen: "
            }
        }
        return null;
    }

    public void stopEngine() throws IOException {
        sendCommand("quit");
        reader.close();
        writer.close();
        engine.destroy();
    }
}
