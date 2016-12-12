package edu.insightr.spellmonger.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by antho on 19/10/2016.
 * For some alerts
 */
public class V_Utilities {


    private static V_Utilities INSTANCE = null;

    /**
     * Default constructor for the V_Utilities singleton
     */
    private V_Utilities() {
    }

    /**
     * @return Returns the instance of the Singleton
     */
    public static V_Utilities getInstance() {
        if (INSTANCE == null)
            INSTANCE = new V_Utilities();

        return INSTANCE;
    }

    /**
     * This function displays an alertBox.
     *
     * @param title   Textbox title
     * @param message message to AlertBox
     */
    public void AlertBox(String title, String message) {
        Stage window = new Stage();
        window.getIcons().add(new Image("/logo_esilv.png"));
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesBtn = new Button("ok");
        yesBtn.setOnAction(e -> window.close());

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, yesBtn);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * @param card        the card you want to assign the effect to
     * @param destination the button that will act as the card.
     */
    public void SetCardOnAction(Button card, Button destination) {
        card.setOnAction(e -> {
            Button Temps = new Button();
            Temps.setGraphic(destination.getGraphic());
            destination.setGraphic(card.getGraphic());
            card.setGraphic(Temps.getGraphic());
        });
    }
}
