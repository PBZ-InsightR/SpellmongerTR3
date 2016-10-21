package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize the application. Automatically called.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("SpellmongerTR3");
        primaryStage.getIcons().add(new Image("/lofo_esilv.png"));


        final javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();

        final javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        final javafx.scene.control.Menu editMenu = new javafx.scene.control.Menu("Edit");
        final javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("Help");

//add fullscreen option
        final javafx.scene.control.MenuItem FullScreen = new javafx.scene.control.MenuItem("FullScreen");
        fileMenu.getItems().setAll(FullScreen);
        FullScreen.setOnAction(e-> primaryStage.setFullScreen(true));
//add about option
        final javafx.scene.control.MenuItem About = new javafx.scene.control.MenuItem("About..");
        helpMenu.getItems().setAll(About);
        About.setOnAction(e->AlertBox.display("About..", "Program did by Anthony, Stanislas, Sibel, Tara, Vincent"));
        // add exit
        final javafx.scene.control.MenuItem Exit = new javafx.scene.control.MenuItem("Exit");
        fileMenu.getItems().add(Exit);
        Exit.setOnAction(e->primaryStage.close());
//add menu to menubar
        menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);



        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        Image logo_go = new Image(getClass().getResourceAsStream("/go.png"));

        
        //Set the go for open Window V_BoardCard
        Button go = new Button("GO");

        //Set logo button go
        go.setGraphic(new ImageView(logo_go));

        go.setOnAction(e -> V_BoardCard.display(img, img2, img3));

        //add button and set scene
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(go);
        layout.setAlignment(go, Pos.CENTER);
        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}