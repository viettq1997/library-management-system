package com.project2group4.librarymanagement.Entites;

import com.project2group4.librarymanagement.Models.*;
import com.project2group4.librarymanagement.db.JDBCConnect;
import java.sql.*;
import javafx.collections.*;
import com.project2group4.librarymanagement.utils.DateUtil;

public class BookEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<Book> GetAll() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Book b = new Book();

                b.setIndex(i);
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setCoyear(rs.getString("co_year"));
                b.setPrice(rs.getFloat("price"));
                b.setQuantity(rs.getInt("quantity"));
                b.setDescription(rs.getString("description"));
                b.setPublishingId(rs.getInt("publishId"));
                b.setPublishingName(rs.getString("publishingName"));
                b.setCategoryId(rs.getInt("categoryId"));
                b.setCategoryName(rs.getString("categoryName"));
                b.setAuthorId(rs.getInt("authorId"));
                b.setAuthorSignName(rs.getString("authorSignname"));

                books.add(b);
            }

            return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static Book GetBookWithBookId(int id) {
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id "
                + "WHERE b.id = ?";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Book b = new Book();

                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setCoyear(rs.getString("co_year"));
                b.setPrice(rs.getFloat("price"));
                b.setQuantity(rs.getInt("quantity"));
                b.setDescription(rs.getString("description"));
                b.setPublishingId(rs.getInt("publishId"));
                b.setPublishingName(rs.getString("publishingName"));
                b.setCategoryId(rs.getInt("categoryId"));
                b.setCategoryName(rs.getString("categoryName"));
                b.setAuthorId(rs.getInt("authorId"));
                b.setAuthorSignName(rs.getString("authorSignname"));

                return b;
            }

            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }
    
    
    public static ObservableList<Book> GetBookWithPublishCategoryAuthor(Publishing p, Author a, Category c) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id "
                + "WHERE b.categoryId = ? AND b.publishId = ? AND b.authorId = ? ";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, c.getId());
            preparedStatement.setInt(2, p.getId());
            preparedStatement.setInt(3, a.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Book b = new Book();

                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setCoyear(rs.getString("co_year"));
                b.setPrice(rs.getFloat("price"));
                b.setQuantity(rs.getInt("quantity"));
                b.setDescription(rs.getString("description"));
                b.setPublishingId(rs.getInt("publishId"));
                b.setPublishingName(rs.getString("publishingName"));
                b.setCategoryId(rs.getInt("categoryId"));
                b.setCategoryName(rs.getString("categoryName"));
                b.setAuthorId(rs.getInt("authorId"));
                b.setAuthorSignName(rs.getString("authorSignname"));

                books.add(b);
            }

            return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static Book GetBookWithBookName(String name) {
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id "
                + "WHERE b.name = ?";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Book book = new Book();

            book.setId(rs.getInt("id"));
            book.setName(rs.getString("name"));
            book.setCoyear(rs.getString("co_year"));
            book.setPrice(rs.getFloat("price"));
            book.setQuantity(rs.getInt("quantity"));
            book.setDescription(rs.getString("description"));
            book.setPublishingId(rs.getInt("publishId"));
            book.setPublishingName(rs.getString("publishingName"));
            book.setCategoryId(rs.getInt("categoryId"));
            book.setCategoryName(rs.getString("categoryName"));
            book.setAuthorId(rs.getInt("authorId"));
            book.setAuthorSignName(rs.getString("authorSignname"));

            return book;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
    }

    public static ObservableList<Book> GetBookByCategoryId(int categoryId) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id "
                + "WHERE b.categoryId = ?";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, categoryId);
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Book b = new Book();

                b.setIndex(i);
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setCoyear(rs.getString("co_year"));
                b.setPrice(rs.getFloat("price"));
                b.setQuantity(rs.getInt("quantity"));
                b.setDescription(rs.getString("description"));
                b.setPublishingId(rs.getInt("publishId"));
                b.setPublishingName(rs.getString("publishingName"));
                b.setCategoryId(rs.getInt("categoryId"));
                b.setCategoryName(rs.getString("categoryName"));
                b.setAuthorId(rs.getInt("authorId"));
                b.setAuthorSignName(rs.getString("authorSignname"));

                books.add(b);
            }

            return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static ObservableList<Book> GetBookByAuthorId(int authorId) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String sql = "Select b.*, c.name AS categoryName, p.name AS publishingName, a.sign_name as authorSignname "
                + "FROM books AS b "
                + "JOIN categories AS c ON b.categoryId = c.id "
                + "JOIN publishing AS p ON b.publishId = p.id "
                + "JOIN authors AS a ON b.authorId = a.id "
                + "WHERE b.authorId = ?";
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, authorId);
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Book b = new Book();

                b.setIndex(i);
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setCoyear(rs.getString("co_year"));
                b.setPrice(rs.getFloat("price"));
                b.setQuantity(rs.getInt("quantity"));
                b.setDescription(rs.getString("description"));
                b.setPublishingId(rs.getInt("publishId"));
                b.setPublishingName(rs.getString("publishingName"));
                b.setCategoryId(rs.getInt("categoryId"));
                b.setCategoryName(rs.getString("categoryName"));
                b.setAuthorId(rs.getInt("authorId"));
                b.setAuthorSignName(rs.getString("authorSignname"));

                books.add(b);
            }

            return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static boolean Add(Book book) {
        String sql = "INSERT INTO books "
                + "(name, co_year, price, quantity, description, categoryId, authorId, publishId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = JDBCConnect.getJDBCConnection();

            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, book.getName());
            preparedStatement.setDate(2, DateUtil.convertStringToDate(book.getCoyear()));
            preparedStatement.setFloat(3, book.getPrice());
            preparedStatement.setInt(4, book.getQuantity());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getCategoryId());
            preparedStatement.setInt(7, book.getAuthorId());
            preparedStatement.setInt(8, book.getPublishingId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean Update(Book book) {
        String sql = "UPDATE books "
                + "SET name = ?, co_year = ?, price = ?, quantity = ?, description = ?, categoryId = ?, authorId = ?, publishId = ? "
                + "WHERE id = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);

            preparedStatement.setString(1, book.getName());
            preparedStatement.setDate(2, DateUtil.convertStringToDate(book.getCoyear()));
            preparedStatement.setFloat(3, book.getPrice());
            preparedStatement.setInt(4, book.getQuantity());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getCategoryId());
            preparedStatement.setInt(7, book.getAuthorId());
            preparedStatement.setInt(8, book.getPublishingId());
            preparedStatement.setInt(9, book.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public static boolean Delete(int id) {
        String sql = "Delete from books WHERE id = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
