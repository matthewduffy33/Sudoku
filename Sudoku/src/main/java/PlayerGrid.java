import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class PlayerGrid extends Grid {

    PlayerGrid(){
        super ();
    }

    public void addValue(int x, int y, int value){  //add values to grid
        grid[y][x]=value;
    }

    public void removeValue(int x, int y){
        grid[y][x]=null;
    }

    public void clear(){
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                grid[y][x]=null;
            }
        }
    }

    public ArrayList<Integer> addOption(int x, int y, int value){
        if(!options[y][x].contains(value)){   //if not already an option then add it and sort the options
            options[y][x].add(value);
            Collections.sort(options[y][x]);
        }
        return options[y][x];
    }


    public ArrayList<Integer> removeOption(int x, int y, int value){
        if(options[y][x].contains(value)){         //if an option then remove the options
            options[y][x].remove(options[y][x].indexOf(value));
        }
        return options[y][x];
    }




    public boolean completed(Integer[][] startGrid){

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){  //loop through grid
                if(startGrid[y][x] == null && grid[y][x] == null){  //if both grids have an empty square then not complete
                    return false;
                }

            }
        }
        return true;
    }



    public boolean correct(Integer[][] startGrid, Integer[][] answer){

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){   //loops through grid
                if(!startGrid[y][x].equals(answer[y][x]) && !grid[y][x].equals(answer[y][x])){  //if the players grid does not equal the answer then not correct
                    return false;

                }
            }
        }
        return true;
    }


    public JSONObject hint(Integer[][] startGrid, Integer[][] answer){

        JSONObject hint= new JSONObject();

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                if(startGrid[y][x] == null && grid[y][x] == null){   //find first blank space and give a hint
                    grid[y][x]= answer[y][x];
                    hint.put("x", x);
                    hint.put("y",y);
                    hint.put("value", answer[y][x]);
                    return hint;
                }
            }
        }

        //no blank spaces exist then

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                if(startGrid[y][x] == null && !grid[y][x].equals(answer[y][x])){  //find first incorrect value and replace with hint
                    grid[y][x] = answer[y][x];
                    hint.put("x", x);
                    hint.put("y",y);
                    hint.put("value", answer[y][x]);
                    return hint;
                }
            }
        }

        return hint;
    }





    public Boolean isMistake(Integer[][] answer, int x, int y){
        return grid[y][x] != null && !grid[y][x].equals(answer[y][x]); //if not equal to answer then mistake
    }


    public ArrayList<String> mistakesArray(Integer[][] answer){
        ArrayList<String> mistakes = new ArrayList<>();
        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){   //loops through grid and gets an array of all mistakes
                if(grid[y][x] != null && !grid[y][x].equals(answer[y][x])){
                    mistakes.add("x"+x + "y"+y);
                }
            }
        }

        return mistakes;
    }



}
