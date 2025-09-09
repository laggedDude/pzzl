package com.example.pzzl.services;

import com.example.pzzl.entities.Puzzle;
import com.example.pzzl.entities.User;
import com.example.pzzl.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User registerUser(String username) {

        User user = new User();
        user.setUsername(username);

        return userRepo.save(user);

    }

    public User registerUser(String username, List<Puzzle>  puzzles) {

        User user = new User();
        user.setUsername(username);
        user.setPuzzles(puzzles);

        return userRepo.save(user);

    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User getUser(String username) {

        Optional<User> optUser = userRepo.findByUsername(username);
        return optUser.orElse(null);

    }

    public User makeAnAttempt(String username, boolean solved) {

        Optional<User> optUser = userRepo.findByUsername(username);
        User user = optUser.orElse(null);
        if (user != null) {
            if (solved) {
                user.setSolved(user.getSolved() + 1);
            } else {
                user.setAttempted(user.getAttempted() + 1);
            }
        }
        return null;
    }

    public List<Puzzle> addPuzzles(List<Puzzle> puzzles, String username) {

        User user = getUser(username);

        List<Puzzle> allPuzzles = user.getPuzzles();
        allPuzzles.addAll(puzzles);

        user.setPuzzles(allPuzzles);
        saveUser(user);
        return allPuzzles;

    }

    public boolean userExists(String username) {
        Optional<User> optUser = userRepo.findByUsername(username);
        return optUser.isPresent();
    }

    public int getUserLevel(String username) {

        return getUser(username).getAccuracy()/20;

    }

    public List<Puzzle> getPuzzles(String username) {

        return getUser(username).getPuzzles();

    }

}
