package ui;

import dao.CategoryDAO;
import dao.ExpenseDAO;
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
import model.Expense;

import java.time.LocalDate;

public class ExpenseView {

    private final Stage stage;
    private final VBox root;

    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ExpenseDAO expenseDAO = new ExpenseDAO();

    private final ComboBox<Category> cbCategory = new ComboBox<>();
    private final DatePicker dpDate = new DatePicker(LocalDate.now());
    private final TextField tfAmount = new TextField();
    private final TextField tfNote = new TextField();
    private final Label msg = new Label();

    private final TableView<Expense> table = new TableView<>();

    private Integer editingExpenseId = null;

    public ExpenseView(Stage stage) {
        this.stage = stage;
        this.root = new VBox(12);
        root.setPadding(new Insets(20));

        Label title = new Label("Manage Expenses (CRUD)");
        title.getStyleClass().add("title");

        cbCategory.setPromptText("Select category");
        tfAmount.setPromptText("Amount (e.g. 120.50)");
        tfNote.setPromptText("Note (optional)");

        Button btnAdd = new Button("Add Expense");
        Button btnUpdate = new Button("Update Expense");
        Button btnDelete = new Button("Delete Expense");
        Button btnRefresh = new Button("Refresh");
        Button btnClear = new Button("Clear");
        Button btnBack = new Button("Back");

        btnAdd.setOnAction(e -> addExpense());
        btnUpdate.setOnAction(e -> updateExpense());
        btnDelete.setOnAction(e -> deleteExpense());
        btnRefresh.setOnAction(e -> loadExpenses());
        btnClear.setOnAction(e -> clearForm());
        btnBack.setOnAction(e -> goBack());

        HBox formRow1 = new HBox(10,
                new Label("Date:"), dpDate,
                new Label("Category:"), cbCategory
        );

        HBox formRow2 = new HBox(10,
                new Label("Amount:"), tfAmount,
                new Label("Note:"), tfNote
        );

        HBox actions = new HBox(10, btnAdd, btnUpdate, btnDelete, btnRefresh, btnClear, btnBack);

        setupTable();
        loadCategories();
        loadExpenses();

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                editingExpenseId = selected.getExpenseId();
                dpDate.setValue(LocalDate.parse(selected.getDate()));
                tfAmount.setText(String.valueOf(selected.getAmount()));
                tfNote.setText(selected.getNote() == null ? "" : selected.getNote());

                for (Category c : cbCategory.getItems()) {
                    if (c.getCategoryId() == selected.getCategoryId()) {
                        cbCategory.setValue(c);
                        break;
                    }
                }
                msg.setText("Selected Expense ID: " + editingExpenseId);
            }
        });

        root.getChildren().addAll(title, formRow1, formRow2, actions, msg, table);
    }

    private void setupTable() {
        TableColumn<Expense, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colId.setPrefWidth(70);

        TableColumn<Expense, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDate.setPrefWidth(120);

        TableColumn<Expense, String> colCat = new TableColumn<>("Category");
        colCat.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colCat.setPrefWidth(180);

        TableColumn<Expense, Double> colAmt = new TableColumn<>("Amount");
        colAmt.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAmt.setPrefWidth(120);

        TableColumn<Expense, String> colNote = new TableColumn<>("Note");
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colNote.setPrefWidth(300);

        table.getColumns().addAll(colId, colDate, colCat, colAmt, colNote);
        table.setPrefHeight(330);
    }

    private void loadCategories() {
        cbCategory.setItems(FXCollections.observableArrayList(categoryDAO.getAllCategories()));
        if (!cbCategory.getItems().isEmpty()) cbCategory.setValue(cbCategory.getItems().get(0));
    }

    private void loadExpenses() {
        table.setItems(FXCollections.observableArrayList(expenseDAO.getAllExpenses()));
    }

    private void addExpense() {
        Category cat = cbCategory.getValue();
        if (cat == null) {
            msg.setText("Select category.");
            return;
        }
        if (dpDate.getValue() == null) {
            msg.setText("Select date.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(tfAmount.getText().trim());
        } catch (Exception e) {
            msg.setText("Enter valid amount.");
            return;
        }

        String note = tfNote.getText();
        boolean ok = expenseDAO.addExpense(cat.getCategoryId(), dpDate.getValue().toString(), amount, note);
        msg.setText(ok ? "Expense added." : "Add failed.");
        clearForm();
        loadExpenses();
    }

    private void updateExpense() {
        if (editingExpenseId == null) {
            msg.setText("Select an expense from table to update.");
            return;
        }

        Category cat = cbCategory.getValue();
        if (cat == null || dpDate.getValue() == null) {
            msg.setText("Select category and date.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(tfAmount.getText().trim());
        } catch (Exception e) {
            msg.setText("Enter valid amount.");
            return;
        }

        boolean ok = expenseDAO.updateExpense(editingExpenseId, cat.getCategoryId(),
                dpDate.getValue().toString(), amount, tfNote.getText());

        msg.setText(ok ? "Expense updated." : "Update failed.");
        clearForm();
        loadExpenses();
    }

    private void deleteExpense() {
        Expense selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg.setText("Select an expense from table to delete.");
            return;
        }

        boolean ok = expenseDAO.deleteExpense(selected.getExpenseId());
        msg.setText(ok ? "Expense deleted." : "Delete failed.");
        clearForm();
        loadExpenses();
    }

    private void clearForm() {
        editingExpenseId = null;
        dpDate.setValue(LocalDate.now());
        tfAmount.clear();
        tfNote.clear();
        table.getSelectionModel().clearSelection();
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
