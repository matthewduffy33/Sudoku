import java.util.Random;

public class StartGrid extends Grid {


    StartGrid(){
        super ();
    }


    public void generate(Integer[][] seed){
        Random rand= new Random();
        Solver solver = new Solver();

        int x;
        int y;
        int value;

        this.setGrid(seed);

        while(amount()>30){         //30 is amount of values left in the sudoku
            solver.resetOptions();

            x = rand.nextInt(9);   //getting a random x and y position
            y = rand.nextInt(9);

            if(grid[y][x] != null){   //if something is in the grid
                value= grid[y][x];   //remove it
                grid[y][x]=null;


               if(!solver.check(grid, seed)){   //if no longer solvable then backtrack
                    grid[y][x] = value;
                }

            }
        }
    }

}
