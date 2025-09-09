package com.example.pzzl.repos;

import com.example.pzzl.entities.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PuzzleRepo extends JpaRepository<Puzzle, Long> {

    Optional<Puzzle> findFirstByDoneFalseAndDifficultyEquals(int  difficulty);

}
