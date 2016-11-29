package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard;
import edu.insightr.spellmonger.View.V_BoardCard_P;
import edu.insightr.spellmonger.View.V_Menu;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Tara on 02/11/2016.
 */
public class C_SpellmongerApp implements IObservable {
    private SpellmongerApp app; // Correspond to the model
    private ArrayList<IObserver> observersList;

    private int counter=0;

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);


    // 	Initialize of the variables
    boolean onePlayerDead;
    Player winner;

    Player currentPlayer;
    Player opponentPlayer;
    private boolean playTurn;


    public C_SpellmongerApp(SpellmongerApp model) {

        this.observersList = new ArrayList<>();
        this.app = model; // We create the application


        onePlayerDead = false;
        winner = null;

        currentPlayer = this.app.getCurrentPlayer();
        opponentPlayer = this.app.getOpponentPlayer();
        this.playTurn = false;

    }


    /**
     * Launches the game
     */
    public void play() {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();
    }

    public PlayCard getCard(Player player, int id){
        return player.getCardsInHand().get(id);
    }



    public ArrayList<String> get3Cards(){
        int id = this.counter;
        ArrayList<PlayCard> cards=new ArrayList<>(3);
        ArrayList<String> cardsName=new ArrayList<>(3);
        String name;

        PlayCard card1=this.app.getCurrentPlayer().getCardsInHand().get(id);
        PlayCard card2=this.app.getCurrentPlayer().getCardsInHand().get(id+1);
        PlayCard card3=this.app.getCurrentPlayer().getCardsInHand().get(id+2);

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        name=card1.getName();
        cardsName.add(name);
        name=card2.getName();
        cardsName.add(name);
        name=card3.getName();
        cardsName.add(name);

        this.counter=this.counter+1;


        logger.info("\n "+cardsName);
        return cardsName;
    }
/*
    public ArrayList<String> getImagePath(){
        String imgPath;
        ArrayList<String> cards = new ArrayList<>(3);
        String nameCard;
        for(int i = 0;i<3;i++){
            nameCard=this.get3Cards().get(i);
            switch (nameCard) {
                case "Bear":
                    imgPath = "/bear.png";
                    break;
                case "Wolf":
                    imgPath = "/wolf.png";
                    break;
                case "Eagle":
                    imgPath = "/eagle.png";
                    break;
                case "Shield":
                    imgPath = "/shield.png";
                    break;
                case "Poison":
                    imgPath = "/poison.png";
                    break;
                case "Heal":
                    imgPath = "/heal.png";
                    break;
                default:
                    imgPath = "/img.jpg";
                    break;
            }
        }
        return cards;
    }
*/
    /**
     * Plays a turn
     */
    public void playTurn() {
        // Everything is set up, start the game!
        if (!onePlayerDead) {


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

    /**
     * Display only the view for the board
     */
    public void displayBoard() {

    for (int i = 0; i < observersList.size(); i++) {
        IObserver o = observersList.get(i);

        if (o instanceof V_BoardCard_P) {
            V_BoardCard_P board_p1 = (V_BoardCard_P) o;
            board_p1.display();
        }

        if (o instanceof V_BoardCard) {
            V_BoardCard board = (V_BoardCard) o;
            board.display();
        }

    }

    }

}
