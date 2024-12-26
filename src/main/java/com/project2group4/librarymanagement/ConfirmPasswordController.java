package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Entites.AccountEntity;
import com.project2group4.librarymanagement.Models.Account;
import com.project2group4.librarymanagement.Models.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import org.apache.commons.codec.digest.DigestUtils;

public class ConfirmPasswordController implements Initializable {

    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public boolean Confirm() {
        User user = User.getInstace();
        String username = user.getUserName();
        String password = txtPassword.getText();
        String md5Password = DigestUtils.md5Hex(password);

        Alert alert = new Alert(Alert.AlertType.NONE);
        Account acc = AccountEntity.GetAccountByUsername(username);

        if (md5Password.equals(acc.getPassword())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
            try {
                alert.setDialogPane(loader.load());
                MessageController mc = loader.getController();
                mc.setMessage("Confirm Successfully!");
            } catch (Exception e) {
            }
            alert.showAndWait();

            return true;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
        try {
            alert.setDialogPane(loader.load());
            MessageController mc = loader.getController();
            mc.setMessage("Wrong Password! Let's confirm old password again!");
        } catch (Exception e) {
        }
        alert.showAndWait();

        return false;
    }
}
