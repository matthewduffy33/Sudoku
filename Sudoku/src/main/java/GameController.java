import io.jooby.ModelAndView;
import io.jooby.annotations.*;
import kong.unirest.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;



@Path("/sudoku")
public class GameController {

    Seed seed; //seed is answer to puzzle
    StartGrid start = new StartGrid();   //start grid is the grid the player starts with (generated from the seed)
    PlayerGrid playerBoard = new PlayerGrid();  //player board is what stores the guesses the player makes
    Boolean showMistakes = false;  //boolean value of if the user wishes to show the mistakes they have made
    String movesFile;  //file showing the moves that have been made
    String redoFile;  //file storing what functions have been undone due to the undo button (refreshes once a new move is made)

    public GameController(String moves, String redo) throws FileNotFoundException {

        seed=new Seed();

        seed.generateGrid();

        start.generate(seed.getGrid());

        movesFile = moves;
        redoFile = redo;

        refreshFile(movesFile);  //ensures no junk is already in the file
        refreshFile(redoFile);

    }

    @GET
    public ModelAndView welcome() {
        return new ModelAndView("Sudoku.html");
    } //displays html welcome page

    @GET("/play")
    public ModelAndView play()  {

        ModelAndView play = new ModelAndView("game.hbs").put("start", start.getGrid()); //uses handlebars to display the grid and other details

        play.put("playerboard", playerBoard.getGrid());

        play.put("options", playerBoard.getOptions());

        if(showMistakes){ //if the player wishes to view mistakes then their mistakes are loaded and sent to the handlebars file
            ArrayList<String> mistakes= playerBoard.mistakesArray(seed.getGrid());
            play.put("mistakes", mistakes);
            play.put("showMistakes", true);
        }

        play.put("undo", moveExists(movesFile));  //checks if an undo can happen

        play.put("redo", moveExists(redoFile));  //checks if a redo undo can happen

        return play;
    }



    @POST("/play")
    public ModelAndView formPlay(@FormParam String option) throws IOException { //version of play that deals with functions that change the state of the game completely

        switch (option) {
            case "newGame":   //if new game then reset everything
                seed = new Seed();

                seed.generateGrid();

                start.generate(seed.getGrid());

                playerBoard.setGrid(start.getGrid());
                playerBoard.clear();
                playerBoard.resetOptions();

                refreshFile(movesFile);
                refreshFile(redoFile);

                break;

            case "reset":  //if reset then clear the moves and options the player has made

                playerBoard.clear();
                playerBoard.resetOptions();

                refreshFile(movesFile);
                refreshFile(redoFile);

                break;
        }

        return play();

    }


    @GET("/play/add")  //adding a value to board and returns a json string
    public JSONObject changeGrid(@QueryParam Integer x, @QueryParam Integer y, @QueryParam Integer value) throws IOException {
        List<String> data =new ArrayList<>();
        JSONObject response = new JSONObject();

        if(value!=0){ //if the value is not 0 then not removing value
            playerBoard.addValue(x, y, value); //run the addValue function and then write to the moves file
            data.add("addValue"+ " "+ x +" "+ y +" "+ value);
            writeToFile(data, movesFile);

            if(showMistakes){  //if the user wishes to see mistakes then check if the guess is correct or not and display the result
                response.put("isMistake", playerBoard.isMistake(seed.getGrid(), x, y));
            }else{
                response.put("isMistake", false);
            }

            if(playerBoard.completed(start.getGrid())) { //if this guess makes the grid complete then check if the full board is correct or not and display the result
                response.put("correct", playerBoard.correct(start.getGrid(), seed.getGrid()));
            }
        }else{
            playerBoard.removeValue(x, y);  //remove values from board and then write to file
            data.add("removeValue" + " "+ x +" "+ y +" "+ value);
            writeToFile(data, movesFile);

            response.put("isMistake", false);
        }
        return response; //return the json object

    }

    @GET("/play/option")
    public ArrayList<Integer> changeOptions(@QueryParam String func, @QueryParam Integer x, @QueryParam Integer y, @QueryParam Integer value) throws IOException {

        List<String> data = new ArrayList<>();
        ArrayList<Integer> options= new ArrayList<>();

        if(func.equals("removeOption")){
            options=playerBoard.removeOption(x, y, value);//remove an option and then write to file
            data.add("removeOption"+ " "+ x +" "+ y +" "+ value);
            writeToFile(data, movesFile);

        }else if(func.equals("addOption")){
            options=playerBoard.addOption(x, y, value);  //add an option and then write to file
            data.add("addOption"+ " "+ x +" "+ y +" "+ value);
            writeToFile(data, movesFile);
        }

        return options; //returns the options left now after the change

    }

    @GET("/play/hint")
    public JSONObject hint(){

        JSONObject hint = playerBoard.hint(start.getGrid(), seed.getGrid());  //gets the hint

        if(playerBoard.completed(start.getGrid())){  //if the hint causes the grid to be complete then check if the grid is correct or not
            hint.put("correct",playerBoard.correct(start.getGrid(), seed.getGrid()));
        }

        return hint;

    }


    @GET("/play/undo")
    public String undo(){
        return changeFile(movesFile, redoFile, true);
    }  //reads the previously made moves, flips them and then redoes them, then puts the move into the redo file

    @GET("/play/checkUndo")
    public Boolean checkUndo(){
        return moveExists(movesFile);
    }  //checks if any moves have been made



    @GET("/play/redo")
    public String redo() {
        return changeFile(redoFile, movesFile, false);
    }  //reads the moves from the redo file, does them and adds them back to moves file

    @GET("/play/checkRedo")
    public Boolean checkRedo(){
        return moveExists(redoFile);
    } //checks if redo file has anything in it



    @GET("/play/getOptions")
    public ArrayList<Integer> getOptions(@QueryParam Integer x, @QueryParam Integer y) {

        ArrayList<Integer> options;
        options=playerBoard.getOption(x, y);

        return options;  //simply gets the options of a specific item in a grid

    }

    @GET("/play/flipMistakes")
    public JSONObject flipMistakes() {
        showMistakes= !showMistakes; //flips the current state
        JSONObject response = new JSONObject();

        if(showMistakes){ //if the mistakes are going to be shown then return all mistakes
            response.put("showMistakes", playerBoard.mistakesArray(seed.getGrid()));
        }else{ //if not then return simply "hide"
            response.put("hideMistakes", "hide");
        }

        return response;
    }


    boolean moveExists(String file) {
        String fullLine;
        String[] splitMoveDetails;

        try {
            // Read the existing file data and store it in a list
            Scanner reader = new Scanner(new File(file));
            if(reader.hasNextLine()) {

                fullLine = reader.nextLine();
                splitMoveDetails = fullLine.split(" ");


                return splitMoveDetails.length == 4; //checks read in data is in right state and if so then undo is possible

            }
            reader.close();
            return false; //undo is not possible if nothing is in file

        } catch (NoSuchElementException | FileNotFoundException e) {
            return false;

        }
    }



    public String changeFile(String sourceFile, String destinationFile, Boolean flip) {
        String fullLine;
        String initialType = null;
        String type = null;

        int x = 0;
        int y = 0;
        int value;

        String[] splitMoveDetails;
        boolean options =true;
        JSONObject overallJSON = new JSONObject();
        List<String> fileData = new ArrayList<>();

        List<String> redoData = new ArrayList<>();

        int index = 0;

        try {
            Scanner reader = new Scanner(new File(sourceFile));
            while (reader.hasNextLine() && options) { //loops through the file until the end, or it is not reading in an option
                //changes to options for the same place in the grid are grouped together and thus the loop keeps running checking when the first value is an option to make sure to read in all the grouped values

                fullLine = reader.nextLine();

                splitMoveDetails = fullLine.split(" ");  //reads in the line and splits it by spaces

                JSONObject innerJSON = new JSONObject();


                if (splitMoveDetails.length != 4) {  //if not of the correct size then error occurred

                    if(overallJSON.isEmpty()){  //if the json string is empty (no moves already extracted) then return error otherwise return whatever moves have already been found
                        return "error";
                    }else{
                        break;
                    }

                }else {

                    if(index==0){  //if it is the first time through the loop then check the values are valid
                        initialType = splitMoveDetails[0];
                        x = Integer.parseInt(splitMoveDetails[1]);
                        y = Integer.parseInt(splitMoveDetails[2]);
                        value = Integer.parseInt(splitMoveDetails[3]);

                        if(x<0 || x>=9 || y<0 || y>=9 || value <=0 || value>9){
                            return "error";
                        }

                        if(flip){  //if you are meant to flip the type then flip it
                            type = flipType(initialType);
                        }else{
                            type=initialType;
                        }
                    }


                    //after the first loop checks that x and y coordinates are still the same and that the type is the same as the type initially
                    if(index>0 && (!initialType.equals(splitMoveDetails[0]) || x != Integer.parseInt(splitMoveDetails[1]) || y != Integer.parseInt(splitMoveDetails[2]))){
                        fileData.add(fullLine); //if not then time to stop
                        break;
                    }

                    value = Integer.parseInt(splitMoveDetails[3]);  //get the value of the change


                    switch (type) {
                        case "removeOption":

                            innerJSON.put("type", "removeOption");
                            innerJSON.put("options", playerBoard.removeOption(x, y, value)); //puts the result in a json format and wraps it within another json object
                            overallJSON = insertJSON(x, y, value, index, innerJSON, overallJSON);

                            break;
                        case "addOption":

                            innerJSON.put("type", "addOption");
                            innerJSON.put("options", playerBoard.addOption(x, y, value));  //puts the result in a json format and wraps it within another json object
                            overallJSON = insertJSON(x, y, value, index, innerJSON, overallJSON);

                            break;
                        case "removeValue":

                            options = false;//not changing options so no need to keep looping
                            playerBoard.removeValue(x, y);
                            innerJSON.put("type", "removeValue");  //puts the result in a json format and wraps it within another json object
                            overallJSON = insertJSON(x, y, value, index, innerJSON, overallJSON);

                            break;
                        case "addValue":

                            options = false;//not changing options so no need to keep looping
                            playerBoard.addValue(x, y, value);
                            innerJSON.put("type", "addValue");
                            overallJSON = insertJSON(x, y, value, index, innerJSON, overallJSON);  //puts the result in a json format and wraps it within another json object

                            break;
                        default:  //not a valid type so error occurred
                            return "error";

                    }

                    redoData.add(fullLine);  //adds the line of what has already been processed

                    index++;

                }

            }

            while (reader.hasNextLine()) {  //gets the rest of the file being read
                fileData.add(reader.nextLine());
            }
            reader.close();

            refreshFile(sourceFile);  //refresh the source file

            writeToFile(fileData, sourceFile);   //puts the unprocessed lines back in the source file

            writeToFile(redoData, destinationFile);  //puts the processed lines in the source file


        } catch (NoSuchElementException | FileNotFoundException e) {
            return "error";

        }

        return overallJSON.toString();
    }



    public String flipType(String type){  //swaps the adds to removes and vice versa
        switch (type) {
            case "addOption":
                return "removeOption";

            case "removeOption":
                return "addOption";

            case "addValue":
                return "removeValue";

            case "removeValue":
                return "addValue";
        }
        return type;
    }



    public JSONObject insertJSON(int x, int y, int value, int index, JSONObject innerJSON, JSONObject overallJSON){
        innerJSON.put("x", x);
        innerJSON.put("y", y);
        innerJSON.put("value", value);
        overallJSON.put("index"+index, innerJSON);

        return overallJSON;
    }



    public void writeToFile(List<String> fileData, String file) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(file));
        while (reader.hasNextLine()) {  //gets everything already in the file
            fileData.add(reader.nextLine());
        }


        // Write the updated data back to the file
        PrintWriter fw = new PrintWriter(file);
        if(!fileData.isEmpty()) {
            for (String line : fileData) {
                fw.println(line);

            }
        }
        fw.close();



    }

    public void refreshFile(String file) throws FileNotFoundException {
        PrintWriter fw = new PrintWriter(file); //opens a file and makes it blank
        fw.println("");
        fw.close();

    }

}
