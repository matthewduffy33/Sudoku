import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {
    @Test
    public void refreshFileCheck() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);
        gameController.refreshFile(file);

        String fullLine;

        Scanner reader = new Scanner(new File(file));
        fullLine = reader.nextLine();
        assertEquals("", fullLine);
        assertFalse(reader.hasNextLine());

    }

    @Test
    public void flipTypeNotValid() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        assertEquals("", gameController.flipType(""));
        assertEquals("a", gameController.flipType("a"));

    }

    @Test
    public void flipTypeValid() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        assertEquals("addOption", gameController.flipType("removeOption"));
        assertEquals("removeOption", gameController.flipType("addOption"));
        assertEquals("addValue", gameController.flipType("removeValue"));
        assertEquals("removeValue", gameController.flipType("addValue"));

    }



    @Test
    public void flipFileNull() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        assertEquals("[]", gameController.flipFile(new ArrayList<>()).toString());

    }

    @Test
    public void flipFileOne() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        List<String> fileData = new ArrayList<String>();
        fileData.add("a");


        assertEquals("[a]", gameController.flipFile(fileData).toString());

    }

    @Test
    public void flipFileMultiple() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        List<String> fileData = new ArrayList<String>();
        fileData.add("a");
        fileData.add("b");
        fileData.add("c");


        assertEquals("[c, b, a]", gameController.flipFile(fileData).toString());

    }


    @Test
    public void jsonInsertOne() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        JSONObject jsonString = new JSONObject();

        assertEquals("{\"x\":1,\"y\":1,\"type\":\"changeOptions\"}", gameController.insertJSON(1, 1, "changeOptions", jsonString).toString());

    }





    @Test
    public void moveNotExist() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        assertFalse(gameController.moveExists(file));
    }


    @Test
    public void moveNotValid() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        PrintWriter fw = new PrintWriter(file);
        fw.println("1 2 3 4 5");

        fw.close();

        assertFalse(gameController.moveExists(file));
    }

    @Test
    public void moveValid() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        GameController gameController = new GameController(file, file);

        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");

        fw.close();

        assertTrue(gameController.moveExists(file));
    }


    @Test
    public void fileWriteNull() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");               //make sure these are deleted

        GameController gameController = new GameController(file, file);

        List<String> fileData = new ArrayList<>();

        gameController.writeToFile(fileData, file);

        String fullLine;
        Scanner reader = new Scanner(new File(file));
        fullLine = reader.nextLine();
        assertEquals("", fullLine);
        assertFalse(reader.hasNextLine());
    }


    @Test
    public void fileWriteOne() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");           //make sure these are deleted

        GameController gameController = new GameController(file, file);

        List<String> fileData = new ArrayList<>();
        fileData.add("test");

        gameController.writeToFile(fileData, file);

        String fullLine;
        Scanner reader = new Scanner(new File(file));

        fullLine = reader.nextLine();
        assertEquals("test", fullLine);

        fullLine = reader.nextLine();
        assertEquals("", fullLine);
        assertFalse(reader.hasNextLine());
    }


    @Test
    public void fileWriteMultiple() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";

        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");           //make sure these are deleted

        GameController gameController = new GameController(file, file);

        List<String> fileData = new ArrayList<>();
        fileData.add("addValue 1 2 3");
        fileData.add("removeValue 3 4 5");
        fileData.add("addOption 6 7 8");
        fileData.add("removeOption 1 2 9");

        gameController.writeToFile(fileData, file);
        String fullLine;
        Scanner reader = new Scanner(new File(file));
        int index = 0;

        while(reader.hasNextLine() || index > fileData.size()){
            fullLine = reader.nextLine();
            assertEquals(fileData.get(index), fullLine);
            index++;
        }

        if(index == fileData.size() + 1){
            fail();
        }

        assertFalse(reader.hasNextLine());
    }


    @Test
    public void changeFileNormalAddValue() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");
        fw.println("addValue 1 2 3");
        fw.println("addValue 6 7 8");
        fw.println("addValue 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"addValue\",\"value\":5}", gameController.changeFile(file, destination, false));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addValue 1 2 3", fullLine);

       fullLine = reader.nextLine();
       assertEquals("addValue 6 7 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addValue 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("addValue 3 4 5", fullLine);



    }

    @Test
    public void changeFileFlipAddValue() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addValue 3 4 5");
        fw.println("addValue 1 2 3");
        fw.println("addValue 6 7 8");
        fw.println("addValue 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"removeValue\",\"value\":5}", gameController.changeFile(file, destination, true));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addValue 1 2 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addValue 6 7 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addValue 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("addValue 3 4 5", fullLine);

    }


    @Test
    public void changeFileNormalRemoveValue() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("removeValue 3 4 5");
        fw.println("removeValue 1 2 3");
        fw.println("removeValue 6 7 8");
        fw.println("removeValue 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"removeValue\",\"value\":5}", gameController.changeFile(file, destination, false));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("removeValue 1 2 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeValue 6 7 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeValue 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeValue 3 4 5", fullLine);

    }

    @Test
    public void changeFileFlipRemoveValue() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("removeValue 3 4 5");
        fw.println("removeValue 1 2 3");
        fw.println("removeValue 6 7 8");
        fw.println("removeValue 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"addValue\",\"value\":5}", gameController.changeFile(file, destination, true));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("removeValue 1 2 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeValue 6 7 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeValue 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeValue 3 4 5", fullLine);

    }

    @Test
    public void changeFileNormalAddOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addOption 3 4 5");
        fw.println("addOption 3 4 3");
        fw.println("addOption 3 4 8");
        fw.println("addOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,3,4,5,6,7,8,9]}", gameController.changeFile(file, destination, false));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 5", fullLine);

    }

    @Test
    public void changeFileFlipAddOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addOption 3 4 5");
        fw.println("addOption 3 4 3");
        fw.println("addOption 3 4 8");
        fw.println("addOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,4,6,7,9]}", gameController.changeFile(file, destination, true));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 5", fullLine);

    }

    @Test
    public void changeFileNormalRemoveOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("removeOption 3 4 5");
        fw.println("removeOption 3 4 3");
        fw.println("removeOption 3 4 8");
        fw.println("removeOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,4,6,7,9]}", gameController.changeFile(file, destination, false));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("removeOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 5", fullLine);

    }


    @Test
    public void changeFileFlipRemoveOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("removeOption 3 4 5");
        fw.println("removeOption 3 4 3");
        fw.println("removeOption 3 4 8");
        fw.println("removeOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,3,4,5,6,7,8,9]}", gameController.changeFile(file, destination, true));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("removeOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 5", fullLine);

    }




    @Test
    public void changeFileNormalMixedOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addOption 3 4 5");
        fw.println("addOption 3 4 3");
        fw.println("removeOption 3 4 5");
        fw.println("addOption 3 4 8");
        fw.println("removeOption 3 4 3");
        fw.println("addOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,4,6,7,8,9]}", gameController.changeFile(file, destination, false));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 5", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 5", fullLine);


    }

    @Test
    public void changeFileFlipMixedOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        PrintWriter fw = new PrintWriter(file);
        fw.println("addOption 3 4 5");
        fw.println("addOption 3 4 3");
        fw.println("removeOption 3 4 5");
        fw.println("addOption 3 4 8");
        fw.println("removeOption 3 4 3");
        fw.println("addOption 5 4 1");
        fw.close();



        assertEquals( "{\"x\":3,\"y\":4,\"type\":\"changeOptions\",\"options\":[1,2,3,4,5,6,7,9]}", gameController.changeFile(file, destination, true));

        Scanner reader = new Scanner(new File(file));
        String fullLine;

        fullLine = reader.nextLine();
        assertEquals("addOption 5 4 1", fullLine);


        reader = new Scanner(new File(destination));

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 8", fullLine);

        fullLine = reader.nextLine();
        assertEquals("removeOption 3 4 5", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 3", fullLine);

        fullLine = reader.nextLine();
        assertEquals("addOption 3 4 5", fullLine);

    }


    @Test
    public void changeFileEmpty() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);


        assertEquals( "error", gameController.changeFile(file, destination, true));


    }

    @Test
    public void changeFileOutBounds() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);

        PrintWriter fw = new PrintWriter(file);
        fw.println("removeOption 10 3 5");
        fw.close();


        assertEquals( "error", gameController.changeFile(file, destination, true));



        gameController = new GameController(file, destination);

        fw = new PrintWriter(file);
        fw.println("removeOption 3 10 5");
        fw.close();


        assertEquals( "error", gameController.changeFile(file, destination, true));


        gameController = new GameController(file, destination);

        fw = new PrintWriter(file);
        fw.println("removeOption 3 5 10");
        fw.close();


        assertEquals( "error", gameController.changeFile(file, destination, true));


    }

    @Test
    public void changeFileNotOption() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);

        PrintWriter fw = new PrintWriter(file);
        fw.println("remove 2 3 5");
        fw.close();


        assertEquals( "error", gameController.changeFile(file, destination, true));

    }


    @Test
    public void changeFileNotRightSize() throws FileNotFoundException {
        String file = "src/test/files/testMoves.txt";
        String destination = "src/test/files/testRedo.txt";

        GameController gameController = new GameController(file, destination);

        PrintWriter fw = new PrintWriter(file);
        fw.println("removeValue 1 2 3 4");
        fw.close();


        assertEquals( "error", gameController.changeFile(file, destination, true));

    }



}
