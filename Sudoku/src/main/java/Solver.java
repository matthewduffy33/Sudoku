
public class Solver extends Grid {



    Solver(){
        super ();
    }

    public boolean check(Integer[][] sudokuGrid, Integer[][] answer){

        this.setGrid(sudokuGrid);

        boolean solvable = answer();

        if(solvable){   //if solvable and solves to right answer then return true
            return this.gridEqual(answer);

        }else{
            return false;
            
        }

    }




    public boolean answer(){
        boolean valid;

        for(int x=0; x<9; x++){     //loop through the grid
            for(int y=0; y<9; y++){

                if(this.grid[y][x]==null){  //if nothing is in the grid

                    setOptions(x, y);          //set the options to what they could be

                    if(options[y][x].size()==0){     // if there is nothing that the space can be then puzzle is unsolvable
                        return false;
                    }

                    if(options[y][x].size()==1){    //if there is one option then set the space to that value
                        valid =setValue(x, y);

                        if(!valid){
                            return false;
                        }

                    }
                }


            }

        }

        return true;
    }



    public boolean setValue(int x, int y){

        int value = options[y][x].get(0);  //place the option into the grid space
        grid[y][x]=value;

        if(!rowSet(y, value)){  //goes through the rows, cols and blocks and removes the values from the options in the row, column and block
            return false;
        }

        if(!colSet(x, value)){
            return false;
        }

        return blockSet(x, y, value);


    }



    public boolean colSet(int x, int value){
        boolean valid =true;
        for(int i=0; i<9; i++){   //loops through a column

            if(options[i][x].contains(value) && this.grid[i][x]==null){    //if the space contains the option then remove it
                options[i][x].remove(options[i][x].indexOf(value));

                if(options[i][x].size()==1 ){  //if only one option remains then set the value
                    valid = this.setValue(x, i);
                }

                if(options[i][x].size()==0 || !valid){  //if there are no options then not solvable
                    return false;
                }
            }
        }

        return true;
    }



    public boolean rowSet(int y, int value){
        boolean valid =true;

        for(int j=0; j<9; j++){  //loops through row

            if(options[y][j].contains(value) && this.grid[y][j]==null) {//if the space contains the option then remove it
                options[y][j].remove(options[y][j].indexOf(value));

                if(options[y][j].size()==1 ){   //if only one option remains then set the value
                    valid=this.setValue(j, y);
                }

                if(options[y][j].size()==0 || !valid){   //if there are no options then not solvable
                    return false;
                }
            }

        }

        return true;
    }



    public boolean blockSet(int x, int y, int value){
        boolean valid =true;
        int newX = (x/3)*3;
        int newY = (y/3)*3;

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){ //loops through block

                if(options[j+newY][i+newX].contains(value) && this.grid[j+newY][i+newX]==null){        //if the space contains the option then remove it
                    options[j+newY][i+newX].remove(options[j+newY][i+newX].indexOf(value));

                    if(options[j+newY][i+newX].size()==1){   //if only one option remains then set the value
                        valid=this.setValue(i+newX, j+newY);

                    }else if(options[j+newY][i+newX].size()==0|| !valid){   //if there are no options then not solvable

                        return false;
                    }
                }

            }
        }

        return true;
    }


}
