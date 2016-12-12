package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Defines the menu view
 * Created by antho on 04/11/2016.
 */
public class V_Menu implements IObserver {
    private final C_SpellmongerApp controller; // temporary solution
    private final Image logo_go;
    private final Image logo_go_IA;
    private final Stage Fenetre_Menu = new Stage();
    private final Stage primaryStage;
    private String name1;
    private String name2;
    private Label labelNamePlayers;

    public V_Menu(C_SpellmongerApp app, Stage stage) {
        this.controller = app;
        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
        this.logo_go = new Image(getClass().getResourceAsStream("/go.png"));
        this.logo_go_IA = new Image(getClass().getResourceAsStream("/img.jpg"));
        primaryStage = stage;
    }

    public void display() {

        //   V_BoardCard.primaryStage.setTitle("SpellmongerTR3");
        //   V_BoardCard.primaryStage.getIcons().add(new Image("/logo_esilv.png"));
        Fenetre_Menu.setTitle("SpellmongerTR3_Menu");

        //set menubar
        //  final javafx.scene.control.MenuBar menuBar = V_Utilities.MenuBar(V_BoardCard.primaryStage);

        //Set the go for open Window V_BoardCard
        Button go = new Button("Start");
        go.setId("go");
        go.setGraphic(new ImageView(this.logo_go));

        Button go_IA = new Button("Start IA");
        go_IA.setId("go_IA");
        go_IA.setGraphic(new ImageView(this.logo_go));

        //Zone to fill Name Player
        TextField nameP1 = new TextField();
        TextField nameP2 = new TextField();
        Button submitP1 = new Button("SubmitP1");
        Button submitP2 = new Button("SubmitP2");
        Label label1 = new Label();
        Label label2 = new Label();
        labelNamePlayers = new Label();
        labelNamePlayers.setText(name1 + " vs " + name2);
        Button clear = new Button("Clear");


        label1.setTextFill(Color.web("#FFFF"));
        label1.setFont(Font.font("Cambria", 20));
        label2.setTextFill(Color.web("#FFFF"));
        label2.setFont(Font.font("Cambria", 20));
        labelNamePlayers.setTextFill(Color.web("#FFFF"));
        labelNamePlayers.setFont(Font.font("Cambria", 20));


        VBox leftMenu = new VBox();
        leftMenu.getChildren().addAll(nameP1, submitP1, label1);
        nameP1.setPromptText("Enter player1's name");
        nameP2.setPromptText("Enter player2's name");


        VBox botMenu = new VBox();
        botMenu.getChildren().addAll(labelNamePlayers, clear);

        VBox rightMenu = new VBox();
        rightMenu.getChildren().addAll(nameP2, submitP2, label2);

        HBox centerMenu = new HBox();
        centerMenu.getChildren().addAll(go_IA, go);


        // Function button Submit,Clear, go
        submitP1.setOnAction(e -> sendName("P1", nameP1, label1));
        submitP2.setOnAction(e -> sendName("P2", nameP2, label2));
        clear.setOnAction(e -> Clear(label1, label2));
        go.setOnAction(e -> notifyGo());
        go_IA.setOnAction(e -> notifyGo_IA(label1, label2));


        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setId("layout");
        //   layout.setTop(menuBar);
        //layout.setCenter(centerMenu);

        BorderPane.setAlignment(go, Pos.CENTER_LEFT);
        BorderPane.setAlignment(go_IA, Pos.CENTER_RIGHT);
        BorderPane.setMargin(centerMenu, new Insets(12, 12, 12, 12));
        layout.setCenter(centerMenu);
        layout.setLeft(leftMenu);
        layout.setRight(rightMenu);
        layout.setBottom(botMenu);
        BorderPane.setAlignment(centerMenu, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);
        scene.getStylesheets().add("style.css");
        Fenetre_Menu.setScene(scene);

        //  V_BoardCard.primaryStage.setScene(scene);
        //   V_BoardCard.primaryStage.show();
        Fenetre_Menu.show();

    }


    /**
     * Function that is called when the button Go is pressed
     */
    private void notifyGo() {

        // Since we have default names (Alice and Bob), use them if there is no corresponding names sent
        //if (!"".equals(label1.getText()) && !"".equals(label2.getText())) {
        controller.play();
        Fenetre_Menu.close();
        //}
    }

    private void notifyGo_IA(Label label1, Label label2) {
        //if (!"".equals(label1.getText()) && !"".equals(label2.getText())) {
            controller.play_IA();
            Fenetre_Menu.close();
        //}
    }

    private String sendName(String player, TextField field, Label label) {
        String name = null;
        if ((field.getText() != null && !field.getText().isEmpty())) {
            name = field.getText();
            label.setText("le joueur enregistré est " + field.getText());
            field.clear();
            controller.setName(player, name);
        }
        return name;
    }


    private void Clear(Label label1, Label label2) {
        label1.setText("");
        label2.setText("");
    }

    private void updateNamesView() {
        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
        labelNamePlayers.setText(name1 + " vs " + name2);

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

    @Override
    public void setVisible(boolean setVisible) {
        if (setVisible) Fenetre_Menu.show();
        else Fenetre_Menu.hide();
    }
}
