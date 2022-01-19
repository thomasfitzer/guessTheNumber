/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.dao;

import com.sg.guessthenumber.dto.Round;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thomas
 */

@Repository
public class RoundDaoDB implements RoundDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        try {
            final String SELECT_ROUNDS_BY_GAMEID = "SELECT * FROM round "
                    + "WHERE game_id = ? ORDER BY time_of_guess";
            List<Round> rounds = jdbc.query(SELECT_ROUNDS_BY_GAMEID, new RoundMapper(), gameId);
            return rounds;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE round_id = ?";
            return jdbc.queryForObject(SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(game_id, guess, result) VALUES(?,?,?)";
        jdbc.update(INSERT_ROUND, round.getGameId(), round.getGuess(), round.getResult());
        
        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return getRoundById(newRoundId);
    }
    
    public static final class RoundMapper implements RowMapper<Round> {
        
        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("round_id"));
            round.setGameId(rs.getInt("game_id"));
            round.setGuess(rs.getString("guess"));
            
            Timestamp timestamp = rs.getTimestamp("time_of_guess");
            round.setGuessTime(timestamp.toLocalDateTime());
            
            round.setResult(rs.getString("result"));
            return round;
        }
    }
    
    
}
