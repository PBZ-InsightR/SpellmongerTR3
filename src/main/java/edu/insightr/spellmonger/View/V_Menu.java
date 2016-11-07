package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by antho on 04/11/2016.
 */
public class V_Menu implements IObserver {
    V_BoardCard boardCard;

    public V_Menu(Image img, Image img2, Image img3, Image logo_go, Stage primaryStage) {

        this.boardCard = new V_BoardCard(img, img2, img3, logo_go, primaryStage);
    }

    public void display() {


        V_BoardCard.primaryStage.setTitle("SpellmongerTR3");
        V_BoardCard.primaryStage.getIcons().add(new Image("/logo_esilv.png"));

        //set menubar
        final javafx.scene.control.MenuBar menuBar = Usefull.MenuBar(V_BoardCard.primaryStage);

        //Set the go for open Window V_BoardCard
        Button go = new Button("Start");
        go.setId("go");
        go.setGraphic(new ImageView(this.boardCard.logo_go));

        go.setOnAction(e -> updateGo());

        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setId("layout");
        layout.setTop(menuBar);
        layout.setCenter(go);
        BorderPane.setAlignment(go, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);
        scene.getStylesheets().add("style.css");
        V_BoardCard.primaryStage.setScene(scene);
        V_BoardCard.primaryStage.show();

    }

    /**
     * Function that is called when the button Go is pressed
     */
    /// HAVE TO BE MANAGED WITH INTERFACE
    public void updateGo() {
        boardCard.display();
        // C_SpellmongerApp.notifygo();
    }
}
