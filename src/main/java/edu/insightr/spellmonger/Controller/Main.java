package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard;
import edu.insightr.spellmonger.View.V_BoardCard_P;
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
        final int maxNumberOfCards = 40;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);
        /* End Initialisation variable **/


        SpellmongerApp model = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);
        C_SpellmongerApp controller = new C_SpellmongerApp(model); // is observable


        V_Menu menu = new V_Menu(controller);

        controller.subscribe(menu);
        try {

            V_BoardCard_P boardCard1 = new V_BoardCard_P(primaryStage, controller);
            controller.subscribe(boardCard1);
            // V_BoardCard boardCard = new V_BoardCard(primaryStage, controller);
            // controller.subscribe(boardCard);


        controller.displayMenu();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        }
    }

