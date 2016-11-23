package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Yasmeen on 23/11/2016.
 */
public class ViewLauncher implements IObserver{

    private Stage mainStage;
    C_SpellmongerApp controller;

    public ViewLauncher(Stage primaryStage, C_SpellmongerApp controller){
        this.mainStage=primaryStage;
        this.controller=controller;
        try {
            launchView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void launchView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/spellmongerApp.fxml"));
        this.mainStage.setTitle("SpellmongerApp");
        this.mainStage.setScene(new Scene(root, 1000, 700));
        this.mainStage.show();

    }

    public void updateNamesView() {
        /*
        name1 = this.controller.getPlayerNames()[0];
        name2 = this.controller.getPlayerNames()[1];
        labelNamePlayers.setText(name1 + " vs " + name2);
*/
    }

    @Override
    public void update(IObservable o) {

        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            controller.getPlayerNames();
            updateNamesView();
        }

    }
}
