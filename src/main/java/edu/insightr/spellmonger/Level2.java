package edu.insightr.spellmonger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * AI working with probability
 * Created by Vincent on 04/11/2016.
 */
class Level2 extends Player {

    private int level;
    private ArrayList<PlayCard> cardToPlay;
    private int round;

    /**
     * @param level of the game
     */
    Level2(String name, int lifePoints, int level) {
        super(name, lifePoints);
        this.level = level;
        this.cardToPlay = new ArrayList<>();
        this.round = 1;
    }

    Level2(String name, int lifePoints) {
        super(name, lifePoints);
        this.cardToPlay = new ArrayList<>();
        this.level = 1;
        this.round = 1;
    }

    private double table_point[][] = {{0, -1, -2, -3, 0, 0}, {0, 0, -1, -3, 0, 0}, {0, 0, 0, -3, 0, 0}, {-1, -2, -3, -3, 0, 0}, {0, 0, 0, 0, 0, 0}, {2, 1, 0, 0, 3, 3}}; // point gain for a card against an other card
    private PlayCard[] table_card_order = {new Beast(SpellmongerApp.cardNameWolf, 2, 2),
            new Beast(SpellmongerApp.cardNameBear, 3, 3),
            new Ritual(SpellmongerApp.cardNamePoison, 3, false, true, 3),
            new Ritual(SpellmongerApp.cardNameHeal, -3, true, true, 3),
            new Ritual(SpellmongerApp.cardNameShield, 0, true, false, 2)};


    /**
     * @return the number of each card of the gale of the player
     */
    private int[] table_game_card_number() {

        int[] table_game_card_number = {0, 0, 0, 0, 0, 0};
        for (PlayCard p : this.cardsInHand) {
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

        int[] table_game_card_number = {0, 0, 0, 0, 0, 0};
        for (PlayCard p : this.cardsInHand) { /* changer this.cardsinhand par la nouvelle liste cimetiere */
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
    }/*/*
    int [] table_opponent_card_number(int table_game_card_number[]){

        int[] table_opponent_card_number = {0,0,0,0,0,0};

        table_opponent_card_number[0]=10-table_game_card_number[0] ;
        table_opponent_card_number[1]=10-table_game_card_number[1] ;
        table_opponent_card_number[2]=10-table_game_card_number[2] ;
        table_opponent_card_number[3]=2-table_game_card_number[3] ;
        table_opponent_card_number[4]=3-table_game_card_number[4] ;
        table_opponent_card_number[5]=5-table_game_card_number[5];
        return table_opponent_card_number;
    }*/

    int[] table_playing_card(int table_game_card_number[]){
        int[] table_playing_card = {0,0,0,0,0,0};
        table_playing_card[0]=10-table_game_card_number[0]    ;
        table_playing_card[1]=10-table_game_card_number[1] ;
        table_playing_card[2]=10-table_game_card_number[2] ;
        table_playing_card[3]=2-table_game_card_number[3] ;
        table_playing_card[4]=3-table_game_card_number[4] ;
        table_playing_card[5]=5-table_game_card_number[5];
        return table_playing_card;

    }

    /**
     * @param table_opponent_card_number to descirbe
     * @return return the table pointcard of the opponent
     */
    private double[][] table_point_card(int table_opponent_card_number[]) {
        double[][] table_point_card = new double[6][8];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; i < 6; i++) {
                table_point_card[i][j] = this.table_point[i][j] * table_opponent_card_number[i] / 20;

            }
        }
        return table_point_card;
    }


    /**
     * @param table_point_card to describe
     * @return the avg of the map of card
     */
    private Map<PlayCard, Double> methode_moyenne(double[][] table_point_card) {
        Map<PlayCard, Double> map_moyenne = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            double moyenne;
            double somme = 0;
            for (int j = 0; j < 6; j++) {
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
    private PlayCard bestCard() {
//the number of beast and ritual in my hand
        int tableMyCard[] = table_game_card_number();
// the number of the opponent card : ritual and beast
        //  int tableOpponent []=table_opponent_card_number(tableMyCard);
// avg of the point gain for a card against an other card depending of te number of card of the opponent player
        double tableAvg[][] = table_point_card(tableMyCard);
        Map<PlayCard, Double> mapAvg = methode_moyenne(tableAvg);
        Map.Entry<PlayCard, Double> maxEntry = null;
        for (Map.Entry<PlayCard, Double> entry : mapAvg.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        PlayCard maxKey = maxEntry.getKey();
        // This will return max value in the Hashmap
        return maxKey;

    }

    @Override
    void playACard(SpellmongerApp game) {
        int[] number_card = table_game_card_number();
        double[][] otherGame = table_point_card(number_card);
        methode_moyenne(otherGame);
        bestCard();

    }
}
