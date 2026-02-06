package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.HomeView;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        HomeView home = new HomeView(stage);

        Scene scene = new Scene(home.getRoot(), 900, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setTitle("QA4 Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
