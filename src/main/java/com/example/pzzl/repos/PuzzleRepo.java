package com.example.pzzl.repos;

import com.example.pzzl.entities.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuzzleRepo extends JpaRepository<Puzzle, Long> {
}
