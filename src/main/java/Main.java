import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import office.TimeUpdate;


import java.util.List;

/**
 * Author: Sylwester Gawroński
 * control+alt+l
 */

public class Main extends Application {
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        TimeUpdate timeUpdate = new TimeUpdate().updateConfTime();
        ReadConfig readConfig = new ReadConfig().readConf();
        root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        primaryStage.setTitle("WeatherApplication V2.0.0 BETA SylwesterGawroński");
        primaryStage.setScene(new Scene(root, 240, 500));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOpacity(0.9);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
