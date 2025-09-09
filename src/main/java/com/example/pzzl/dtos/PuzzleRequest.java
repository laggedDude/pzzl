package com.example.pzzl.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PuzzleRequest {

    private String user;
    private List<RequestGames> games; //there should be at least 3 games I guess

}
