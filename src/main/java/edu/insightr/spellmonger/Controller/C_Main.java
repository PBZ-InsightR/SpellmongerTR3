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
        final int lifePoints = 5;
        List<String> playersList = new ArrayList<>(2);
        playersList.add("Alice");
        playersList.add("Bob");

        M_SpellmongerApp model = new M_SpellmongerApp(playersList, lifePoints);
        C_SpellmongerApp controller = new C_SpellmongerApp(model); // is observable
        V_Menu menu = new V_Menu(controller);

        controller.subscribe(menu);
        controller.displayMenu();
    }
}

