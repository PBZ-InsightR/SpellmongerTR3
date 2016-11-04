package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tara on 02/11/2016.
 */
public class C_SpellmongerApp implements IObservable{
    SpellmongerApp app;
    private ArrayList<IObserver> observersList;
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);

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
        this.play();
    }


    /**
     * Launches the game
     */
    public void play() {

        // 	Initialize of the variables
        boolean onePlayerDead = false;
        Player winner = null;
        Player currentPlayer = this.app.getCurrentPlayer();
        Player opponentPlayer = this.app.getOpponentPlayer();

        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();

        // Everything is set up, start the game!
        while (!onePlayerDead) {

            // If no one has cards left, the game is ended
            if (!this.app.isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
                break;
            }

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

        if (onePlayerDead) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Graveyard : " + this.app.getGraveyard());
        }
    }

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
