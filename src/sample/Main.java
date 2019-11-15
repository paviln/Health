package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 500, 325);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Health");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("hospital.png")));
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(500);
        primaryStage.setMaxHeight(325);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }

}