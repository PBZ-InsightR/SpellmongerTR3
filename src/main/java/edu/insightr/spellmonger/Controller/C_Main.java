package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.M_SpellmongerApp;
import edu.insightr.spellmonger.View.V_Menu;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class C_Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the application. Automatically called.
     *
     * @param primaryStage stage actual
     * @throws Exception properly handle exceptions should they occur
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int lifePoints = 20;
        final String player1 = "Alice";
        final String player2 = "Bob";
        List<String> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);

        M_SpellmongerApp model = new M_SpellmongerApp(playersList, lifePoints);
        C_SpellmongerApp controller = new C_SpellmongerApp(model); // is observable
        V_Menu menu = new V_Menu(controller);
        controller.subscribe(menu);

        controller.displayMenu();
    }
}

