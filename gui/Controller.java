package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

import MCServerInfo.ServerInfo;

public class Controller{

    @FXML
    TextField address_input;
    @FXML
    TextField port_input;
    @FXML
    Label loading_label, name_label, player_label, desc_label;

    @FXML
    public void ready() {
        address_input.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) run();
        });

        port_input.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) run();
        });

        loading_label.setTextFill(Paint.valueOf("WHITE"));
        loading_label.setText("Press Go or Enter to start...");
        name_label.setText("");
        player_label.setText("");
        desc_label.setText("");

    }

    @FXML
    public void run() {

        loading_label.setTextFill(Paint.valueOf("RED"));
        loading_label.setText("Failed, try again...");

        ServerInfo serverInfo = new ServerInfo(""+address_input.getText(), ""+port_input.getText());

        if (serverInfo.exists()) {
            name_label.setText("Info:\n\nAddresse: " + serverInfo.toString() + "\nVersion: " + serverInfo.getVersion());
            player_label.setText("Es sind " + serverInfo.getOnlinePlayers() + " Spieler von " + serverInfo.getMaxPlayers() + " online");
            desc_label.setText("Server Beschreibung:\n\n" + serverInfo.getDescription());
            desc_label.setAlignment(Pos.CENTER);
            loading_label.setText("Done!");
            loading_label.setTextFill(Paint.valueOf("LIMEGREEN"));
        } else {
            loading_label.setText("Server nicht verfügbar");
            name_label.setText("");
            player_label.setText("");
            desc_label.setText("");
        }


    }

}
