import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTests {


    @Test
    public void solvingCompleteGrid(){
        Seed seed = new Seed();
        seed.generateGrid();

        Solver solver = new Solver();
        assertTrue(solver.check(seed.getGrid(), seed.getGrid()));
    }
    @Test
    public void solvingAlmostCompleteGrid(){
        Seed seed =new Seed();
        seed.generateGrid();

        Integer[][] testGrid = seed.getGrid();

        Integer[][] changedGrid = new Integer[9][9];
        for(int i=0; i<9; i++){
            System.arraycopy(testGrid[i], 0, changedGrid[i], 0, 9);
        }
        changedGrid[0][0]=null;

        Solver solver = new Solver();
        assertTrue(solver.check(changedGrid, seed.getGrid()));


    }


    @Test
    public void solvingFromStart(){
        Seed seed =new Seed();
        seed.generateGrid();

        StartGrid start = new StartGrid();
        start.setGrid(seed.getGrid());

        Solver solver = new Solver();
        assertTrue(solver.check(start.getGrid(), seed.getGrid()));


    }


    @Test
    public void unsolvable(){

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


        Seed seed =new Seed();
        seed.generateGrid();

        StartGrid start = new StartGrid();
        start.setGrid(seed.getGrid());

        Solver solver = new Solver();
        assertFalse(solver.check(testGrid, seed.getGrid()));


    }

    @Test
    public void rowSetChangeOptions(){
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

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        assertTrue(solver.rowSet(0, 1));

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        for(int x=1; x<9; x++){
            assertNull(solver.getGrid()[0][x]);
            assertEquals( setValues, solver.getOption(x, 0));
        }


    }

    @Test
    public void rowSetChangeValues(){
        Integer[][] testGrid =new Integer[][]{
                {null, 2, 3, 4, 5, 6, 7, 8, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(8, 0);

        assertTrue(solver.rowSet(0, 1));
        assertEquals(9, solver.getGrid()[0][8]);


    }

    @Test
    public void rowSetNotValid(){
        Integer[][] testGrid =new Integer[][]{
                {null, 2, 3, 4, 5, 6, 7, 8, null},
                {null, null, null, null, null, null, null, null, 9},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(8, 0);

        assertFalse(solver.rowSet(0, 1));
        assertNull(solver.getGrid()[0][8]);


    }


    @Test
    public void colSetChangeOptions(){
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

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        assertTrue(solver.colSet(0, 1));

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        for(int y=1; y<9; y++){
            assertNull(solver.getGrid()[y][0]);
            assertEquals( setValues, solver.getOption(0, y));
        }


    }

    @Test
    public void colSetChangeValues(){
        Integer[][] testGrid =new Integer[][]{
                {null, null, null, null, null, null, null, null, null},
                {2, null, null, null, null, null, null, null, null},
                {3, null, null, null, null, null, null, null, null},
                {4, null, null, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(0, 8);

        assertTrue(solver.colSet(0, 1));
        assertEquals(9, solver.getGrid()[8][0]);


    }

    @Test
    public void colSetNotValid(){
        Integer[][] testGrid =new Integer[][]{
                {null, null, null, null, null, null, null, null, null},
                {2, null, null, null, null, null, null, null, null},
                {3, null, null, null, null, null, null, null, null},
                {4, null, null, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, 9, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(0, 8);

        assertFalse(solver.colSet(0, 1));
        assertNull(solver.getGrid()[8][0]);


    }



    @Test
    public void blockSetChangeOptions(){
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

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        assertTrue(solver.blockSet(0, 0, 1));

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        for(int x=1; x<3; x++){
            for(int y=0; y<3; y++){
                assertNull(solver.getGrid()[y][x]);
                assertEquals( setValues, solver.getOption(x, y));
            }

        }

        for(int y=1; y<3; y++){
            assertNull(solver.getGrid()[y][0]);
            assertEquals( setValues, solver.getOption(0, y));
        }


    }

    @Test
    public void blockSetChangeValues(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, null, null, null, null, null, null},
                {2, 5, 8, null, null, null, null, null, null},
                {3, 6, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(2, 2);

        assertTrue(solver.blockSet(0, 0, 1));
        assertEquals(9, solver.getGrid()[2][2]);


    }


    @Test
    public void blockNotValid(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, null, null, null, null, null, null},
                {2, 5, 8, null, null, null, null, null, null},
                {3, 6, null, 9, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);
        solver.setOptions(2, 2);

        assertFalse(solver.blockSet(0, 0, 1));
        assertNull(solver.getGrid()[2][2]);


    }

    @Test
    public void setValueChangeOptions(){
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

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        ArrayList<Integer> val = new ArrayList<>();
        val.add(1);

        solver.setOption(0, 0, val);

        assertTrue(solver.setValue(0, 0));

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        for(int x=1; x<9; x++){
            assertNull(solver.getGrid()[0][x]);
            assertEquals( setValues, solver.getOption(x, 0));
        }

        for(int y=1; y<9; y++){
            assertNull(solver.getGrid()[y][0]);
            assertEquals( setValues, solver.getOption(0, y));
        }

        for(int x=1; x<3; x++){
            for(int y=0; y<3; y++){
                assertNull(solver.getGrid()[y][x]);
                assertEquals( setValues, solver.getOption(x, y));
            }

        }

        for(int y=1; y<3; y++){
            assertNull(solver.getGrid()[y][0]);
            assertEquals( setValues, solver.getOption(0, y));
        }

        assertEquals(1, solver.getGrid()[0][0]);


    }



    @Test
    public void setValuesChangeValues(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, 8, 2, 3, 5, 6, null},
                {2, 5, 8, null, null, null, null, null, null},
                {3, 6, null, null, null, null, null, null, null},
                {4, null, null, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        ArrayList<Integer> val = new ArrayList<>();
        val.add(1);

        solver.setOption(0, 0, val);

        solver.setOptions(2, 2);
        solver.setOptions(0, 8);
        solver.setOptions(8, 0);

        assertTrue(solver.setValue(0, 0));


        assertEquals(1, solver.getGrid()[0][0]);

        assertEquals(9, solver.getGrid()[2][2]);
        assertEquals(9, solver.getGrid()[0][8]);
        assertEquals(9, solver.getGrid()[8][0]);


    }


    @Test
    public void setValuesNotValidCol(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, 8, 2, 3, 5, 6, null},
                {2, 5, 8, null, null, null, null, null, null},
                {3, 6, null, null, null, null, null, null, null},
                {4, null, null, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, 9, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        ArrayList<Integer> val = new ArrayList<>();
        val.add(1);

        solver.setOption(0, 0, val);
        solver.setOptions(0,8);

        assertFalse(solver.setValue(0, 0));
        assertNull(solver.getGrid()[8][0]);


    }

    @Test
    public void setValuesNotValidRow(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, 8, 2, 3, 5, 6, null},
                {2, 5, 8, null, null, null, null, null, 9},
                {3, 6, null, null, null, null, null, null, null},
                {4, null, null, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        ArrayList<Integer> val = new ArrayList<>();
        val.add(1);

        solver.setOption(0, 0, val);
        solver.setOptions(8, 0);

        assertFalse(solver.setValue(0, 0));
        assertNull(solver.getGrid()[0][8]);


    }



    @Test
    public void setValuesNotValidBlock(){
        Integer[][] testGrid =new Integer[][]{
                {null, 4, 7, 8, 2, 3, 5, 6, null},
                {2, 5, 8, null, null, null, null, null, null},
                {3, 6, null, null, null, null, null, null, null},
                {4, null, 9, null, null, null, null, null, null},
                {5, null, null, null, null, null, null, null, null},
                {6, null, null, null, null, null, null, null, null},
                {7, null, null, null, null, null, null, null, null},
                {8, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Solver solver = new Solver();

        solver.setGrid(testGrid);

        ArrayList<Integer> val = new ArrayList<>();
        val.add(1);

        solver.setOption(0, 0, val);
        solver.setOptions(2, 2);

        assertFalse(solver.setValue(0, 0));
        assertNull(solver.getGrid()[2][2]);


    }

}
