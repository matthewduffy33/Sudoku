

import io.jooby.handlebars.HandlebarsModule;
import io.jooby.helper.UniRestExtension;
import io.jooby.Jooby;
import java.io.FileNotFoundException;


public class App extends Jooby {
    {
        install(new UniRestExtension());
        install(new HandlebarsModule());


        try {
            mvc(new GameController("files/moves.txt", "files/redo.txt"));
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
        assets("/sudoku/style.css", "public/style.css");
    }

    {
        assets("/sudoku/functions.js", "public/functions.js");
    }

    {
        assets("/sudoku/generatingGrid.js", "public/generatingGrid.js");
    }

    {
        assets("/sudoku/selectCell.js", "public/selectCell.js");
    }

    {
        assets("/sudoku/undoAndRedo.js", "public/undoAndRedo.js");
    }

}