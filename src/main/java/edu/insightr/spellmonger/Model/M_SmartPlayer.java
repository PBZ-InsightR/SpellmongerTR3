package edu.insightr.spellmonger.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by sibel on 10/10/2016.
 * Define the Levels of the IA for the application
 */
public class M_SmartPlayer extends M_Player {

    //private final int level;
    private final ArrayList<M_PlayCard> cardToPlay;
    private int round;

    /*
    /**
     * @param level of the game
     */
    /*
    public M_SmartPlayer(String name, int lifePoints, int level) {
        super(name, lifePoints);
        this.level = level;
        this.cardToPlay = new ArrayList<>();
        this.round = 1;
    }*/
    private double table_point[][] = {{0, -1, -2, -3, 0, 0}, {0, 0, -1, -3, 0, 0}, {0, 0, 0, -3, 0, 0}, {-1, -2, -3, -3, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 1, 0, 0, 3, 3}}; // point gain for a card against an other card

    /*
    /**
     * Constructor by copy
     * @param player : the player to copy
     */
    /*
    public M_SmartPlayer(M_Player player){

        this.name = player.name;
        this.lifePoints = player.lifePoints;

        for(M_PlayCard card : player.getCardsStack()) this.cardsStack.add(card);
        for(M_PlayCard card : player.getCardsInHand()) this.cardsInHand.add(card);

        this.cardToPlay = new ArrayList<>();
        this.level = 1;
        this.round = 1;
    }*/
    private M_PlayCard[] table_card_order = {new M_Beast(M_SpellmongerApp.cardNameWolf, 2, 2),
            new M_Beast(M_SpellmongerApp.cardNameBear, 3, 3),
            new M_Ritual(M_SpellmongerApp.cardNamePoison, 3, false, true, 3),
            new M_Ritual(M_SpellmongerApp.cardNameHeal, -3, true, true, 3),
            new M_Ritual(M_SpellmongerApp.cardNameShield, 0, true, false, 2)};

    M_SmartPlayer(String name, int lifePoints) {
        super(name, lifePoints);
        this.cardToPlay = new ArrayList<>();
        //this.level = 1;
        this.round = 1;
    }

    /**
     * @return a random number that will be the position of the card in the hand of a player
     */
    int level0() {
        if (this.cardsInHand.isEmpty())
            return 0;

        int size = this.cardsInHand.size();
        return new Random().nextInt(size);
    }

   /* public void ReInitializa()
    {
        this.numberOfEach = new int[] {10,10,3,4,5} ;
    }

    public void graveyardStat_ADD(M_PlayCard i){
        this.graveyardStat.add(i);
    }*/

    /**
     * @return the number of the card the player'll play in function of the value of his deck
     */
    public int level1() {
        if (this.round != 1) {
            this.cardToPlay.clear();
        }

        ++round;
        if (!this.cardsInHand.isEmpty()) {
            int avg = getDeckPower() / this.cardsInHand.size();  //avg of the game is 2.1

            if (avg > 2.3) {
                getStrongCardList();

            } else if (avg < 1.9) {
                getAverageCardList();
            } else {
                getBadCardList();
            }
        }
        return chooseCard();
    }

    public int level2() {
        int[] number_card = table_game_card_number();
        double[][] otherGame = table_point_card(number_card);
        methode_moyenne(otherGame);
        int playcard = 0;
        for(int i = 0; i < cardsInHand.size(); ++i){
            if(cardsInHand.get(i) == bestCard()){
                playcard = i;
            }

        }
        return playcard;
    }

    /**
     * @return the number of each card of the gale of the player
     */
    private int[] table_game_card_number() {

        int[] table_game_card_number = {0, 0, 0, 0, 0, 0};
        for (M_PlayCard p : this.cardsInHand) {
            if (p.getName().equalsIgnoreCase("Eagle")) {
                table_game_card_number[0] = table_game_card_number[0] + 1;
            } else if (p.getName().equalsIgnoreCase("Wolf")) {
                table_game_card_number[1] = table_game_card_number[1] + 1;
            } else if (p.getName().equalsIgnoreCase("Bear")) {
                table_game_card_number[2] = table_game_card_number[2] + 1;
            } else if (p.getName().equalsIgnoreCase("Poison")) {
                table_game_card_number[3] = table_game_card_number[3] + 1;
            } else if (p.getName().equalsIgnoreCase("Heal")) {
                table_game_card_number[4] = table_game_card_number[4] + 1;
            } else if (p.getName().equalsIgnoreCase("Shield")) {
                table_game_card_number[5] = table_game_card_number[5] + 1;
            }

        }
        return table_game_card_number;
    }
    private int[] table_graveyard_card_number() {

        int[] table_graveyard = {0, 0, 0, 0, 0, 0};
        for (M_PlayCard p : this.cardsInHand) { /* changer this.cardsinhand par la nouvelle liste cimetiere */
            if (p.getName().equalsIgnoreCase("Eagle")) {
                table_graveyard[0] = table_graveyard[0] + 1;
            } else if (p.getName().equalsIgnoreCase("Wolf")) {
                table_graveyard[1] = table_graveyard[1] + 1;
            } else if (p.getName().equalsIgnoreCase("Bear")) {
                table_graveyard[2] = table_graveyard[2] + 1;
            } else if (p.getName().equalsIgnoreCase("Poison")) {
                table_graveyard[3] = table_graveyard[3] + 1;
            } else if (p.getName().equalsIgnoreCase("Heal")) {
                table_graveyard[4] = table_graveyard[4] + 1;
            } else if (p.getName().equalsIgnoreCase("Shield")) {
                table_graveyard[5] = table_graveyard[5] + 1;
            }

        }
        return table_graveyard;
    }

    private int[] table_playing_card(int table_game_card_number[], int table_graveyard[]){
        int[] table_playing_card = {0,0,0,0,0,0};
        table_playing_card[0]=10-table_game_card_number[0] - table_graveyard[0]   ;
        table_playing_card[1]=10-table_game_card_number[1] - table_graveyard[1] ;
        table_playing_card[2]=10-table_game_card_number[2]  - table_graveyard[2];
        table_playing_card[3]=2-table_game_card_number[3] - table_graveyard[3];
        table_playing_card[4]=3-table_game_card_number[4] - table_graveyard[4];
        table_playing_card[5]=5-table_game_card_number[5] - table_graveyard[5];
        return table_playing_card;

    }

    /**
     * @param table_opponent_card_number to descirbe
     * @return return the table pointcard of the opponent
     */
    private double[][] table_point_card(int table_opponent_card_number[]) {
        double[][] table_point_card = new double[6][8];
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 6; ++j) {
                table_point_card[i][j] = this.table_point[i][j] * table_opponent_card_number[i] / 20;

            }
        }
        return table_point_card;
    }


    /**
     * @param table_point_card to describe
     * @return the avg of the map of card
     */
    private Map<M_PlayCard, Double> methode_moyenne(double[][] table_point_card) {
        Map<M_PlayCard, Double> map_moyenne = new HashMap<>();
        for (int i = 0; i < 5; ++i) {
            double moyenne;
            double somme = 0;
            for (int j = 0; j < 6; ++j) {
                somme = somme + table_point_card[i][j];
            }
            moyenne = somme / 6;
            map_moyenne.put(table_card_order[i], moyenne);
        }
        return map_moyenne;
    }


    /**
     * @return the card with the maxKey
     */
    private M_PlayCard bestCard() {
//the number of beast and ritual in my hand
        int tableMyCard[] = table_game_card_number();
        int graveyardCard[]= table_graveyard_card_number();
// the number of the opponent card : ritual and beast
        //  int tableOpponent []=table_opponent_card_number(tableMyCard);
// avg of the point gain for a card against an other card depending of te number of card of the opponent player
        int PlayingCard[]=table_playing_card(tableMyCard,graveyardCard);
        double tableAvg[][] = table_point_card(PlayingCard);
        Map<M_PlayCard, Double> mapAvg = methode_moyenne(tableAvg);
        Map.Entry<M_PlayCard, Double> maxEntry = null;
        for (Map.Entry<M_PlayCard, Double> entry : mapAvg.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        // This will return max value in the Hashmap

        try {
          return maxEntry.getKey();
        }catch (java.lang.NullPointerException e){
            return new M_Beast(M_SpellmongerApp.cardNameBear, 3, 3);
        }

    }

    /**
     * @return the Destruction power of a deck
     */
    private int getDeckPower() {
        int count = 0;

        for (M_PlayCard e : this.cardsInHand) {
            count += e.getCardValue();
        }

        return count;
    }

    /**
     * @return the number of the card to choose
     */
    private int chooseCard() {
        if (this.cardToPlay.isEmpty())
            return 0;

        int size = this.cardToPlay.size();
        return new Random().nextInt(size);
    }

    /**
     * The list the player can play is with his strongest cards
     */
    void getStrongCardList() {


        this.cardToPlay.addAll(this.cardsInHand.stream().filter(card -> card.getCardValue() >= 2).collect(Collectors.toList()));
    }


    /**
     * The list the player can play is with his average cards
     */
    void getAverageCardList() {


        this.cardToPlay.addAll(this.cardsInHand.stream().filter(card -> card.getCardValue() == 2).collect(Collectors.toList()));

        if (this.cardToPlay.isEmpty()) {
            getStrongCardList();
        }

    }

    /**
     * The list the player can play is with his lowest cards
     */
    void getBadCardList() {
        this.cardToPlay.addAll(this.cardsInHand.stream().filter(card -> card.getCardValue() <= 2).collect(Collectors.toList()));
    }

    /*
    /**
     * Override the play of a card from M_PlayCard, with the IA
     *
     *
     */
    /*
    @Deprecated
    public M_PlayCard smartPlay() {
        int playCardNumber;

        switch (level) {
            case 0:
                playCardNumber = level0();
                break;
            case 1:
                playCardNumber = level1();
                break;
            default:
                playCardNumber = level1();
                break;
        }

        // if (this.cardsInHand.isEmpty())

        M_PlayCard card = this.cardsInHand.get(playCardNumber);
        this.cardsInHand.remove(playCardNumber);
        return card;
    }*/

}
