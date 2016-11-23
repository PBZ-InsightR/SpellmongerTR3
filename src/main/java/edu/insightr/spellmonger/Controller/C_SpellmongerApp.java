package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_Menu;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Tara on 02/11/2016.
 */
public class C_SpellmongerApp implements IObservable {
    private SpellmongerApp app; // Correspond to the model
    private ArrayList<IObserver> observersList;

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);


    // 	Initialize of the variables
    boolean onePlayerDead;
    Player winner;

    Player currentPlayer;
    Player opponentPlayer;
    private boolean appLaunched;
    private boolean playTurn;


    public C_SpellmongerApp(SpellmongerApp model, Stage primaryStage) {

        this.observersList = new ArrayList<>();
        this.app = model; // We create the application


        onePlayerDead = false;
        winner = null;

        currentPlayer = this.app.getCurrentPlayer();
        opponentPlayer = this.app.getOpponentPlayer();
        this.appLaunched = false;
        this.playTurn = false;
    }


    /**
     * Launches the game
     */
    public void play() {
        this.appLaunched = true;
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();
    }

    /**
     * Plays a turn
     */
    public void playTurn() {
        // Everything is set up, start the game!
        if (appLaunched && !onePlayerDead) {


            // If no one has cards left, the game is ended
            if (!this.app.isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
            } else {
                logger.info("\n");
                logger.info("***** ROUND " + this.app.getRoundCounter() + " *****");

                // Each players chooses a card to play
                this.app.playersPlay();
                // The turn is resolved (damage denying, damage dealing, healing, etc)
                this.app.resolveTurn();
                //Switch players
                this.app.nextTurn();


                if (opponentPlayer.isDead()) {
                    winner = currentPlayer;
                    onePlayerDead = true;
                }
                if (currentPlayer.isDead()) {
                    winner = opponentPlayer;
                    onePlayerDead = true;
                }
            }
        }

        if (onePlayerDead) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Graveyard : " + this.app.getGraveyard());
            // notifyObserver();
        }
    }

    public String[] getPlayerNames()
    {
        return this.app.getPlayerNames();
    }

    public void setName(String player, String name) {
        this.app.setName(player, name);
        this.notifyObserver();
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

    @Override
    public void notifyObserver() {
        for (int i = 0; i < observersList.size(); i++) {
            IObserver o = observersList.get(i);
            o.update(this); // update the Listeners for this class
        }
    }


    /// TO DELETE

    /**
     * Display only the view for the menu
     */
    public void displayMenu() {
        for (int i = 0; i < observersList.size(); i++) {
            IObserver o = observersList.get(i);
            if (o instanceof V_Menu) {
                V_Menu menu = (V_Menu) o;
                menu.display();
            }
        }
    }
    /// END DELETE

}
