package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the application. Automatically called.
     * @param primaryStage stage actual
     * @throws Exception no things to said
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("SpellmongerTR3");
        primaryStage.getIcons().add(new Image("/logo_esilv.png"));


        //set menubar
        final javafx.scene.control.MenuBar menuBar = Usefull.MenuBar(primaryStage);

        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        Image logo_go = new Image(getClass().getResourceAsStream("/go.png"));
        
        //Set the go for open Window V_BoardCard
        Button go = new Button("Start");
        go.setId("go");
        //Set logo button go
        go.setGraphic(new ImageView(logo_go));

        go.setOnAction(e -> V_BoardCard.display(img, img2, img3, primaryStage));

        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setId("layout");
        layout.setTop(menuBar);
        layout.setCenter(go);
        BorderPane.setAlignment(go, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);

        // Set css file
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }




}