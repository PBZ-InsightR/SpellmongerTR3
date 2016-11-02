package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.SpellmongerApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tara on 02/11/2016.
 */
public class C_SpellmongerApp {
    SpellmongerApp app;

    public C_SpellmongerApp(String playerA, String playerB) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 40;

        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        // We create the application
        this.app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);
    }

    public void runSpellmongerApp() {
        // We start the game
        this.app.play();
    }


    public static void main(String[] args) {
        C_SpellmongerApp controller = new C_SpellmongerApp("Alice", "Bob");
        controller.runSpellmongerApp();
    }

}
