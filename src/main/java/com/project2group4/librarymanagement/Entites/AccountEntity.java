package com.project2group4.librarymanagement.Entites;

import com.project2group4.librarymanagement.Models.Account;
import java.sql.*;
import com.project2group4.librarymanagement.db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.codec.digest.DigestUtils;

import static com.project2group4.librarymanagement.utils.DateUtil.convertStringToDate;

public class AccountEntity {

    public static Connection connection = null;
    public static PreparedStatement preparedStatement = null;
    public static ResultSet rs = null;

    public static ObservableList<Account> GetAll() {
        ObservableList<Account> list = FXCollections.observableArrayList();

        String sql = "SELECT accounts.*, roles.name as roleName "
                + "FROM accounts "
                + "JOIN roles ON accounts.roleId = roles.id "
                + "WHERE accounts.status = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            rs = preparedStatement.executeQuery();
            int i = 1;
            while (rs.next()) {
                Account acc = new Account();
                acc.setIndex(i);
                acc.setId(rs.getInt("id"));
                acc.setUID(rs.getString("UID"));
                acc.setUsername(rs.getString("username"));
                acc.setAvatar(rs.getString("avatar"));
                acc.setPassword(rs.getString("password"));
                acc.setFull_name(rs.getString("full_name"));
                acc.setGender(rs.getInt("gender"));
                acc.setEmail(rs.getString("email"));
                acc.setDob(rs.getString("dob"));
                acc.setMobile(rs.getString("mobile"));
                acc.setStatus(rs.getInt("status"));
                acc.setRoleId(rs.getInt("roleId"));
                acc.setRoleName(rs.getString("roleName"));
                acc.setCreatedAt(rs.getString("createdAt"));
                acc.setUpdatedAt(rs.getString("updatedAt"));
                list.add(acc);

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

    public static Account GetAccountByUID(String UID) {
        String sql = "SELECT accounts.*, roles.name as roleName "
                + "FROM accounts "
                + "JOIN roles ON accounts.roleId = roles.id "
                + "WHERE accounts.UID = ? AND accounts.status = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UID);
            preparedStatement.setInt(2, 1);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Account acc = new Account();

                acc.setId(rs.getInt("id"));
                acc.setUID(rs.getString("UID"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setFull_name(rs.getString("full_name"));
                acc.setGender(rs.getInt("gender"));
                acc.setEmail(rs.getString("email"));
                acc.setDob(rs.getString("dob"));
                acc.setMobile(rs.getString("mobile"));
                acc.setStatus(rs.getInt("status"));
                acc.setRoleId(rs.getInt("roleId"));
                acc.setRoleName(rs.getString("roleName"));
                acc.setCreatedAt(rs.getString("createdAt"));
                acc.setUpdatedAt(rs.getString("updatedAt"));

                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closeResultSet(rs);
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return null;
    }

    public static Account GetAccountByUsername(String username) {
        String sql = "SELECT accounts.*, roles.name as roleName "
                + "FROM accounts JOIN roles ON accounts.roleId = roles.id "
                + "WHERE accounts.username = ? AND accounts.status = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, 1);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Account acc = new Account();

                acc.setId(rs.getInt("id"));
                acc.setUID(rs.getString("UID"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setFull_name(rs.getString("full_name"));
                acc.setGender(rs.getInt("gender"));
                acc.setEmail(rs.getString("email"));
                acc.setDob(rs.getString("dob"));
                acc.setMobile(rs.getString("mobile"));
                acc.setStatus(rs.getInt("status"));
                acc.setRoleId(rs.getInt("roleId"));
                acc.setRoleName(rs.getString("roleName"));
                acc.setCreatedAt(rs.getString("createdAt"));
                acc.setUpdatedAt(rs.getString("updatedAt"));

                return acc;
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

    public static ObservableList<Account> GetAccountByRole(int roleId) {
        ObservableList<Account> list = FXCollections.observableArrayList();
        String sql = "SELECT accounts.*, roles.name as roleName "
                + "FROM accounts JOIN roles ON accounts.roleId = roles.id "
                + "WHERE accounts.roleId = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, roleId);
            rs = preparedStatement.executeQuery();

            int i = 1;
            while (rs.next()) {
                Account acc = new Account();
                acc.setIndex(i);
                acc.setId(rs.getInt("id"));
                acc.setUID(rs.getString("UID"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setFull_name(rs.getString("full_name"));
                acc.setGender(rs.getInt("gender"));
                acc.setEmail(rs.getString("email"));
                acc.setDob(rs.getString("dob"));
                acc.setMobile(rs.getString("mobile"));
                acc.setStatus(rs.getInt("status"));
                acc.setRoleId(rs.getInt("roleId"));
                acc.setRoleName(rs.getString("roleName"));
                acc.setCreatedAt(rs.getString("createdAt"));
                acc.setUpdatedAt(rs.getString("updatedAt"));
                list.add(acc);
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

    public static ObservableList<Account> SearchByUID(String UID) {
        ObservableList<Account> list = FXCollections.observableArrayList();
        String sql = "SELECT accounts.*, roles.name as roleName FROM accounts JOIN roles ON accounts.roleId = roles.id WHERE accounts.UID like ? AND accounts.status = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + UID + "%");
            preparedStatement.setInt(2, 1);
            rs = preparedStatement.executeQuery();

            int i = 1;
            while (rs.next()) {
                Account acc = new Account();
                acc.setIndex(i);
                acc.setId(rs.getInt("id"));
                acc.setUID(rs.getString("UID"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setFull_name(rs.getString("full_name"));
                acc.setGender(rs.getInt("gender"));
                acc.setEmail(rs.getString("email"));
                acc.setDob(rs.getString("dob"));
                acc.setMobile(rs.getString("mobile"));
                acc.setStatus(rs.getInt("status"));
                acc.setRoleId(rs.getInt("roleId"));
                acc.setRoleName(rs.getString("roleName"));
                acc.setCreatedAt(rs.getString("createdAt"));
                acc.setUpdatedAt(rs.getString("updatedAt"));
                list.add(acc);
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
    
    public static boolean SignUp(Account acc) {
        String insertOne = "INSERT INTO accounts (UID, username, avatar, password, full_name, gender, email, mobile, status, roleId, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(insertOne);

            preparedStatement.setString(1, acc.getUID());
            preparedStatement.setString(2, acc.getUsername());
            preparedStatement.setString(3, acc.getAvatar());
            preparedStatement.setString(4, DigestUtils.md5Hex(acc.getPassword()));
            preparedStatement.setString(5, acc.getFull_name());
            preparedStatement.setInt(6, acc.getGender());
            preparedStatement.setInt(7, acc.getGender());
            preparedStatement.setString(8, acc.getMobile());
            preparedStatement.setInt(9, acc.getStatus());
            preparedStatement.setInt(10, acc.getRoleId());
            preparedStatement.setDate(11, preDate);
            preparedStatement.setDate(12, preDate);

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

    public static boolean Add(Account acc) {
        String insertOne = "INSERT INTO accounts (UID, username, avatar, password, full_name, gender, email, dob, mobile, status, roleId, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long milis = System.currentTimeMillis();
        Date preDate = new Date(milis);
        Date formatDob = convertStringToDate(acc.getDob());
        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(insertOne);

            preparedStatement.setString(1, acc.getUID());
            preparedStatement.setString(2, acc.getUsername());
            preparedStatement.setString(3, acc.getAvatar());
            preparedStatement.setString(4, DigestUtils.md5Hex(acc.getPassword()));
            preparedStatement.setString(5, acc.getFull_name());
            preparedStatement.setInt(6, acc.getGender());
            preparedStatement.setString(7, acc.getEmail());
            preparedStatement.setDate(8, formatDob);
            preparedStatement.setString(9, acc.getMobile());
            preparedStatement.setInt(10, acc.getStatus());
            preparedStatement.setInt(11, acc.getRoleId());
            preparedStatement.setDate(12, preDate);
            preparedStatement.setDate(13, preDate);

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

    public static boolean Update(Account acc, Account existingAcc) {
        String query = "UPDATE accounts SET username = ?, password = ?, full_name = ?, gender = ?, email = ?, dob = ?, mobile = ?, roleId = ?, updatedAt = ?  WHERE UID = ?";

        long milis = System.currentTimeMillis();
        java.sql.Date preDate = new java.sql.Date(milis);
        Date formatDob = convertStringToDate(acc.getDob());

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, acc.getUsername());
            if (acc.getPassword() != null && !acc.getPassword().isEmpty()) {
                preparedStatement.setString(2, DigestUtils.md5Hex(acc.getPassword()));
            } else {
                preparedStatement.setString(2, existingAcc.getPassword());
            }

            preparedStatement.setString(3, acc.getFull_name());
            preparedStatement.setInt(4, acc.getGender());
            preparedStatement.setString(5, acc.getEmail());
            preparedStatement.setDate(6, formatDob);
            preparedStatement.setString(7, acc.getMobile());
            preparedStatement.setInt(8, acc.getRoleId());
            preparedStatement.setDate(9, preDate);
            preparedStatement.setString(10, acc.getUID());

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

    public static boolean Delete(String UID) {
        String query = "UPDATE accounts SET status = ? WHERE UID = ?";

        try {
            connection = JDBCConnect.getJDBCConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, UID);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            JDBCConnect.closePreparedStatement(preparedStatement);
            JDBCConnect.closeConnection(connection);
        }

        return false;
    }
}
