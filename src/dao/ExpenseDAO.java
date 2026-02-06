package dao;

import db.DBUtil;
import model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    private static final int USER_ID = 1;

    public boolean addExpense(int categoryId, String date, double amount, String note) {
        String sql = "INSERT INTO expenses(user_id, category_id, date, amount, note) VALUES(?,?,?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, USER_ID);
            ps.setInt(2, categoryId);
            ps.setString(3, date);
            ps.setDouble(4, amount);
            ps.setString(5, note);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();

        String sql =
                "SELECT e.expense_id, e.category_id, c.name AS category_name, e.date, e.amount, e.note " +
                "FROM expenses e " +
                "JOIN categories c ON e.category_id = c.category_id " +
                "ORDER BY e.expense_id DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("date"),
                        rs.getDouble("amount"),
                        rs.getString("note")
                ));
            }
        } catch (SQLException ignored) { }
        return list;
    }

    public boolean updateExpense(int expenseId, int categoryId, String date, double amount, String note) {
        String sql = "UPDATE expenses SET category_id=?, date=?, amount=?, note=? WHERE expense_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ps.setString(2, date);
            ps.setDouble(3, amount);
            ps.setString(4, note);
            ps.setInt(5, expenseId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteExpense(int expenseId) {
        String sql = "DELETE FROM expenses WHERE expense_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expenseId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            return false;
        }
    }
}
