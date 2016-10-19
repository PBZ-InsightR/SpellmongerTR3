package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by antho on 19/10/2016.
 * For some alerts
 */
public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.getIcons().add(new Image("/lofo_esilv.png"));

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

}
