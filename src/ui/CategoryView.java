package ui;

import dao.CategoryDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Category;

public class CategoryView {

    private final Stage stage;
    private final VBox root;

    private final CategoryDAO dao = new CategoryDAO();

    private final TextField tfName = new TextField();
    private final Label msg = new Label();

    private final TableView<Category> table = new TableView<>();

    public CategoryView(Stage stage) {
        this.stage = stage;
        this.root = new VBox(12);
        root.setPadding(new Insets(20));

        Label title = new Label("Manage Categories (CRUD)");
        title.getStyleClass().add("title");

        tfName.setPromptText("Category name");

        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");
        Button btnRefresh = new Button("Refresh");
        Button btnBack = new Button("Back");

        btnAdd.setOnAction(e -> addCategory());
        btnUpdate.setOnAction(e -> updateCategory());
        btnDelete.setOnAction(e -> deleteCategory());
        btnRefresh.setOnAction(e -> loadCategories());
        btnBack.setOnAction(e -> goBack());

        HBox actions = new HBox(10, btnAdd, btnUpdate, btnDelete, btnRefresh, btnBack);

        setupTable();
        loadCategories();

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                tfName.setText(selected.getName());
                msg.setText("");
            }
        });

        root.getChildren().addAll(title, new Label("Name:"), tfName, actions, msg, table);
    }

    private void setupTable() {
        TableColumn<Category, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colId.setPrefWidth(80);

        TableColumn<Category, String> colName = new TableColumn<>("Category");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(300);

        table.getColumns().addAll(colId, colName);
        table.setPrefHeight(350);
    }

    private void loadCategories() {
        table.setItems(FXCollections.observableArrayList(dao.getAllCategories()));
    }

    private void addCategory() {
        String name = tfName.getText();
        if (name == null || name.trim().isEmpty()) {
            msg.setText("Enter category name.");
            return;
        }

        boolean ok = dao.addCategory(name);
        msg.setText(ok ? "Category added." : "Failed (maybe duplicate name).");
        tfName.clear();
        loadCategories();
    }

    private void updateCategory() {
        Category selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg.setText("Select a category from table.");
            return;
        }
        String name = tfName.getText();
        if (name == null || name.trim().isEmpty()) {
            msg.setText("Enter category name.");
            return;
        }

        boolean ok = dao.updateCategory(selected.getCategoryId(), name);
        msg.setText(ok ? "Category updated." : "Update failed.");
        tfName.clear();
        loadCategories();
    }

    private void deleteCategory() {
        Category selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg.setText("Select a category from table.");
            return;
        }

        boolean ok = dao.deleteCategory(selected.getCategoryId());
        msg.setText(ok ? "Category deleted." : "Delete failed (maybe used in expenses).");
        tfName.clear();
        loadCategories();
    }

    private void goBack() {
        HomeView home = new HomeView(stage);
        Scene scene = new Scene(home.getRoot(), 900, 550);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public Parent getRoot() {
        return root;
    }
}
