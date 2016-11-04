package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.sun.org.apache.bcel.internal.util.SecuritySupport.getResourceAsStream;

/**
 * Created by antho on 04/11/2016.
 */
public class V_Menu {



    public static void display(Image img, Image img2, Image img3,Image logo_go,Stage primaryStage) {


        primaryStage.setTitle("SpellmongerTR3");
        primaryStage.getIcons().add(new Image("/logo_esilv.png"));

        //set menubar
        final javafx.scene.control.MenuBar menuBar = Usefull.MenuBar(primaryStage);

        //Set the go for open Window V_BoardCard
        Button go = new Button("Start");
        go.setId("go");
        go.setGraphic(new ImageView(logo_go));

        go.setOnAction(e -> V_BoardCard.display(img, img2, img3, primaryStage));

        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setId("layout");
        layout.setTop(menuBar);
        layout.setCenter(go);
        BorderPane.setAlignment(go, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
