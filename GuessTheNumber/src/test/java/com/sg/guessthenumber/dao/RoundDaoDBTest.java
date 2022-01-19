/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.TestApplicationConfiguration;
import com.sg.guessthenumber.dto.Game;
import com.sg.guessthenumber.dto.Round;
import java.time.LocalDateTime;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

/**
 *
 * @author Thomas
 */


@SpringBootTest(classes = TestApplicationConfiguration.class)
public class RoundDaoDBTest {
    
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    GameDao gameDao;

    @Autowired
    RoundDao roundDao;    
    
    public RoundDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Game game = new Game();
        // game.setAnswer("1234");
        // game.setFinished(false);
        // game.setGameId(1);
        // gameDao.addGame(game);
    }
    
    @AfterEach
    public void tearDown() {
    JdbcTestUtils.deleteFromTables(jdbc, "round");        
    }

    /**
     * Test of getAllRoundsByGameId method, of class RoundDaoDB.
     */
    @Test
    public void testGetAllRoundsByGameId() {
    }

    /**
     * Test of getRoundById method, of class RoundDaoDB.
     */
    @Test
    public void testGetRoundById() {
    }

    /**
     * Test of addRound method, of class RoundDaoDB.
     */
    @Test
    public void testAddRound() {
        int id = 1;
        
        Game game = new Game();
        game.setAnswer("1234");
        game.setFinished(false);
        game.setGameId(id);
        game = gameDao.addGame(game);  
        
        
        
        Round round = new Round();
        
        round.setGameId(game.getGameId());
        round.setGuess("1235");
        round.setGuessTime(LocalDateTime.MIN);
        round.setResult("e:3:p:0");
        round.setRoundId(id);
        Round fromDao = roundDao.addRound(round);
        

        // Figure out which thing is not matching
        assertEquals(round.getRoundId(), fromDao.getRoundId());
        assertEquals(round.getGuess(), fromDao.getGuess());
        assertEquals(round.getResult(), fromDao.getResult());
        assertEquals(round.getGameId(), fromDao.getGameId());
    }
    
}
