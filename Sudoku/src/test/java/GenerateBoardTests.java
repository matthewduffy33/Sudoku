import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateBoardTests{


    @Test
    public void generateSeedBlock(){
        Seed seed = new Seed();
        seed.generateGrid();

        Integer[] values = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        Integer[] actual = seed.block(0,0);      //checking one of each number exists in every block
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(0,3);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(0,6);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(3,0);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(3,3);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(3,6);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(6,0);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(6,3);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.block(6,6);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

    }


    @Test
    public void generateSeedRow(){
        Seed seed = new Seed();
        seed.generateGrid();

        Integer[] values = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        Integer[] actual = seed.row(0);      //checking one of each number exists in every row
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(1);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(2);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(3);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(4);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(5);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(6);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(7);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.row(8);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);


    }


    @Test
    public void generateSeedCol(){
        Seed seed = new Seed();
        seed.generateGrid();

        Integer[] values = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        Integer[] actual = seed.column(0);      //checking one of each number exists in every col
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(1);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(2);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(3);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(4);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(5);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(6);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(7);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);

        actual = seed.column(8);
        Arrays.sort(actual);
        assertArrayEquals(values, actual);


    }

    @Test
    public void randomValuesAll(){

        Random rand = new Random();

        Seed seed = new Seed();

        seed.randomValue(rand, 0, 0);

        Integer[][] testGrid = seed.getGrid();

        assertNotNull(testGrid[0][0]);

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(1);
        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        assertTrue(setValues.contains(testGrid[0][0]));
    }


    @Test
    public void randomValuesOne(){

        Random rand = new Random();

        Seed seed = new Seed();

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(1);

        seed.setOption(0,0, setValues);

        seed.randomValue(rand, 0, 0);

        Integer[][] testGrid = seed.getGrid();

        assertNotNull(testGrid[0][0]);

        assertEquals(1, testGrid[0][0]);
    }


    @Test
    public void setOptionsAll(){

        Seed seed = new Seed();

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(1);
        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        seed.setOptions(0, 0);

        assertEquals(setValues, seed.getOption(0, 0));
    }


    @Test
    public void setOptionsNormal(){

        Integer[][] testGrid =new Integer[][]{
                {9, 8, 7, 3, 2, 1, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Seed seed = new Seed();
        seed.setGrid(testGrid);

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(5);
        setValues.add(6);

        seed.setOptions(1, 2);

        assertEquals(setValues, seed.getOption(1, 2));
    }

    @Test
    public void setOptionsOne(){

        Integer[][] testGrid =new Integer[][]{
                {9, 8, 7, 3, 2, 1, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Seed seed = new Seed();
        seed.setGrid(testGrid);

        ArrayList<Integer> setValues = new ArrayList<>();
        setValues.add(6);

        seed.setOptions(2, 2);

        assertEquals(setValues, seed.getOption(2, 2));
    }


    @Test
    public void setOptionsNone(){

        Integer[][] testGrid =new Integer[][]{
                {9, 8, 7, 3, 2, 1, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {2, 1, 4, 5, 7, 8, 6, 9, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Seed seed = new Seed();
        seed.setGrid(testGrid);

        seed.setOptions(8, 3);

        assertEquals(new ArrayList<>(), seed.getOption(8, 3));
    }


    @Test
    public void backTrackingNormal(){

        Integer[][] testGrid =new Integer[][]{
                {9, 8, 7, 3, 2, 1, 4, 5, 6},
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Seed seed = new Seed();
        seed.setGrid(testGrid);

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(1);
        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        seed.setOptions(2, 2);

        seed.backtrack(2, 2);

        assertEquals(setValues, seed.getOption(2, 2));
    }

    @Test
    public void backTrackingExtreme(){

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

        Seed seed = new Seed();
        seed.setGrid(testGrid);

        ArrayList<Integer> setValues = new ArrayList<>();

        setValues.add(1);
        setValues.add(2);
        setValues.add(3);

        setValues.add(4);
        setValues.add(5);
        setValues.add(6);

        setValues.add(7);
        setValues.add(8);
        setValues.add(9);

        seed.setOptions(2, 2);

        seed.backtrack(2, 2);

        assertEquals(setValues, seed.getOption(2, 2));
    }


    @Test
    public void generateStart(){

        Seed seed = new Seed();
        seed.generateGrid();

        StartGrid start = new StartGrid();
        start.generate(seed.getGrid());

        Solver solver = new Solver();

        assertTrue(solver.check(start.getGrid(), seed.getGrid()));
        assertEquals(30, start.amount());
    }



}