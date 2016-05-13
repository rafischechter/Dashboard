package Dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        root.getStylesheets().add("Dashboard/style1.css");
        primaryStage.setTitle("Dashboard");
        primaryStage.getIcons().add(new Image("file:./src/assets/Dashboard.png"));
        primaryStage.setScene(new Scene(root));


        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
