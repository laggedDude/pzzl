package com.example.pzzl.repos;

import com.example.pzzl.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepo extends JpaRepository<Move, Long> {
}
