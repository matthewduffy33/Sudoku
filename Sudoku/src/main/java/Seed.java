import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Seed extends Grid {



    Seed(){
        super ();
    }

    public void generateGrid(){

        Random rand = new Random();

        for (int y=0; y<9; y++) {   //loops through grid

            for (int x = 0; x < 9; x++) {

                setOptions(x, y);  //sets the options to what they can be

                if(options[y][x].size()==0){            //if there are no options then

                    while(options[y][x].size()<=1){    //backtrack until there is another option to try

                        backtrack(x, y);

                        if(x==0){     //goes back an entire row if needed
                            x=8;
                            y--;
                        }else{
                            x--;
                        }

                    }

                    options[y][x].remove(options[y][x].indexOf(grid[y][x]));   // removes the option from being called again to stop from going down the same path

                    grid[y][x]=null;    //removes the value from grid

                    setOptions(x, y);    //sets the rest of the options
                }

                randomValue(rand, x, y);     //adds a random value from the options

            }

        }

    }

    public void backtrack(int x, int y){
        grid[y][x]=null;
        options[y][x].clear();

        for(int z=1; z<=9; z++){      //resets the options back to 1-9
            options[y][x].add(z);
        }

    }


    public void randomValue(Random rand, int x, int y){

        int pos = rand.nextInt(options[y][x].size());

        int value = options[y][x].get(pos);  //gets random value from options and place it in the grid

        grid[y][x]=value;

    }



}
