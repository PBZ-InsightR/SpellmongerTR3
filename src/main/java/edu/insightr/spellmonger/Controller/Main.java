package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.SpellmongerApp;
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

        /** Initialisation variable **/

        String playerA = "Alice";
        String playerB = "Bob";

        final int lifePoints = 20;
        final int maxNumberOfCards = 40;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);
        /** End Initialisation variable **/


        SpellmongerApp model = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);
        C_SpellmongerApp controller = new C_SpellmongerApp(model, primaryStage); // is observable
        // HAVE TO DECLARE View in Main : V_Menu menu = new V_Menu();
        controller.display();

        controller.notifygo(); // HAVE TO BE REMOVED
        }


}