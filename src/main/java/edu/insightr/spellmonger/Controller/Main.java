package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard_P1;
import edu.insightr.spellmonger.View.V_BoardCard_P2;
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
     * @param primaryStage stage actual
     * @throws Exception no things to said
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        /* Initialisation variable **/

        String playerA = "Alice";
        String playerB = "Bob";


        final int lifePoints = 20;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);
        /* End Initialisation variable **/


        SpellmongerApp model = new SpellmongerApp(playersList, lifePoints);
        C_SpellmongerApp controller = new C_SpellmongerApp(model); // is observable


        V_Menu menu = new V_Menu(controller);

        controller.subscribe(menu);
        try {

            V_BoardCard_P2 boardCard_P2 = new V_BoardCard_P2(primaryStage, controller);
            controller.subscribe(boardCard_P2);
             V_BoardCard_P1 boardCard_P1 = new V_BoardCard_P1(primaryStage, controller);
            controller.subscribe(boardCard_P1);


        controller.displayMenu();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        }
    }

