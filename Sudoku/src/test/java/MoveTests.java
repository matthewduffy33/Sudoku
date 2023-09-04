import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTests {

    @Test
    public void addValues(){
        PlayerGrid game = new PlayerGrid();

        game.addValue(0, 0, 1);
        game.addValue(0, 8, 2);
        game.addValue(8, 0, 3);
        game.addValue(8, 8, 4);
        game.addValue(4, 4, 5);

        assertEquals(1, game.getGrid()[0][0]);
        assertEquals(2, game.getGrid()[8][0]);
        assertEquals(3, game.getGrid()[0][8]);
        assertEquals(4, game.getGrid()[8][8]);
        assertEquals(5, game.getGrid()[4][4]);

        assertEquals(5, game.amount());

    }

    @Test
    public void removeValues(){
        PlayerGrid game = new PlayerGrid();

        game.addValue(0, 0, 1);
        game.addValue(0, 8, 2);
        game.addValue(8, 0, 3);
        game.addValue(8, 8, 4);
        game.addValue(4, 4, 5);

        assertEquals(1, game.getGrid()[0][0]);
        assertEquals(2, game.getGrid()[8][0]);
        assertEquals(3, game.getGrid()[0][8]);
        assertEquals(4, game.getGrid()[8][8]);
        assertEquals(5, game.getGrid()[4][4]);

        assertEquals(5, game.amount());

        game.removeValue(0, 0);
        game.removeValue(0, 8);
        game.removeValue(8, 0);
        game.removeValue(8, 8);
        game.removeValue(4, 4);

        assertNull(game.getGrid()[0][0]);
        assertNull(game.getGrid()[0][8]);
        assertNull(game.getGrid()[8][0]);
        assertNull(game.getGrid()[8][8]);
        assertNull(game.getGrid()[4][4]);

        assertEquals(0, game.amount());

    }


    @Test
    public void clearNormal(){
        PlayerGrid game = new PlayerGrid();

        game.addValue(0, 0, 1);
        game.addValue(0, 8, 2);
        game.addValue(8, 0, 3);
        game.addValue(8, 8, 4);
        game.addValue(4, 4, 5);

        assertEquals(1, game.getGrid()[0][0]);
        assertEquals(2, game.getGrid()[8][0]);
        assertEquals(3, game.getGrid()[0][8]);
        assertEquals(4, game.getGrid()[8][8]);
        assertEquals(5, game.getGrid()[4][4]);

        assertEquals(5, game.amount());

        game.clear();

        Integer[][] gameGrid = game.getGrid();

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                assertNull(gameGrid[y][x]);
            }
        }

        assertEquals(0, game.amount());

    }


    @Test
    public void clearAll(){
        PlayerGrid game = new PlayerGrid();

        Seed seed = new Seed();
        seed.generateGrid();

        game.setGrid(seed.getGrid());

        assertEquals(81, game.amount());

        game.clear();

        Integer[][] gameGrid = game.getGrid();

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                assertNull(gameGrid[y][x]);
            }
        }

        assertEquals(0, game.amount());

    }



    @Test
    public void RemoveNew(){
        PlayerGrid game = new PlayerGrid();

        ArrayList<Integer> opt = new ArrayList<>();

        opt.add(1);
        opt.add(2);
        opt.add(3);

        opt.add(4);
        opt.add(5);
        opt.add(6);

        opt.add(7);
        opt.add(8);
        opt.add(9);


        for(int i=1; i<=9; i++){
            opt.remove(0);
            assertEquals(opt, game.removeOption(0,0, i));
        }

    }


    @Test
    public void RemoveRepeat(){
        PlayerGrid game = new PlayerGrid();

        ArrayList<Integer> opt = new ArrayList<>();

        opt.add(2);
        opt.add(3);

        opt.add(4);
        opt.add(5);
        opt.add(6);

        opt.add(7);
        opt.add(8);
        opt.add(9);


        for(int i=1; i<=3; i++){
            assertEquals(opt, game.removeOption(0,0, 1));
        }

    }


    @Test
    public void AddNew(){
        PlayerGrid game = new PlayerGrid();

        ArrayList<Integer> opt = new ArrayList<>();
        game.setOption(0, 0, opt);


        for(int i=1; i<=9; i++){
            opt.add(i);
            assertEquals(opt, game.addOption(0,0, i));
        }

    }

    @Test
    public void notComplete(){

        Integer[][] testGrid =new Integer[][]{
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};


        PlayerGrid game = new PlayerGrid();

        assertFalse(game.completed(testGrid));


    }


    @Test
    public void fullyComplete(){
        PlayerGrid game = new PlayerGrid();

        Seed seed =new Seed();
        seed.generateGrid();

        assertTrue(game.completed(seed.getGrid()));

    }


    @Test
    public void correct(){
        PlayerGrid game = new PlayerGrid();

        Seed seed =new Seed();
        seed.generateGrid();

        assertTrue(game.correct(seed.getGrid(), seed.getGrid()));

    }

    @Test
    public void notCorrect(){

        Integer[][] testGrid =new Integer[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};

        PlayerGrid game = new PlayerGrid();
        game.setGrid(testGrid);

        Seed seed =new Seed();
        seed.generateGrid();

        assertFalse(game.correct(testGrid, seed.getGrid()));

    }



    @Test
    public void hintNoHint(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(seed.getGrid());

        StartGrid start = new StartGrid();

        JSONObject hintString= new JSONObject();

        assertEquals(hintString, game.hint(start.getGrid(), seed.getGrid()));


    }

    @Test
    public void hintCheck(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();

        StartGrid start = new StartGrid();

        Integer[][] seedGrid = seed.getGrid();

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                JSONObject hintString= new JSONObject();
                hintString.put("x", x);
                hintString.put("y", y);
                hintString.put("value", seedGrid[y][x]);

                assertEquals(hintString, game.hint(start.getGrid(), seed.getGrid()));

            }
        }


    }


    @Test
    public void hintReplaceSetValue(){
        Integer[][] testGrid =new Integer[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(testGrid);

        StartGrid start = new StartGrid();

        Integer[][] seedGrid = seed.getGrid();

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                if(seedGrid[y][x] != 1){
                    JSONObject hintString= new JSONObject();
                    hintString.put("x", x);
                    hintString.put("y", y);
                    hintString.put("value", seedGrid[y][x]);

                    assertEquals(hintString, game.hint(start.getGrid(), seed.getGrid()));
                }


            }
        }

    }


    @Test
    public void mistakeNull(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                assertFalse(game.isMistake(seed.getGrid(), x, y));
            }
        }


    }

    @Test
    public void notAMistake(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(seed.getGrid());

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                assertFalse(game.isMistake(seed.getGrid(), x, y));
            }
        }


    }

    @Test
    public void isAMistake(){

        Integer[][] testGrid =new Integer[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(testGrid);

        Integer[][] seedGrid = seed.getGrid();

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                if(seedGrid[y][x] != 1){
                    assertTrue(game.isMistake(seed.getGrid(), x, y));
                }

            }
        }


    }

    @Test
    public void nullMistakes(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();

        ArrayList<String> mistakes = new ArrayList<>();
        assertEquals(mistakes, game.mistakesArray(seed.getGrid()));


    }


    @Test
    public void noMistakes(){

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(seed.getGrid());

        ArrayList<String> mistakes = new ArrayList<>();
        assertEquals(mistakes, game.mistakesArray(seed.getGrid()));


    }


    @Test
    public void areMistakes(){

        Integer[][] testGrid =new Integer[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1}};

        Seed seed =new Seed();
        seed.generateGrid();

        PlayerGrid game = new PlayerGrid();
        game.setGrid(testGrid);

        Integer[][] seedGrid = seed.getGrid();

        ArrayList<String> mistakes = new ArrayList<>();

        for(int x = 0; x<9; x++){
            for(int y = 0; y<9; y++){
                if(seedGrid[y][x] != 1){
                    mistakes.add("x"+x + "y"+y);
                }

            }
        }

        assertEquals(mistakes, game.mistakesArray(seed.getGrid()));


    }


}
