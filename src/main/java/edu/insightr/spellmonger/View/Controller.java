package edu.insightr.spellmonger.View;

/**
 * Created by Yasmeen on 09/11/2016.
 * Defines the view controller
 */

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

public class Controller implements Initializable {

    public TextField username1;
    public TextField username2;
    public Button goButton;
    public Button clearButton;
    public ToggleButton aiButton;
    public Label actiontarget;
    public Button p1_leftCard, p1_middleCard, p1_rightCard;
    public Button p2_leftCard, p2_middleCard, p2_rightCard;
    public Button p1_playCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is loaded!");
    }

    public void go(ActionEvent actionEvent) {
        if("".equals(username1.getText()) || "".equals(username2.getText())) actiontarget.setText("Field(s) empty");
        else if(username1.getText().equals(username2.getText())) actiontarget.setText("Choose a different player");
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
        if(aiButton.isSelected())username2.setText("PC");
        else username2.clear();
    }

    public void playGame(){
        String playerA = username1.getText();
        String playerB = username2.getText();

        int lifePoints = 20;
        int maxNumberOfCards = 40;
        List<String> playersList = new ArrayList<>();
        playersList.add(playerA);
        playersList.add(playerB);

        SpellmongerApp app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);

        PlayCard c= app.cardPool.get(0);
        String img=getImage(c);
        Image imgPath = new Image(getClass().getResourceAsStream(img));
        p1_leftCard.setGraphic(new ImageView(imgPath));
        p1_leftCard.setAccessibleText(c.getName());

        c= app.cardPool.get(1);
        img=getImage(c);
        imgPath = new Image(getClass().getResourceAsStream(img));
        p1_middleCard.setGraphic(new ImageView(imgPath));
        p1_middleCard.setAccessibleText(c.getName());

        c= app.cardPool.get(2);
        img=getImage(c);
        imgPath = new Image(getClass().getResourceAsStream(img));
        p1_rightCard.setGraphic(new ImageView(imgPath));
        p1_rightCard.setAccessibleText(c.getName());
    }

    public void onPlayCard(ActionEvent actionEvent){
        String cardName;
        p1_playCard.setText(null);
        if(actionEvent.getSource().equals(p1_leftCard)){
            cardName=p1_leftCard.getAccessibleText();
            p1_playCard.setGraphic(p1_leftCard.getGraphic());
        }
        else if(actionEvent.getSource().equals(p1_middleCard)){
            cardName=p1_middleCard.getAccessibleText();
            p1_playCard.setGraphic(p1_middleCard.getGraphic());
        }
        else if(actionEvent.getSource().equals(p1_rightCard)){
            cardName=p1_rightCard.getAccessibleText();
            p1_playCard.setGraphic(p1_rightCard.getGraphic());
        }
        else cardName="Error";
        actiontarget.setText(cardName);
    }

    public String getImage(PlayCard card){
        String imgPath;
        switch(card.getName()){
            case "Bear":
                imgPath="/bear.png";
                break;
            case "Wolf":
                imgPath="/wolf.png";
                break;
            case "Eagle":
                imgPath="/eagle.png";
                break;
            case "Shield":
                imgPath="/shield.png";
                break;
            case "Poison":
                imgPath="/poison.png";
                break;
            case "Heal":
                imgPath="/heal.png";
                break;
            default:
                imgPath="/img.jpg";
                break;
        }
        return imgPath;
    }




}