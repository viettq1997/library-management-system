package com.project2group4.librarymanagement.Entites;

import com.project2group4.librarymanagement.Models.ManageBook;
import com.project2group4.librarymanagement.Models.Top5AuthorsIntersted;
import com.project2group4.librarymanagement.Models.Top5CategoriesInterested;
import com.project2group4.librarymanagement.db.*;
import java.sql.*;
import javafx.collections.*;

public class ManageBookEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<ManageBook> GetAllBookInfo() {
        ObservableList<ManageBook> categories = FXCollections.observableArrayList();
        String sql = "Select manage_book.*, accounts.UID AS accountUID, books.name AS bookName, books.co_year, books.price, books.quantity, books.description, books.categoryId, books.authorId, books.publishId, status_manage.name AS statusManageName "
                + "FROM manage_book "
                + "JOIN accounts ON manage_book.accountId = accounts.id "
                + "JOIN books ON manage_book.bookId = books.id "
                + "JOIN status_manage ON manage_book.statusId = status_manage.id";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                ManageBook mb = new ManageBook();

                mb.setIndex(i);
                mb.setId(rs.getInt("id"));
                mb.getAccount().setId(rs.getInt("accountId"));
                mb.getAccount().setUID(rs.getString("accountUID"));
                mb.getBook().setId(rs.getInt("bookId"));
                mb.getBook().setName(rs.getString("bookName"));
                mb.getBook().setCoyear(rs.getString("co_year"));
                mb.getBook().setPrice(rs.getFloat("price"));
                mb.getBook().setQuantity(rs.getInt("quantity"));
                mb.getBook().setDescription(rs.getString("description"));
                mb.getBook().setCategoryId(rs.getInt("categoryId"));
                mb.getBook().setAuthorId(rs.getInt("authorId"));
                mb.getBook().setPublishingId(rs.getInt("publishId"));
                mb.getStatus().setId(rs.getInt("statusId"));
                mb.getStatus().setName(rs.getString("statusManageName"));
                mb.setCreatedAt(rs.getString("createdAt"));
                mb.setUpdatedAt(rs.getString("updatedAt"));

                categories.add(mb);
            }

            return categories;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static ManageBook GetAllBookInfoById(int id) {
        String sql = "Select manage_book.*, accounts.UID AS accountUID, books.name AS bookName, books.co_year, books.price, books.quantity, books.description, books.categoryId, books.authorId, books.publishId, status_manage.name AS statusManageName "
                + "FROM manage_book "
                + "JOIN accounts ON manage_book.accountId = accounts.id "
                + "JOIN books ON manage_book.bookId = books.id "
                + "JOIN status_manage ON manage_book.statusId = status_manage.id "
                + "WHERE manage_book.id = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                ManageBook mb = new ManageBook();

                mb.setIndex(1);
                mb.setId(rs.getInt("id"));
                mb.getAccount().setId(rs.getInt("accountId"));
                mb.getAccount().setUID(rs.getString("accountUID"));
                mb.getBook().setId(rs.getInt("bookId"));
                mb.getBook().setName(rs.getString("bookName"));
                mb.getBook().setCoyear(rs.getString("co_year"));
                mb.getBook().setPrice(rs.getFloat("price"));
                mb.getBook().setQuantity(rs.getInt("quantity"));
                mb.getBook().setDescription(rs.getString("description"));
                mb.getBook().setCategoryId(rs.getInt("categoryId"));
                mb.getBook().setAuthorId(rs.getInt("authorId"));
                mb.getBook().setPublishingId(rs.getInt("publishId"));
                mb.getStatus().setId(rs.getInt("statusId"));
                mb.getStatus().setName(rs.getString("statusManageName"));
                mb.setCreatedAt(rs.getString("createdAt"));
                mb.setUpdatedAt(rs.getString("updatedAt"));

                return mb;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static int CountBookInterestedByUID(String UID) {
        String sql = "Select count(bw.id) as totalInterestBook "
                + "FROM borrow AS bw "
                + "JOIN accounts ON bw.accountid = accounts.id "
                + "WHERE accounts.UID = ? ";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("totalInterestBook");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return 0;
    }

    public static int CountAuthorInterestedByUID(String UID) {
        String sql = "Select count(distinct books.authorId) as countAuthors "
                + "FROM  borrow AS bw "
                + "JOIN accounts ON bw.accountId = accounts.id "
                + "JOIN manage_book mb ON bw.manageid = mb.id "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE accounts.UID = ? ";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("countAuthors");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return 0;
    }

    public static int CountCategoryInterestedByUID(String UID) {
        String sql = "Select count(distinct books.categoryId) as countCategories "
                + "FROM  borrow AS bw "
                + "JOIN accounts ON bw.accountId = accounts.id "
                + "JOIN manage_book mb ON bw.manageid = mb.id "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE accounts.UID = ? ";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("countCategories");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return 0;
    }

    public static int CountPublishingInterestedByUID(String UID) {
        String sql = "Select count(distinct books.publishId) as countPublishers "
                + "FROM  borrow AS bw "
                + "JOIN accounts ON bw.accountId = accounts.id "
                + "JOIN manage_book mb ON bw.manageid = mb.id "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE accounts.UID = ? ";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("countPublishers");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return 0;
    }

    public static ObservableList<Top5CategoriesInterested> Top5CategoryInterestedByUID(String UID) {
        ObservableList<Top5CategoriesInterested> categories = FXCollections.observableArrayList();
        String sql = "Select books.categoryId AS id , COUNT(distinct books.categoryId) AS totalBook "
                + "FROM  borrow AS bw "
                + "JOIN accounts ON bw.accountId = accounts.id "
                + "JOIN manage_book mb ON mb.id = bw.manageid "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE accounts.UID = ? "
                + "GROUP BY books.categoryId "
                + "LIMIT ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            preparedStatement.setInt(2, 5);
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Top5CategoriesInterested mb = new Top5CategoriesInterested();

                mb.setId(rs.getInt("id"));
                mb.setTotal(rs.getInt("totalBook"));

                categories.add(mb);
            }

            return categories;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static ObservableList<Top5AuthorsIntersted> Top5AuthorInterestedByUID(String UID) {
        ObservableList<Top5AuthorsIntersted> categories = FXCollections.observableArrayList();
        String sql = "Select books.authorId AS id , COUNT(distinct books.authorId) AS totalBook "
                + "FROM  borrow AS bw "
                + "JOIN accounts ON bw.accountId = accounts.id "
                + "JOIN manage_book mb ON mb.id = bw.manageid "
                + "JOIN books ON mb.bookId = books.id "
                + "WHERE accounts.UID = ? "
                + "GROUP BY books.authorId "
                + "LIMIT ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, UID);
            preparedStatement.setInt(2, 5);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Top5AuthorsIntersted top5Authors = new Top5AuthorsIntersted();

                top5Authors.setId(rs.getInt("id"));
                top5Authors.setTotal(rs.getInt("totalBook"));

                categories.add(top5Authors);
            }

            return categories;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static ObservableList<ManageBook> SearchByBookName(String search) {
        ObservableList<ManageBook> categories = FXCollections.observableArrayList();
        String sql = "Select manage_book.*, accounts.UID AS accountUID, books.name AS bookName, books.co_year, books.price, books.quantity, books.description, books.categoryId, books.authorId, books.publishId, status_manage.name AS statusManageName "
                + "FROM manage_book "
                + "JOIN accounts ON manage_book.accountId = accounts.id "
                + "JOIN books ON manage_book.bookId = books.id "
                + "JOIN status_manage ON manage_book.statusId = status_manage.id "
                + "WHERE books.name like ?";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, "%" + search + "%");
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                ManageBook mb = new ManageBook();

                mb.setIndex(i);
                mb.setId(rs.getInt("id"));
                mb.getAccount().setId(rs.getInt("accountId"));
                mb.getAccount().setUID(rs.getString("accountUID"));
                mb.getBook().setId(rs.getInt("bookId"));
                mb.getBook().setName(rs.getString("bookName"));
                mb.getBook().setCoyear(rs.getString("co_year"));
                mb.getBook().setPrice(rs.getFloat("price"));
                mb.getBook().setQuantity(rs.getInt("quantity"));
                mb.getBook().setDescription(rs.getString("description"));
                mb.getBook().setCategoryId(rs.getInt("categoryId"));
                mb.getBook().setAuthorId(rs.getInt("authorId"));
                mb.getBook().setPublishingId(rs.getInt("publishId"));
                mb.getStatus().setId(rs.getInt("statusId"));
                mb.getStatus().setName(rs.getString("statusManageName"));
                mb.setCreatedAt(rs.getString("createdAt"));
                mb.setUpdatedAt(rs.getString("updatedAt"));

                categories.add(mb);
            }

            return categories;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static boolean Add(ManageBook obj) {
        String sql = "INSERT INTO manage_book (price_per_book, accountId, bookId, statusId, createdAt, updatedAt) values(?, ?, ?, ?, ?, ?)";

        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);

            preparedStatement.setFloat(1, obj.getPricePerBook());
            preparedStatement.setInt(2, obj.getAccount().getId());
            preparedStatement.setInt(3, obj.getBook().getId());
            preparedStatement.setInt(4, obj.getStatus().getId());
            preparedStatement.setDate(5, preDate);
            preparedStatement.setDate(6, preDate);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean Update(ManageBook obj) {
        String sql = "UPDATE manage_book SET price_per_book = ?, statusId = ?, updatedAt = ? "
                + "WHERE bookId = ? AND accountId = ?";
        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setFloat(1, obj.getPricePerBook());
            preparedStatement.setInt(2, obj.getStatus().getId());
            preparedStatement.setDate(3, preDate);
            preparedStatement.setInt(4, obj.getBook().getId());
            preparedStatement.setInt(5, obj.getAccount().getId());

            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Updated Successfully !");

                return true;
            } else {
                System.out.println("Updated Fail !");

                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean Delete(int id) {
        String sql = "Delete FROM manage_book WHERE bookId = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Delete Successfully !");

                return true;
            } else {
                System.out.println("Delete Faild !");

                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
