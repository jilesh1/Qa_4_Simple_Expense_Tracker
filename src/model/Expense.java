package model;

public class Expense {
    private int expenseId;
    private int categoryId;
    private String categoryName;
    private String date;
    private double amount;
    private String note;

    public Expense(int expenseId, int categoryId, String categoryName, String date, double amount, String note) {
        this.expenseId = expenseId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.date = date;
        this.amount = amount;
        this.note = note;
    }

    public int getExpenseId() { return expenseId; }
    public void setExpenseId(int expenseId) { this.expenseId = expenseId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
