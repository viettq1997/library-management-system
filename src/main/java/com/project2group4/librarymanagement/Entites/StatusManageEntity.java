package com.project2group4.librarymanagement.Entites;

import com.project2group4.librarymanagement.Models.StatusManage;
import com.project2group4.librarymanagement.db.*;
import java.sql.*;
import javafx.collections.*;

public class StatusManageEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<StatusManage> GetAll() {
        ObservableList<StatusManage> statusManages = FXCollections.observableArrayList();

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from status_manage");
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                StatusManage statusManage = new StatusManage();

                statusManage.setId(rs.getInt("id"));
                statusManage.setName(rs.getString("name"));

                statusManages.add(statusManage);
            }

            return statusManages;
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

    public static StatusManage GetStatusManageById(int id) {

        try {
//          connect to database and execute query with hidden value
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from status_manage WHERE id = ?");
//          set hidden value in query
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();

//          set a StatusManage, add and return ObservableList with name is statusManage
            while (rs.next()) {
                StatusManage statusManage = new StatusManage();

                statusManage.setId(rs.getInt("id"));
                statusManage.setName(rs.getString("name"));

                return statusManage;
            }

        } catch (SQLException ex) {
//          show message in console screen when incorrect at query
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }
    
    public static StatusManage GetStatusManageByName(String name) {

        try {
//          connect to database and execute query with hidden value
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from status_manage WHERE name = ?");
//          set hidden value in query
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

//          set a StatusManage, add and return ObservableList with name is statusManage
            while (rs.next()) {
                StatusManage statusManage = new StatusManage();

                statusManage.setId(rs.getInt("id"));
                statusManage.setName(rs.getString("name"));

                return statusManage;
            }

        } catch (SQLException ex) {
//          show message in console screen when incorrect at query
            System.out.println(ex.getMessage());
        } finally {
//          Close databse at end
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }
}
