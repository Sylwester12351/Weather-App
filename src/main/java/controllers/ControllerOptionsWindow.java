package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import office.TimeUpdate;
import office.ApiJSONConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOptionsWindow implements Initializable {

    private ApiJSONConverter apiJSONConverter = new ApiJSONConverter();
    private TimeUpdate timeUpdate = new TimeUpdate();
    private ControllerMainWindow controllerMainWindow;

    @FXML
    TextField textFieldCity;
    @FXML
    TextField textFieldCountry;
    @FXML
    TextField textFieldApiID;
    @FXML
    TextField textFieldTimeUpdate;
    @FXML
    Button ButtonSaveBlack;
    @FXML
    RadioButton RadioButtBlackMode;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTextFields();
    }

    private void updateTextFields() {
        textFieldCity.setText(apiJSONConverter.getCity());
        textFieldCountry.setText(apiJSONConverter.getCountry());
        textFieldApiID.setText(apiJSONConverter.getApiID());
        textFieldTimeUpdate.setText(timeUpdate.toString());
    }

    @FXML
    private void save() {
        apiJSONConverter.setCity(textFieldCity.getText());
        apiJSONConverter.setCountry(textFieldCountry.getText());
        apiJSONConverter.setApiID(textFieldApiID.getText());
        int time = Integer.parseInt(textFieldTimeUpdate.getText());
        timeUpdate.setTimerUpdate(time);
        System.out.println("SAVED time is update");
        saveToFile();
        timeUpdate.updateConfTime();
    }

    private void saveToFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("conf"));
            bufferedWriter.write("API={" + textFieldApiID.getText() + "};");
            bufferedWriter.write("CITY={" + textFieldCity.getText() + "};");
            bufferedWriter.write("COUNTRY={" + textFieldCountry.getText() + "};");
            bufferedWriter.write("TIME={" + textFieldTimeUpdate.getText() + "};ENDCONF");
            bufferedWriter.close();
            System.out.println("ok");
        } catch (IOException e) {
            System.out.println("err save configuration to file");
        }
    }

    @FXML
    private void cancel() {
        updateTextFields();
    }

    public TextField getTextFieldCity() {
        return textFieldCity;
    }

    public TextField getTextFieldCountry() {
        return textFieldCountry;
    }

    public TextField getTextFieldApiID() {
        return textFieldApiID;
    }

    public TextField getTextFieldTimeUpdate() {
        return textFieldTimeUpdate;
    }
}

