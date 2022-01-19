/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

import com.sg.guessthenumber.dao.GameDao;
import com.sg.guessthenumber.dao.RoundDao;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thomas
 */

@Service
public class ServiceLayer {
   
    @Autowired
    GameDao gameDao;
    
    @Autowired
    RoundDao roundDao;
    
    public List<Game> getAllGames() {
        List<Game> games = gameDao.getAllGames();
        for (Game game : games) {
            if (!game.isFinished()) {
                game.setAnswer("****");
            }
        }
        return games;
    }
    
    // Could this be an issue?
    public List<Round> gettAllRoundsByGameId (int gameId) {
        return roundDao.getAllRoundsByGameId(gameId);
    }
    
    public Round makeGuess(Round guess) {
         LocalDateTime currentTime = LocalDateTime.now();
        guess.setGuessTime(currentTime);

        // Compute result of guess
        Game game = gameDao.getGameById(guess.getGameId());
        String result = figureSolution(guess.getGuess(), game.getAnswer());
        guess.setResult(result);

        // Add new round to db
        guess = roundDao.addRound(guess);

        // Check if game is finished
        if (guess.getGuess().equals(game.getAnswer())) {
            game.setFinished(true);
            gameDao.updateGame(game);
        }

        return guess;       
    }
    
    public Game getGameById(int gameId) {
        Game game = gameDao.getGameById(gameId);
        if (!game.isFinished()) {
            game.setAnswer("****");
        }
        
        return game;
    }
    
    public Game addGame(Game game) {
        return gameDao.addGame(game);
    }
    
    public int newGame() {
        Game game = new Game();
        game.setAnswer(createAnswer());
        game = gameDao.addGame(game);
        
        return game.getGameId();
    }
    
    
    private String createAnswer() {
        Random ran = new Random();
        int first = ran.nextInt(10);
        
        int second = ran.nextInt(10);
        while (second == first) {
            second = ran.nextInt(10);
        }
        
        int third = ran.nextInt(10);
        while (third == second || third == first) {
            third = ran.nextInt(10);
        }
        
        int fourth = ran.nextInt(10);
        while (fourth == third || fourth == second || fourth == first) {
            fourth = ran.nextInt(10);
        }
        
        String solution = String.valueOf(first) + String.valueOf(second)
                + String.valueOf(third) + String.valueOf(fourth);
        
        return solution;
    }
    
    public String figureSolution (String guess, String solution) {
        char[] guessChar = guess.toCharArray();
        char[] solChar = solution.toCharArray();
        int identical = 0;
        int part = 0;
        
        for (int i = 0; i < guessChar.length; i++) {
            if (solution.indexOf(guessChar[i]) > -1) {
                if (guessChar[i] == solChar[i]) {
                    identical++;
                } else {
                    part++;
                }
            }
        }
        String result = "e:" + identical + ":p" + part;
        
        return result;
    }
}
