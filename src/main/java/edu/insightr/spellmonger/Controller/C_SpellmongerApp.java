package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.SpellmongerApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tara on 02/11/2016.
 */
public class C_SpellmongerApp implements IObservable{
    SpellmongerApp app;
    private ArrayList<IObserver> observersList;

    public C_SpellmongerApp(String playerA, String playerB) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 40;

        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        this.observersList = new ArrayList<>();

        // We create the application
        this.app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);
    }

    public void runSpellmongerApp() {
        // We start the game
        this.app.play();
    }
    /*

     */

    public static void main(String[] args) {
        C_SpellmongerApp controller = new C_SpellmongerApp("Alice", "Bob");
        controller.runSpellmongerApp();
    }

    @Override
    public boolean subscribe(IObserver observer) {
        if (this.observersList.contains(observer)) return false;
        else return this.observersList.add(observer);
    }

    @Override
    public boolean unsubcsribe(IObserver observer) {
        if (this.observersList.contains(observer)) return this.observersList.remove(observer);
        else return false;
    }
}
