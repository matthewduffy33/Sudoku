import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GridTests {

    @Test
    public void gridCreation(){

        Grid grid = new Grid();

        Integer[][] getGrid = grid.getGrid();

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                assertNull(getGrid[y][x]);
            }
        }

        ArrayList<Integer>[][] options = grid.getOptions();
        ArrayList<Integer> opt = new ArrayList<>();

        for(int z=1; z<=9; z++){
            opt.add(z);
        }


        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                assertEquals(opt, options[y][x]);
            }
        }
    }

    @Test
    public void testGridSet(){

        Random rand = new Random();
        Integer[][] testGrid =new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                testGrid[y][x] = (rand.nextInt(9))+1;
            }
        }

        Grid grid = new Grid();
        grid.setGrid(testGrid);

        Integer[][] setGrid = grid.getGrid();

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                assertEquals(testGrid[y][x], setGrid[y][x]);
            }
        }

    }


    @Test
    public void testGridEquals(){

        Random rand = new Random();
        Integer[][] testGrid =new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                testGrid[y][x] = (rand.nextInt(9))+1;
            }
        }

        Grid grid = new Grid();
        grid.setGrid(testGrid);


        assert(grid.gridEqual(testGrid));
    }

    @Test
    public void testGridNotEquals(){
        Random rand = new Random();
        Integer[][] testGrid =new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                testGrid[y][x] = (rand.nextInt(9))+1;
            }
        }

        Integer[][] blankGrid = new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Grid grid = new Grid();
        grid.setGrid(testGrid);
        assertFalse(grid.gridEqual(blankGrid));
    }

    @Test
    public void amountInGridNone(){

        Grid grid = new Grid();
        assertEquals(0, grid.amount());
    }


    @Test
    public void amountInGridNormal(){

        Random rand = new Random();
        Integer[][] testGrid =new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        for(int x=0; x<3; x++){
            for(int y=0; y<9; y++){
                testGrid[y][x] = (rand.nextInt(9))+1;
            }
        }

        Grid grid = new Grid();
        grid.setGrid(testGrid);

        assertEquals(grid.amount(),27);
    }

    @Test
    public void amountInGridFull(){

        Random rand = new Random();
        Integer[][] testGrid =new Integer[][]{{null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        for(int x=0; x<9; x++){
            for(int y=0; y<9; y++){
                testGrid[y][x] = (rand.nextInt(9))+1;
            }
        }

        Grid grid = new Grid();
        grid.setGrid(testGrid);

        assertEquals(81, grid.amount());
    }

    @Test
    public void rowNull(){
        Grid grid = new Grid();

        Integer[] nullArray = new Integer[]{null, null, null, null, null, null, null, null, null};

        for(int y=0; y<=8; y++){
            assertArrayEquals(nullArray, grid.row(y));

        }

    }


    @Test
    public void rowNormal(){

        Integer[][] testGrid =new Integer[][]{{null, 3, null, 2, 5, null, 7, null, null},
                                              {9, null, 1, 4, null, 6, null, 8, null},
                                              {5, null, 2, null, 1, null, 6, null, 3},
                                              {8, null, 7, null, 2, null, null, 3, null},
                                              {null, 1, null, 7, null, 5, 3, null, null},
                                              {null, 8, 9, null, null, 2, null, 1, null},
                                              {1, null, 3, null, 5, null, 7, null, null},
                                              {null, null, 3, null, null, 4, null, 1, null},
                                              {7, null, 4, null, 1, null, 5, null, null}};


        Grid grid = new Grid();
        grid.setGrid(testGrid);


        assertArrayEquals( new Integer[]{null, 3, null, 2, 5, null, 7, null, null} , grid.row(0));
        assertArrayEquals( new Integer[]{9, null, 1, 4, null, 6, null, 8, null} , grid.row(1));
        assertArrayEquals( new Integer[]{5, null, 2, null, 1, null, 6, null, 3} , grid.row(2));

        assertArrayEquals( new Integer[]{8, null, 7, null, 2, null, null, 3, null} , grid.row(3));
        assertArrayEquals( new Integer[]{null, 1, null, 7, null, 5, 3, null, null} , grid.row(4));
        assertArrayEquals( new Integer[]{null, 8, 9, null, null, 2, null, 1, null} , grid.row(5));

        assertArrayEquals( new Integer[]{1, null, 3, null, 5, null, 7, null, null} , grid.row(6));
        assertArrayEquals( new Integer[]{null, null, 3, null, null, 4, null, 1, null} , grid.row(7));
        assertArrayEquals( new Integer[]{7, null, 4, null, 1, null, 5, null, null} , grid.row(8));
    }


    @Test
    public void colNull(){
        Grid grid = new Grid();

        Integer[] nullArray = new Integer[]{null, null, null, null, null, null, null, null, null};

        for(int x=0; x<=8; x++){
            assertArrayEquals(nullArray, grid.column(x));

        }
    }


    @Test
    public void colNormal(){

        Integer[][] testGrid =new Integer[][]{{null, 3, null, 2, 5, null, 7, null, null},
                {9, null, 1, 4, null, 6, null, 8, null},
                {5, null, 2, null, 1, null, 6, null, 3},
                {8, null, 7, null, 2, null, null, 3, null},
                {null, 1, null, 7, null, 5, 3, null, null},
                {null, 8, 9, null, null, 2, null, 1, null},
                {1, null, 3, null, 5, null, 7, null, null},
                {null, null, 3, null, null, 4, null, 1, null},
                {7, null, 4, null, 1, null, 5, null, null}};


        Grid grid = new Grid();
        grid.setGrid(testGrid);


        assertArrayEquals( new Integer[]{null, 9, 5, 8, null, null, 1, null, 7} ,(grid.column(0)));
        assertArrayEquals( new Integer[]{3, null, null, null, 1, 8, null, null, null}, grid.column(1));
        assertArrayEquals( new Integer[]{null, 1, 2, 7, null, 9, 3, 3, 4}, grid.column(2));

        assertArrayEquals( new Integer[]{2, 4, null, null, 7, null, null, null, null}, grid.column(3));
        assertArrayEquals( new Integer[]{5, null, 1, 2, null, null, 5, null, 1}, grid.column(4));
        assertArrayEquals( new Integer[]{null, 6, null, null, 5, 2, null, 4, null}, grid.column(5));

        assertArrayEquals( new Integer[]{7, null, 6, null, 3, null, 7, null, 5}, grid.column(6));
        assertArrayEquals( new Integer[]{null, 8, null, 3, null, 1, null, 1, null}, grid.column(7));
        assertArrayEquals( new Integer[]{null, null, 3, null, null, null, null, null, null}, grid.column(8));
    }


    @Test
    public void blockNull(){
        Grid grid = new Grid();

        Integer[] nullArray = new Integer[]{null, null, null, null, null, null, null, null, null};

        for(int x =0; x<=6; x=x+3){
            for(int y =0; y<=6; y=y+3){
                assertArrayEquals( nullArray, grid.block(x,y));
            }
        }

    }


    @Test
    public void blockNormal(){

        Integer[][] testGrid =new Integer[][]{{null, 3, null, 2, 5, null, 7, null, null},
                {9, null, 1, 4, null, 6, null, 8, null},
                {5, null, 2, null, 1, null, 6, null, 3},
                {8, null, 7, null, 2, null, null, 3, null},
                {null, 1, null, 7, null, 5, 3, null, null},
                {null, 8, 9, null, null, 2, null, 1, null},
                {1, null, 3, null, 5, null, 7, null, null},
                {null, null, 3, null, null, 4, null, 1, null},
                {7, null, 4, null, 1, null, 5, null, null}};


        Grid grid = new Grid();
        grid.setGrid(testGrid);


        assertArrayEquals(new Integer[]{null, 3, null, 9, null, 1, 5, null, 2}, grid.block(0,0));
        assertArrayEquals(new Integer[]{8, null, 7, null, 1, null, null, 8, 9}, grid.block(0, 3));
        assertArrayEquals(new Integer[]{1, null, 3, null, null, 3, 7, null, 4}, grid.block(0, 6));

        assertArrayEquals(new Integer[]{2, 5, null, 4, null, 6, null, 1, null}, grid.block(3, 0));
        assertArrayEquals(new Integer[]{null, 2, null, 7, null, 5, null, null, 2} , grid.block(3, 3));
        assertArrayEquals(new Integer[]{null, 5, null, null, null, 4, null, 1, null}, grid.block(3, 6));

        assertArrayEquals(new Integer[]{7, null, null, null, 8, null, 6, null, 3}, grid.block(6, 0));
        assertArrayEquals(new Integer[]{null, 3, null, 3, null, null, null, 1, null}, grid.block(6, 3));
        assertArrayEquals(new Integer[]{7, null, null, null, 1, null, 5, null, null}, grid.block(6, 6));
    }




}
