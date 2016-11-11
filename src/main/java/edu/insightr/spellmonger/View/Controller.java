package edu.insightr.spellmonger.View;

/**
 * Created by Yasmeen on 09/11/2016.
 * Defines the view controller
 */
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField username1;
    public TextField username2;
    public Button goButton;
    public Button clearButton;
    public ToggleButton aiButton;
    public Label actiontarget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is loaded!");
    }

    public void go(ActionEvent actionEvent) {
        if("".equals(username1.getText()) || "".equals(username2.getText())) actiontarget.setText("Field(s) empty");
        else if(username1.getText().equals(username2.getText())) actiontarget.setText("Choose a different player");
        else actiontarget.setText(username1.getText() + " VS " + username2.getText());
    }

    public void clearGame(ActionEvent actionEvent) {
        username1.clear();
        username2.clear();
    }

    public void addAI(ActionEvent actionEvent) {
        if(aiButton.isSelected())username2.setText("PC");
        else username2.clear();
    }
}