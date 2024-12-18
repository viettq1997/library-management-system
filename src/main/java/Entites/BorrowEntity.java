package Entites;

import Models.Account;
import Models.Book;
import Models.Borrow;
import Models.StatusBorrow;
import java.sql.*;
import db.*;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import utils.DateUtil;

public class BorrowEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<Borrow> GetAll() {
        ObservableList<Borrow> list = FXCollections.observableArrayList();
        String query = """
                Select bw.id,a.UID,bw.amount_of_pay, b.name as bookName,bw.borrowAt,bw.refundAt ,sb.name as stautsName,a.username as accountName,bw.statusId,bw.accountId, mb.bookId,bw.manageId from borrow as bw
                 join status_borrow as sb on bw.statusId = sb.id
                 join manage_book as mb on bw.manageId = mb.id
                 join accounts as a on bw.accountId = a.id
                 join books as b on mb.bookId = b.id""";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            int i = 1;
            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setIndex(i);
                borrow.setId(rs.getInt("id"));
                borrow.setBorrowAt(rs.getString("borrowAt"));
                borrow.setRefundAt(rs.getString("refundAt"));
                borrow.setAmount_of_pay(rs.getFloat("amount_of_pay"));
                borrow.setManageId(rs.getInt("manageId"));
                borrow.setStatusId(i);
                borrow.setStatusName(rs.getString("stautsName"));
                borrow.setBookName(rs.getString("bookName"));
                borrow.setAccountName(rs.getString("accountName"));
                list.add(borrow);
                i++;
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public ObservableList<Borrow> GetBorrowByAccountId(int accountId) {
        ObservableList<Borrow> list = FXCollections.observableArrayList();
        String query = "SELECT b.* "
                + "FROM borrow AS b "
                + "WHERE b.accountId = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountId);
            rs = preparedStatement.executeQuery();

            for (int i=1; rs.next(); i++) {
                Borrow borrow = new Borrow();
                
                borrow.setIndex(i);
                borrow.setId(rs.getInt("id"));
                borrow.setBorrowAt(rs.getString("borrowAt"));
                borrow.setRefundAt(rs.getString("refundAt"));
                borrow.setAmount_of_pay(rs.getFloat("amount_of_pay"));
                borrow.setManageId(rs.getInt("manageId"));
                borrow.setStatusId(rs.getInt("statusId"));

                list.add(borrow);
            }

            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }
    
    public ObservableList<Borrow> SearchBorrowByAccountId(int accountId, String search) {
        ObservableList<Borrow> list = FXCollections.observableArrayList();
        String query = "SELECT b.* "
                + "FROM borrow AS b "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE b.accountId = ? AND books.name like ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, "%" + search + "%");
            rs = preparedStatement.executeQuery();

            for (int i=1; rs.next(); i++) {
                Borrow borrow = new Borrow();
                
                borrow.setIndex(i);
                borrow.setId(rs.getInt("id"));
                borrow.setBorrowAt(rs.getString("borrowAt"));
                borrow.setRefundAt(rs.getString("refundAt"));
                borrow.setAmount_of_pay(rs.getFloat("amount_of_pay"));
                borrow.setManageId(rs.getInt("manageId"));
                borrow.setStatusId(rs.getInt("statusId"));

                list.add(borrow);
            }

            return list;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static ObservableList<Borrow> Search(String name) {
        ObservableList<Borrow> list = FXCollections.observableArrayList();
        String query = "Select bw.id,a.UID,bw.amount_of_pay, b.name as bookName,bw.borrowAt,bw.refundAt ,sb.name as stautsName,a.username as accountName,bw.statusId,bw.accountId, mb.bookId,bw.manageId from borrow as bw\n"
                + " join status_borrow as sb on bw.statusId = sb.id\n"
                + " join manage_book as mb on bw.manageId = mb.id\n"
                + " join accounts as a on bw.accountId = a.id\n"
                + " join books as b on mb.bookId = b.id  where b.name like '%" + name + "%'";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            int i = 1;
            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setIndex(i);
                borrow.setId(rs.getInt("id"));
                borrow.setBorrowAt(rs.getString("borrowAt"));
                borrow.setRefundAt(rs.getString("refundAt"));
                borrow.setAmount_of_pay(rs.getFloat("amount_of_pay"));
                borrow.setManageId(rs.getInt("manageId"));
                borrow.setStatusId(i);
                borrow.setStatusName(rs.getString("stautsName"));
                borrow.setBookName(rs.getString("bookName"));
                borrow.setAccountName(rs.getString("accountName"));
                list.add(borrow);
                i++;
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static boolean Add(Borrow obj) {
        String query = "INSERT INTO borrow (borrowAt, refundAt, amount_of_pay, manageId, statusId, accountId) VALUES (?, ?, ?, ? ,? ,?, ?)";
        try {
            connection = JDBCConnect.getJDBCConnection();

            int id = newStatusID();
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, DateUtil.convertStringToDate(obj.getBorrowAt()));
            preparedStatement.setDate(3, DateUtil.convertStringToDate(obj.getRefundAt()));
            preparedStatement.setFloat(4, obj.getAmount_of_pay());
            preparedStatement.setInt(5, id);
            preparedStatement.setInt(6, obj.getStatusId());
            preparedStatement.setInt(7, obj.getAccountid().get());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
    }

    public static int newStatusID() throws SQLException {
        String manageId = "select id from manage_book order by id DESC LIMIT 1";
        int id = 0;
        connection = JDBCConnect.getJDBCConnection();
        preparedStatement = connection.prepareStatement(manageId);
        rs = preparedStatement.executeQuery();
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    public static boolean Update(Borrow obj) {
        String sql_borrow = "UPDATE borrow SET borrowAt = ?,refundAt = ?,statusId = ?, accountId = ? WHERE (id = ?);";
        String sql_manage_lib = "UPDATE manage_book SET bookId = ? WHERE (id = ?);";

        try {
            System.out.println("Insert Manage_book");
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql_manage_lib);
            preparedStatement.setInt(1, obj.getBookid().intValue());
            preparedStatement.setInt(2, obj.getManageId());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sql_borrow);
            preparedStatement.setDate(1, DateUtil.convertStringToDate(obj.getBorrowAt()));
            preparedStatement.setDate(2, DateUtil.convertStringToDate(obj.getRefundAt()));
            preparedStatement.setInt(3, obj.getStatusId());
            preparedStatement.setInt(4, obj.getAccountid().intValue());
            preparedStatement.setInt(5, obj.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
    }
    
    public static boolean Delete(int id) {
        String query = "DELETE FROM borrow WHERE id = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return false;
    }

    public static int selectAccountIndex(ComboBox txtAccount) {
        ArrayList<Integer> list = new ArrayList<>();
        int accountID = 0;
        for (int i = 0; i < txtAccount.getItems().size(); i++) {
            if (txtAccount.getItems().get(i) == txtAccount.getValue()) {
                accountID = (i);
                break;
            }
        }

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM accounts;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
        } catch (SQLException | NullPointerException ex) {
        }
        return list.get(accountID);
    }

    public static int selectBookIndex(ComboBox txtBook) {
        ArrayList<Integer> list = new ArrayList<>();
        int bookID = 0;
        for (int i = 0; i < txtBook.getItems().size(); i++) {
            if (txtBook.getItems().get(i) == txtBook.getValue()) {
                bookID = (i);
                break;
            }
        }
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM books;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
        } catch (SQLException | NullPointerException ex) {
        }
        return list.get(bookID);
    }

    public static int selectStatusIndex(ComboBox txtStatus) {
        ArrayList<Integer> list = new ArrayList<>();
        int StatusID = 0;
        for (int i = 0; i < txtStatus.getItems().size(); i++) {
            if (txtStatus.getItems().get(i) == txtStatus.getValue()) {
                StatusID = (i);
                break;
            }
        }
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM status_borrow;");
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("id"));
            }
        } catch (SQLException | NullPointerException ex) {
        }

        return list.get(StatusID);
    }

    public static void data_Status(ComboBox<StatusBorrow> txtStatus) {
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM status_borrow");
            rs = preparedStatement.executeQuery();
            ObservableList<StatusBorrow> list = FXCollections.observableArrayList();
            while (rs.next()) {
                StatusBorrow status_borrow = new StatusBorrow();
                status_borrow.setId(rs.getInt("id"));
                status_borrow.setName(rs.getString("name"));
                list.add(status_borrow);
            }
            txtStatus.setItems(list);
        } catch (SQLException | NullPointerException ex) {
        }
    }

    public static void data_Books(ComboBox<Book> txtBook) {
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM books b join manage_book mb on mb.bookid = b.id where mb.statusid = 2");
            rs = preparedStatement.executeQuery();
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                list.add(book);
            }
            txtBook.setItems(list);
        } catch (SQLException | NullPointerException ignored) {
        }
    }

    public static void data_Account(ComboBox<Account> txtAccount) {
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("SELECT * FROM accounts where status = 1");
            rs = preparedStatement.executeQuery();
            ObservableList<Account> list = FXCollections.observableArrayList();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                list.add(account);
            }
            txtAccount.setItems(list);
        } catch (SQLException | NullPointerException ignored) {
        }
    }

}
