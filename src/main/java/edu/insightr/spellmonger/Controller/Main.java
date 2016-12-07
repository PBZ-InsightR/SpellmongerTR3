package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_Menu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the application. Automatically called.
     *
     * @param primaryStage stage actual
     * @throws Exception no things to said
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Initialisation variable **/

        String player1 = "Alice";
        String player2 = "Bob";


        final int lifePoints = 20;
        List<String> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        /* End Initialisation variable **/


        SpellmongerApp model = new SpellmongerApp(playersList, lifePoints);
        C_SpellmongerApp controller = new C_SpellmongerApp(model); // is observable

        V_Menu menu = new V_Menu(controller, primaryStage);
        controller.subscribe(menu);

        controller.displayMenu();
        // controller.play();

    }
}

