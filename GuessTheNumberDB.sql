DROP DATABASE IF EXISTS GuessTheNumberDB;
CREATE DATABASE GuessTheNumberDB;
USE GuessTheNumberDB;

CREATE TABLE game (
	game_id INT PRIMARY KEY AUTO_INCREMENT,
    answer CHAR(4),
    finished BOOLEAN DEFAULT false
    );
    
INSERT INTO game(game_id, answer, finished) VALUES
	(1, "6785", true),
    (2, "8345", true),
    (3, "1756", true);
    
CREATE TABLE round (
	round_id INT PRIMARY KEY AUTO_INCREMENT,
    game_id INT NOT NULL,
    time_of_guess TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    guess CHAR(4),
    result CHAR(7),
    FOREIGN KEY fk_gameid (game_id) REFERENCES game(game_id)
    );
    
INSERT INTO round (round_id, game_id, time_of_guess, guess, result) VALUES
    (1, 1, "2021-09-01 19:25:16", "6512", "e:1:p:1"),
    (2, 1, "2021-09-02 11:45:56", "8541", "e:0:p:2"),
    (3, 1, "2021-09-03 07:43:17", "6785", "e:4:p:0"),
    (4, 2, "2021-09-04 17:12:34", "8345", "e:4:p:0"),
    (5, 3, "2021-09-05 22:59:32", "2498", "e:0:p:0"),
    (6, 3, "2021-09-06 14:11:59", "1756", "e:4:p:0");