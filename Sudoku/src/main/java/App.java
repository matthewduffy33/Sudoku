

import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.Jooby;
import java.io.FileNotFoundException;


public class App extends Jooby {
    {
        install(new UniRestExtension());
        install(new HandlebarsModule());


        try {
            mvc(new GameController("Sudoku/files/moves.txt", "Sudoku/files/redo.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        /*
         Finally we register our application lifecycle methods
         */
        onStarted(() -> onStart());
        onStop(() -> onStop());
    }

    public static void main(final String[] args) {
        runApp(args, App::new);
    }

    public void onStart()  {
        System.out.println("Starting up...");
    }

    public void onStop() {
        System.out.println("Shutting Down...");
    }

    {
        assets("/sudoku/style.css", "Sudoku/public/style.css");
    }

    {
        assets("/sudoku/functions.js", "Sudoku/public/functions.js");
    }

    {
        assets("/sudoku/generatingGrid.js", "Sudoku/public/generatingGrid.js");
    }

    {
        assets("/sudoku/selectCell.js", "Sudoku/public/selectCell.js");
    }

    {
        assets("/sudoku/undoAndRedo.js", "Sudoku/public/undoAndRedo.js");
    }

}