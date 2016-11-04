package edu.insightr.spellmonger.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by antho on 19/10/2016.
 * For some alerts
 */
public class Usefull {

    /**
     * This function displays an alertBox.
     * @param title Textbox title
     * @param message message to AlertBox
     */
    public static void AlertBox(String title, String message){
        Stage window = new Stage();
        window.getIcons().add(new Image("/logo_esilv.png"));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesBtn = new Button("ok");
        yesBtn.setOnAction(e-> window.close());

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label,yesBtn);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }



    public  static  javafx.scene.control.MenuBar MenuBar(Stage stage){
        //Stage stage = win.getStage();

        final javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();

        final javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        final javafx.scene.control.Menu editMenu = new javafx.scene.control.Menu("Edit");
        final javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("Help");

        //add fullscreen option
        final javafx.scene.control.MenuItem FullScreen = new javafx.scene.control.MenuItem("FullScreen");
        fileMenu.getItems().setAll(FullScreen);
        FullScreen.setOnAction(e-> stage.setFullScreen(true));

        //add about option
        final javafx.scene.control.MenuItem About = new javafx.scene.control.MenuItem("About..");
        helpMenu.getItems().setAll(About);
        About.setOnAction(e-> Usefull.AlertBox("About..", "Program did by Anthony, Stanislas, Sibel, Tara, Vincent"));

        // add exit
        final javafx.scene.control.MenuItem Exit = new javafx.scene.control.MenuItem("Exit");
        fileMenu.getItems().add(Exit);
        Exit.setOnAction(e->stage.close());

        // add shortcut
        Exit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));


        //add menu to menubar
        menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);

        return menuBar;
    }

}
