package com.project2group4.librarymanagement.Entites;

import com.project2group4.librarymanagement.Models.Role;
import java.sql.*;
import com.project2group4.librarymanagement.db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoleEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<Role> GetAll() {
        ObservableList<Role> roles = FXCollections.observableArrayList();

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from roles");
            rs = preparedStatement.executeQuery();

            for (int i = 1; rs.next(); i++) {
                Role r = new Role();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                roles.add(r);
            }

            return roles;
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

    public static Role GetOneByName(String name) {

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareCall("Select * from roles WHERE name = ?");
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Role r = new Role();

                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                return r;
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

}
