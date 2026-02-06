package ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeView {
    private final Stage stage;
    private final VBox root;

    public HomeView(Stage stage) {
        this.stage = stage;
        this.root = new VBox(15);
        root.setPadding(new Insets(25));

        Label title = new Label("QA4 Mini Project: Expense Tracker");
        title.getStyleClass().add("title");

        Label info = new Label("Choose a module to manage:");
        Button btnCategories = new Button("Manage Categories (CRUD)");
        Button btnExpenses = new Button("Manage Expenses (CRUD)");
        Button btnExit = new Button("Exit");

        btnCategories.setOnAction(e -> openCategories());
        btnExpenses.setOnAction(e -> openExpenses());
        btnExit.setOnAction(e -> stage.close());

        root.getChildren().addAll(title, info, btnCategories, btnExpenses, btnExit);
    }

    private void openCategories() {
        CategoryView view = new CategoryView(stage);
        Scene scene = new Scene(view.getRoot(), 900, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    private void openExpenses() {
        ExpenseView view = new ExpenseView(stage);
        Scene scene = new Scene(view.getRoot(), 900, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public Parent getRoot() {
        return root;
    }
}
