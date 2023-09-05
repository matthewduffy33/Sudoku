# Sudoku
Sudoku Project created with Java and Jooby as well as some JavaScript

I used a simple format for the grid as I viewed as the issue with lots of other online sudoku apps. 
Lots of apps and websites have a very cluttered and confusing look and I wanted the opposite with simply the grid, the hint button and the undo/redo buttons.
So I wanted to have a stripped back and simple design.

Board:

![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/24693c5b-1dd6-4ec4-a9dc-fb45e5533b42)

NavBar:

![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/51d5413d-b1e5-4498-b2cb-11bb783b6289)

Home Page:

![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/9c90f2b6-c3e6-4dca-9508-e684716414a5)

The game highlights where the mouse is hovering over as well as highlighting the row, column, block and numbers that are the same
![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/14d05fb9-33c6-4c3e-9814-da53496f5218)

You can also click to select on a cell and move what is selected with the arrow keys
![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/d481d9c4-7a42-44c6-bfae-b6dc4330f7fb)



The undo and redo features speak for themselves and the hint feature also simply just gives the user a value they have either got wrong or not guessed yet.

I made 2 decisions with the hint features that may be unorthodox: 
I let the player have infinite hints, this is due to me wanting this to just be a fun and simple game with the goal being fun and not any form of competiviness.
The hints are not randomised, they go from right to left I did this due to the potential of asking for a hint and it not helping at all, in my way if you are really 
stuck then asking for multiple hints will definitely help as having multiple values filled in for you next to each other will help you more than getting random hints at completely different parts of the board.

Another feature I have is the notes section
![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/f49e8406-bf2c-4567-9ea3-6f7f37001d08)
This provides users with the ability to write notes on the potential options for what could go in each cell.

Most online versions of the game take the opposite approach for the notes and have you select what the options are and add them to the board.
I do the opposite with having every square potentially being any value (1-9) and the options are selected by removing values/ ruling values out. 
I do it this way due this making more sense to me as in a game of sudoku it is simpler and more intuitive to say what the cell can't be and using that info to understand what could be in the cell 

The final core feature is the ability to view mistakes.
This can be toggled on and off and it simply shows mistakes made on the board and updates whenever a move is made to say if it is a mistake or not.
![image](https://github.com/matthewduffy33/Sudoku/assets/105711454/569a8665-de63-4731-9759-d7405caf98df)

