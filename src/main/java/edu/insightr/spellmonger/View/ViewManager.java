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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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
    public int count;
    ArrayList<PlayCard> p1_hand = new ArrayList<>(20);
    ArrayList<PlayCard> p2_hand = new ArrayList<>(20);

    private Stage mainStage;
    C_SpellmongerApp controller;

    public ViewManager(Stage primaryStage, C_SpellmongerApp controller) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/spellmongerApp.fxml"));
        primaryStage.setTitle("SpellmongerApp");
        primaryStage.setScene(new Scene(root, 1000, 700));
        this.controller = controller;
        this.mainStage = primaryStage;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.p1_btn.add(p1_leftCard);
        this.p1_btn.add(p1_middleCard);
        this.p1_btn.add(p1_rightCard);
        this.p2_btn.add(p2_leftCard);
        this.p2_btn.add(p2_middleCard);
        this.p2_btn.add(p2_rightCard);
        this.count=0;
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
        actiontarget.setText("Welcome to Spellmonger !");
    }

    public void addAI(ActionEvent actionEvent) {
        if (aiButton.isSelected()) username2.setText("PC");
        else username2.clear();
    }

    public void playGame() {
        String playerA = username1.getText();
        String playerB = username2.getText();

        int lifePoints = 20;
        int maxNumberOfCards = 40;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        SpellmongerApp app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);
        //p1_hand = app.getCurrentPlayer().getCardsInHand();
        //p2_hand = app.getOpponentPlayer().getCardsInHand();

        for(int i=0;i<20;i=i+1){
            p1_hand.add(app.cardPool.get(i));
        }
        for(int i=20;i<app.cardPool.size();i=i+1){
            p2_hand.add(app.cardPool.get(i));
        }

        for(Button b : p1_btn){
            showCardOnButton(b, p1_hand);
        }

        for(Button b : p2_btn){
            showCardOnButton(b, p2_hand);
        }


    }

    public void onPlayCard(ActionEvent actionEvent) {
        String cardName;
        p1_playCard.setText(null);

        if (actionEvent.getSource().equals(p1_leftCard)) {
            cardName = p1_leftCard.getAccessibleText();
            p1_playCard.setGraphic(p1_leftCard.getGraphic());
            p1_leftCard.setGraphic(null);
        } else if (actionEvent.getSource().equals(p1_middleCard)) {
            cardName = p1_middleCard.getAccessibleText();
            p1_playCard.setGraphic(p1_middleCard.getGraphic());
            p1_middleCard.setGraphic(null);
        } else if (actionEvent.getSource().equals(p1_rightCard)) {
            cardName = p1_rightCard.getAccessibleText();
            p1_playCard.setGraphic(p1_rightCard.getGraphic());
            p1_rightCard.setGraphic(null);
        } else cardName = "Error";
        actiontarget.setText(cardName);

        for(Button b : p1_btn){
            if(b.getGraphic()==null) showCardOnButton(b, p1_hand);
        }
    }

    public void onPlayCardP2(ActionEvent actionEvent) {
        String cardName;
        p2_playCard.setText(null);

        if (actionEvent.getSource().equals(p2_leftCard)) {
            cardName = p2_leftCard.getAccessibleText();
            p2_playCard.setGraphic(p2_leftCard.getGraphic());
            p2_leftCard.setGraphic(null);
        } else if (actionEvent.getSource().equals(p2_middleCard)) {
            cardName = p2_middleCard.getAccessibleText();
            p2_playCard.setGraphic(p2_middleCard.getGraphic());
            p2_middleCard.setGraphic(null);
        } else if (actionEvent.getSource().equals(p2_rightCard)) {
            cardName = p2_rightCard.getAccessibleText();
            p2_playCard.setGraphic(p2_rightCard.getGraphic());
            p2_rightCard.setGraphic(null);
        } else cardName = "Error";
        actiontarget.setText(cardName);

        for(Button b : p2_btn){
            if(b.getGraphic()==null) showCardOnButton(b, p2_hand);
        }
    }

    public void showCardOnButton(Button button, ArrayList<PlayCard> hand){
        PlayCard card = hand.get(this.count);
        Image image = new Image(getClass().getResourceAsStream(getImagePath(card)));
        button.setGraphic(new ImageView(image));
        button.setAccessibleText(card.getName());
        this.count = this.count + 1;
    }

    public String getImagePath(PlayCard card) {
        String imgPath;
        switch (card.getName()) {
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
        return imgPath;
    }


    public void updateNamesView() {
        /*
        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
        labelNamePlayers.setText(name1 + " vs " + name2);
*/
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

    public void display() {

        mainStage.show();
    }


}