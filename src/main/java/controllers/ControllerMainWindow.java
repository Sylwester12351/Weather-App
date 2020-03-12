package controllers;

import connection.Connect;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import office.TimeUpdate;
import office.ApiJSONConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ControllerMainWindow implements Initializable {
    private Connect connect = new Connect();
    private ApiJSONConverter apiJSONConverter = new ApiJSONConverter();

    private ScheduledExecutorService scheduledExecutorService;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private TimeUpdate timeUpdate = new TimeUpdate();

    int value;
    double xm, ym;

    Parent root1;
    @FXML
    ImageView optionsImg;
    @FXML
    ImageView closeImg;
    @FXML
    StackPane stackPane;
    @FXML
    Label labelTemp;
    @FXML
    Label labelTempMin;
    @FXML
    Label labelTempMax;
    @FXML
    Label labelCity;
    @FXML
    Label labelCountry;
    @FXML
    LineChart<String, Integer> lineChartHistory;
    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;

    private XYChart.Series<String, Integer> history = new XYChart.Series<>();
    private XYChart.Series<String, Integer> nowV = new XYChart.Series<>();

    private Image backgroundte1 = new Image(("backtexture.jpg"));
    private Image closeImage = new Image(("CLS.png"));
    private Image OptionImage = new Image(("OPS.jpg"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackPane.setBackground(new Background(new BackgroundImage(backgroundte1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        optionsImg.setImage(OptionImage);
        closeImg.setImage(closeImage);

        x.setLabel("Time");
        y.setLabel("Temp");
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        history.setName("Poprzedni zapis");
        nowV.setName(apiJSONConverter.getCity());
        updateOnStart();
    }

    public void drag(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - xm);
        stage.setY(mouseEvent.getScreenY() - ym);
    }

    public void press(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        xm = mouseEvent.getSceneX();
        ym = mouseEvent.getSceneY();
    }

    private void updateOnStart() { // z bazy danych
        Connection connection = connect.createConnection();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM temperature");
            while (resultSet.next()) {
                history.getData().add(new XYChart.Data<>(resultSet.getString(2), resultSet.getInt(3)));
            }
            lineChartHistory.getData().add(history);
            connect.createConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    private void update() {

        lineChartHistory.getData().add(nowV);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                Date now = new Date();
                //String temp = null;
                try {
                    apiJSONConverter.convert();
                    String temp = apiJSONConverter.getTemperature();
                    double a = Double.parseDouble(temp);
                    value = (int) a;
                    System.out.println(now);
                    nowV.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), value));
                    labelTemp.setText(apiJSONConverter.getTemperature());
                    labelTempMin.setText(apiJSONConverter.getTemperatureMin());
                    labelTempMax.setText(apiJSONConverter.getTemperatureMax());
                    labelCity.setText(apiJSONConverter.getCity());
                    labelCountry.setText(apiJSONConverter.getCountry());

                    PreparedStatement preparedStatement = connect.createConnection().prepareStatement("insert into temperature VALUES (default,?,?)", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setTimestamp(1, new java.sql.Timestamp(new java.util.Date().getTime()));
                    preparedStatement.setString(1, simpleDateFormat.format(now));
                    preparedStatement.setInt(2, value);
                    preparedStatement.execute();
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    resultSet.next();
                    connect.createConnection().close();

                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            });
        }, 0, timeUpdate.getTimerUpdate(), TimeUnit.MINUTES);
    }


    @FXML
    private void optionsWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OptionsWindow.fxml"));
            root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.setTitle("Options");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closeApplication() { // tutaj niech dodaje wartości do bazy danych
        System.exit(0);
    }
}
