DROP DATABASE IF EXISTS GuessTheNumberDBTest;
CREATE DATABASE GuessTheNumberDBTest;
USE GuessTheNumberDBTest;

CREATE TABLE game (
	game_Id INT PRIMARY KEY AUTO_INCREMENT,
    answer CHAR(4),
    finished BOOLEAN DEFAULT false
    );
    
    CREATE TABLE round (
	round_id INT PRIMARY KEY AUTO_INCREMENT,
    game_id INT NOT NULL,
    time_of_guess TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    guess CHAR(4),
    result CHAR(7),
    FOREIGN KEY fk_gameid (game_id) REFERENCES game(game_id)
    );
    
    select * from game;
    select * from round