package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Created by antho on 04/11/2016.
 */
public class V_Menu implements IObserver {
    V_BoardCard boardCard;
    C_SpellmongerApp controller; // temporary solution
    String name1;
    String name2;

    public V_Menu(Stage primaryStage, C_SpellmongerApp app) {

        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        Image logo_go = new Image(getClass().getResourceAsStream("/go.png"));

        this.controller = app;
        this.boardCard = new V_BoardCard(img, img2, img3, logo_go, primaryStage, controller);

        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
    }

    public void display() {


        V_BoardCard.primaryStage.setTitle("SpellmongerTR3");
        V_BoardCard.primaryStage.getIcons().add(new Image("/logo_esilv.png"));

        //set menubar
        final javafx.scene.control.MenuBar menuBar = V_Utilities.MenuBar(V_BoardCard.primaryStage);

        //Set the go for open Window V_BoardCard
        Button go = new Button("Start");
        go.setId("go");
        go.setGraphic(new ImageView(this.boardCard.logo_go));

        //Zone to fill Name Player
        TextField nameP1 = new TextField();
        TextField nameP2 = new TextField();
        Button submitP1 = new Button("SubmitP1");
        Button submitP2 = new Button("SubmitP2");
        Label label1 = new Label();
        Label label2 = new Label();
        Label labelNamePlayers = new Label();
        labelNamePlayers.setText(name1 + " vs " + name2);
        Button clear = new Button("Clear");

        VBox leftMenu = new VBox();
        leftMenu.getChildren().addAll(nameP1,submitP1,nameP2,submitP2);
        nameP1.setPromptText("Enter name player1.");
        nameP2.setPromptText("Enter name player2.");



        VBox righMenu = new VBox();
        righMenu.getChildren().addAll(label1, label2, clear, labelNamePlayers);


        // Function button Submit,Clear, go
        submitP1.setOnAction(e -> sendName("P1", nameP1, label1));
        submitP2.setOnAction(e -> sendName("P2", nameP2, label2));
        clear.setOnAction(e -> Clear(label1,label2));
        go.setOnAction(e -> notifyGo(label1,label2));




        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setId("layout");
        layout.setTop(menuBar);
        layout.setCenter(go);
        layout.setLeft(leftMenu);
        layout.setRight(righMenu);
        BorderPane.setAlignment(go, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);
        scene.getStylesheets().add("style.css");
        V_BoardCard.primaryStage.setScene(scene);
        V_BoardCard.primaryStage.show();

    }


    /**
     * Function that is called when the button Go is pressed
     */
    public void notifyGo(Label label1, Label label2) {
        if (label1.getText() != "" && label2.getText() != "") {
            controller.play();
            boardCard.display();
        }
    }

    public String sendName(String player, TextField field, Label label) {
        String name = null;
        if ((field.getText() != null && !field.getText().isEmpty())){
            name = field.getText();
            label.setText("le joueur enregistré est "+ field.getText());
            field.clear();
            controller.setName(player, name);
        }
        return name;
    }



    public void Clear(Label label1, Label label2) {
        label1.setText("");
        label2.setText("");
    }

    public void updateNamesView() {
        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
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
