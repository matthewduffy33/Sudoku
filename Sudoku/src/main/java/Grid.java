import java.util.*;
public class Grid {

    protected Integer[][] grid;
    protected ArrayList<Integer>[][] options;

    Grid(){

        grid = new Integer[][]{{null, null, null, null, null, null, null, null, null},    //grid is 2d array of integer
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        options = new ArrayList[9][9];      //options is a separate 2d array of arraylists that holds the potential values that could be in the grid

        resetOptions();               //sets the arraylist to 1-9 for all the values to start with as for an empty grid any value could be in any space

    }

    public void setGrid(Integer[][] newGrid) {   //sets the grid to match a set grid
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                this.grid[y][x]=newGrid[y][x];
            }
        }
    }

    public Integer[][] getGrid(){
        return grid;
    } //returns the grid



    public void resetOptions(){   //sets the arraylist to 1-9 for all the values
        ArrayList<Integer> base = new ArrayList<>();

        for(int z=1; z<=9; z++){
            base.add(z);
        }

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                options[y][x] = new ArrayList<>(base);

            }
        }
    }


    public ArrayList<Integer>[][] getOptions(){  //returns options 2d array
        return options;
    }

    public ArrayList<Integer> getOption(int x, int y){  //return the options for a specific space in a grid
        return options[y][x];
    }


    public Integer[] row(int y){
        return grid[y];
    }

    public Integer[] column(int x){       //loops through and gets the column of a grid
        Integer[] column = new Integer[9];
        for (int i=0; i<9; i++){
            column[i]= grid[i][x];
        }
        return column;
    }

    public Integer[] block(int x, int y){       //loops through and gets the block of a grid
        Integer[] block = new Integer[9];
        int newX = (x/3)*3;
        int newY = (y/3)*3;
        int count =0;


        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                block[count]= grid[i+newY][j+newX];
                count++;
            }

        }

        return block;
    }


    public boolean gridEqual(Integer[][] otherGrid){    //loops through and check the grid is equal to the given function
        for (int x=0; x<9; x++){
            for (int y=0; y<9; y++){
                if(otherGrid[y][x] != this.grid[y][x]){
                    return false;
                }
            }
        }

        return true;
    }

    public int amount(){       //get the amount of values in the grid
        int amount=0;
        for (int x=0; x<9; x++){
            for (int y=0; y<9; y++){
                if(this.grid[y][x] != null){
                   amount++;
                }
            }
        }

        return amount;
    }


    public void setOption(int x, int y, ArrayList<Integer> values){       //sets options for testing reasons

        options[y][x] = new ArrayList<>(values);

    }


    public void setOptions(int x, int y){
        HashSet<Integer> columnSet = new HashSet<>(Arrays.asList(column(x)));

        HashSet<Integer> rowSet = new HashSet<>(Arrays.asList(row(y)));

        HashSet<Integer> blockSet = new HashSet<>(Arrays.asList(block(x,y)));  //get the values from the column, row and block and removes them from being options

        options[y][x].removeAll(rowSet);
        options[y][x].removeAll(columnSet);
        options[y][x].removeAll(blockSet);


    }



    public void printGrid(){ //prints grid for testing reasons
        for (int y=0; y<9; y++){

            for (int x=0; x<9; x++){

                if(grid[y][x]==null){
                    System.out.print("  ");
                }else{
                    System.out.print(grid[y][x]+" ");
                }


                if(x==2||x==5){
                    System.out.print("| ");
                }
            }

            if(y==2||y==5){
                System.out.println("\n_ _ _ _ _ _ _ _ _ _ _ ");
            }else{
                System.out.println();
            }
        }
    }


}
