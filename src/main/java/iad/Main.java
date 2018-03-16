package iad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.NeuronUtils;

import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String.valueOf("0");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));


        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("chart.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
