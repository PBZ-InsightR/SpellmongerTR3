package edu.insightr.spellmonger.View;

/**
 * Created by Yasmeen on 09/11/2016.
 * Defines the view controller
 */

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ViewManager implements Initializable, IObserver {

    public TextField username1;
    public TextField username2;
    public Button goButton;
    public Button clearButton;
    public ToggleButton aiButton;
    public Label actiontarget;
    public Button p1_leftCard, p1_middleCard, p1_rightCard;
    public Button p2_leftCard, p2_middleCard, p2_rightCard;
    public Label p1_playCard, p2_playCard;
    public ArrayList<Button> p1_btn = new ArrayList<>(3);
    public ArrayList<Button> p2_btn = new ArrayList<>(3);
    public int counterHand1;
    public int counterHand2;
    ArrayList<PlayCard> p1_hand = new ArrayList<>(20);
    ArrayList<PlayCard> p2_hand = new ArrayList<>(20);

    C_SpellmongerApp controller;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.p1_btn.add(p1_leftCard);
        this.p1_btn.add(p1_middleCard);
        this.p1_btn.add(p1_rightCard);
        this.p2_btn.add(p2_leftCard);
        this.p2_btn.add(p2_middleCard);
        this.p2_btn.add(p2_rightCard);
        this.counterHand1=0;
        this.counterHand2=0;
        System.out.println("View is loaded!");
    }

    public void go(ActionEvent actionEvent) {
        if ("".equals(username1.getText()) || "".equals(username2.getText())) actiontarget.setText("Field(s) empty");
        else if (username1.getText().equals(username2.getText())) actiontarget.setText("Choose a different player");
        else {
            actiontarget.setText(username1.getText() + " VS " + username2.getText());
            playGame();
        }
    }

    public void clearGame(ActionEvent actionEvent) {
        username1.clear();
        username2.clear();
        p1_leftCard.setGraphic(null);
        p1_middleCard.setGraphic(null);
        p1_rightCard.setGraphic(null);
        p1_playCard.setGraphic(null);
        p2_playCard.setGraphic(null);
        actiontarget.setText("Welcome to Spellmonger !");
    }

    public void addAI(ActionEvent actionEvent) {
        if (aiButton.isSelected()) username2.setText("PC");
        else username2.clear();
    }

    public void playGame() {

        // TO EDIT
        String playerA = username1.getText();
        String playerB = username2.getText();

        int lifePoints = 20;
        int maxNumberOfCards = 40;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        SpellmongerApp app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);

        controller.play();

        for(int i=0;i<20;i=i+1){
            p1_hand.add(app.cardPool.get(i));
        }
        for(int i=20;i<app.cardPool.size();i=i+1){
            p2_hand.add(app.cardPool.get(i));
        }
        // END EDIT

        for(Button b : p1_btn){
            setCardID(b,1);
            showCard(b, p1_hand,1);
        }

        for(Button b : p2_btn){
            setCardID(b,2);
        }
    }

    public void onPlayCard(ActionEvent actionEvent) {
        int id;
        Image image;
        p1_playCard.setText(null);

        for(Button button : p1_btn){
            if (actionEvent.getSource().equals(button)){
                id = (int)button.getUserData();
                image=p1_hand.get(id).getImage();
                p1_playCard.setGraphic(new ImageView(image));
                button.setGraphic(null);
                actiontarget.setText(p1_hand.get(id).getName());
                setCardID(button,1);
                showCard(button, p1_hand, 1);
            }
        }
    }

    public void onPlayCardP2(ActionEvent actionEvent) {
        int id;
        Image image;
        p2_playCard.setText(null);

        for(Button button : p2_btn){
            if (actionEvent.getSource().equals(button)){
                id = (int)button.getUserData();
                image=p2_hand.get(id).getImage();
                p2_playCard.setGraphic(new ImageView(image));
                button.setGraphic(null);
                actiontarget.setText(p2_hand.get(id).getName());
                setCardID(button, 2);
            }
        }
    }

    public void setCardID(Button button, int value){
        if(value==1) {
            counterHand1 = counterHand1 + 1;
            button.setUserData(counterHand1);
        }
        else{
            counterHand2 = counterHand2 + 1;
            button.setUserData(counterHand2);
        }
    }

    public void showCard(Button button, ArrayList<PlayCard> hand, int value){
        if(value==1) {
            PlayCard card = hand.get(counterHand1);
            button.setGraphic(new ImageView(card.getImage()));
        }
        else{
            PlayCard card = hand.get(counterHand2);
            button.setGraphic(new ImageView(card.getImage()));
        }

    }


    public void updateNamesView() {

        String name1 = this.controller.getPlayerNames()[0];
        String name2 = this.controller.getPlayerNames()[1];
        actiontarget.setText(name1 + " vs " + name2);

    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {

        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            controller.getPlayerNames();
            updateNamesView();
        }

    }



}