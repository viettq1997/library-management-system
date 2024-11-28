package Entites;

import static Entites.PublishingEntity.connection;
import static Entites.PublishingEntity.preparedStatement;
import static Entites.PublishingEntity.rs;
import static utils.DateUtil.convertStringToDate;

import Models.Publishing;
import java.sql.*;
import db.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PublishingEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<Publishing> GetAll() {
        ObservableList<Publishing> publishing = FXCollections.observableArrayList();

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from publishing");
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Publishing p = new Publishing();

                p.setIndex(i);
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setCoyear(rs.getString("co_year"));
                p.setCreatedAt(rs.getString("createdAt"));
                p.setUpdatedAt(rs.getString("updatedAt"));

                publishing.add(p);
            }

            return publishing;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static Publishing GetPublishingWithId(int id) {

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from publishing WHERE id = ?");
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Publishing p = new Publishing();

                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setCoyear(rs.getString("co_year"));
                p.setCreatedAt(rs.getString("createdAt"));
                p.setUpdatedAt(rs.getString("updatedAt"));

                return p;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static Publishing GetPublishingWithName(String name) {

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from publishing WHERE name = ?");
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Publishing p = new Publishing();

                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setCoyear(rs.getString("co_year"));
                p.setCreatedAt(rs.getString("createdAt"));
                p.setUpdatedAt(rs.getString("updatedAt"));

                return p;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static ObservableList<Publishing> Search(String search) {
        ObservableList<Publishing> publishing = FXCollections.observableArrayList();

        try {
//          connect to database and execute query with hidden value
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from publishing WHERE name like ?");
//          set hidden value in query
            preparedStatement.setString(1, "%" + search + "%");
            rs = preparedStatement.executeQuery();

//          set a Publishing, add and return ObservableList with name is publishing
            for (int i = 1; rs.next(); i++) {
                Publishing p = new Publishing();

                p.setIndex(i);
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setCoyear(rs.getString("co_year"));
                p.setCreatedAt(rs.getString("createdAt"));
                p.setUpdatedAt(rs.getString("updatedAt"));

                publishing.add(p);
            }

            return publishing;
        } catch (SQLException ex) {
//          show message in console screen when wrong at query
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }
        return null;
    }

    public static boolean Add(Publishing obj) {
        String sql = "Insert into publishing (name, address, co_year, createdAt, updatedAt) values(?, ?, ?, ?, ?)";
//      set time at present with accuracy approximately is millis
        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);

        try {
//          connect to database and execute query with hidden value
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
//          set hidden value in query
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getAddress());
            preparedStatement.setDate(3, convertStringToDate(obj.getCoyear()));
            preparedStatement.setDate(4, preDate);
            preparedStatement.setDate(5, preDate);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean Update(Publishing obj) {
        String sql = "UPDATE publishing SET name = ?, address= ?, co_year = ?, updatedAt = ? WHERE id = ?";
        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getAddress());
            preparedStatement.setDate(3, convertStringToDate(obj.getCoyear()));
            preparedStatement.setDate(4, preDate);
            preparedStatement.setInt(5, obj.getId());
            System.out.println(preparedStatement.getResultSet());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean Delete(int id) {
        String sql = "Delete FROM publishing WHERE id = ?";

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
