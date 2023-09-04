use `sudoku`;

CREATE TABLE Leaderboard(
    Username VARCHAR(30),
    AttemptedGames INTEGER,
    CompletedGames INTEGER,
    TotalGuesses INTEGER,
    CorrectGuesses INTEGER,
    PRIMARY KEY (Username)
);

INSERT INTO Leaderboard
VALUES("Starburst23", 12, 8, 210, 170);