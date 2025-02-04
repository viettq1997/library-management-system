package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Entites.AccountEntity;
import com.project2group4.librarymanagement.Models.Account;
import com.project2group4.librarymanagement.Models.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

public class SignInController implements Initializable {

    User user = User.getInstace();

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passPassword;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button hide;
    @FXML
    private Button show;
    @FXML
    private Label labelClock;
    @FXML
    private Label errorPassword;
    @FXML
    private Button btnSignUp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initClock();
        Validated();
        errorPassword.setVisible(false);
        hide.setVisible(false);
    }

    @FXML
    private void Login() throws IOException {
        String inpUsername = txtUsername.getText();
        String inpPassword = "";
        String showPassword = txtPassword.getText();
        String hidePassword = passPassword.getText();

        if (hidePassword.length() > showPassword.length()) {
            inpPassword = hidePassword;
        } else {
            inpPassword = showPassword;
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        Account acc = AccountEntity.GetAccountByUsername(inpUsername);
        if (acc != null) {
            String password = acc.getPassword();
            String role = acc.getRoleName();

            if (password.equals(DigestUtils.md5Hex(inpPassword))) {

                user.setUserSession(inpUsername);

                if (role.equals("Admin")) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
                    try {
                        alert.setDialogPane(loader.load());
                        MessageController mc = loader.getController();
                        mc.setMessage("Login Successfully!\nWelcome to Library " + acc.getUsername());
                    } catch (Exception e) {
                    }
                    alert.showAndWait();

                    switchToAdminDashboard();
                } else if (role.equals("Reader")) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
                    try {
                        alert.setDialogPane(loader.load());
                        MessageController mc = loader.getController();
                        mc.setMessage("Login Successfully!\nWelcome to Library " + acc.getFull_name());
                    } catch (Exception e) {
                    }
                    alert.showAndWait();

                    switchToCustomerDashboard();
                }
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
                try {
                    alert.setDialogPane(loader.load());
                    MessageController mc = loader.getController();
                    mc.setMessage("Wrong Username or Password!");
                } catch (Exception e) {
                }

                alert.showAndWait();
            }
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
            try {
                alert.setDialogPane(loader.load());
                MessageController mc = loader.getController();
                mc.setMessage("Wrong Username or Password!");
            } catch (Exception e) {
            }

            alert.showAndWait();
        }

    }

    private void switchToAdminDashboard() throws IOException {
        App.setRoot("AdminDashboard");
    }

    private void switchToCustomerDashboard() throws IOException {
        App.setRoot("CustomerDashboard");
    }

    @FXML
    private void switchToSignUp() throws IOException {
        App.setRoot("SignUp");
    }

    @FXML
    private void HidePassword() {
        String showPassword = txtPassword.getText();
        passPassword.setText(showPassword);
        txtPassword.setVisible(false);
        passPassword.setVisible(true);
        show.setVisible(true);
        hide.setVisible(false);
    }

    @FXML
    private void ShowPassword() {
        String hidePassword = passPassword.getText();
        txtPassword.setText(hidePassword);
        passPassword.setVisible(false);
        txtPassword.setVisible(true);
        hide.setVisible(true);
        show.setVisible(false);
    }

    @FXML
    private void Validated() {
        boolean flag = false;
        String username = txtUsername.getText();
        String hidePassword = passPassword.getText();
        String showPassword = txtPassword.getText();

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$";

        if (username.isEmpty()) {
            flag = true;
        } else {
        }

        if ((hidePassword.isEmpty() && showPassword.isEmpty())) {
            errorPassword.setVisible(true);

            flag = true;
        } else {
            if (hidePassword.matches(PASSWORD_PATTERN) && !hidePassword.isEmpty() || showPassword.matches(PASSWORD_PATTERN) && !showPassword.isEmpty()) {
                errorPassword.setVisible(false);
            } else {
                errorPassword.setVisible(true);

                flag = true;
            }
        }

        btnSubmit.setDisable(flag);
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss\ndd/MM/yyyy");
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}
