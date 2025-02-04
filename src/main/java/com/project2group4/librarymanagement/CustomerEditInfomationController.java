package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Entites.AccountEntity;
import com.project2group4.librarymanagement.Models.Account;
import com.project2group4.librarymanagement.Models.Gender;
import com.project2group4.librarymanagement.Models.User;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class CustomerEditInfomationController implements Initializable {

    User user = User.getInstace();
    @FXML
    private Label labelClock;
    @FXML
    private Label sessionUsername;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnBorrowing;
    @FXML
    private Button btnSignout;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label errorUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtFullname;
    @FXML
    private Label errorFullname;
    @FXML
    private ComboBox<Gender> boxGender;
    @FXML
    private Label errorGender;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label errorEmail;
    @FXML
    private DatePicker datePickerDob;
    @FXML
    private Label errorDob;
    @FXML
    private TextField txtMobile;
    @FXML
    private Label errorMobile;
    @FXML
    private Label errorPassword;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSave;

    private boolean isPasswordInFocus = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Account acc = AccountEntity.GetAccountByUsername(user.getUserName());
        String sessionUser = acc.getFull_name();
        try {

            if (sessionUser.equals("") || sessionUser.equals(null)) {
                switchToSignIn();
            } else {
                sessionUsername.setText(sessionUser);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        initClock();
        initDataBoxGender();
        initData();
        btnSave.setDisable(true);
    }

    @FXML
    private void switchToCustomerDashboard() throws Exception {
        App.setRoot("CustomerDashboard");
    }

    @FXML
    private void switchToCustomerInfomation() throws Exception {
        App.setRoot("CustomerInfomation");
    }

    @FXML
    private void switchToCustomerBorrowing() throws Exception {
        App.setRoot("CustomerBorrow");
    }

    @FXML
    private void switchToSignIn() throws Exception {
        App.setRoot("SignIn");
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss\ndd/MM/yyyy");
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void initData() {
        Account acc = AccountEntity.GetAccountByUsername(user.getUserName());
        txtUsername.setText(acc.getUsername());
        txtFullname.setText(acc.getFull_name());

        boxGender.setValue(boxGender.getItems().get(acc.getGender() - 1));

        txtEmail.setText(acc.getEmail());

        if (acc.getDob() != null) {
            Date date = Date.valueOf(acc.getDob());
            datePickerDob.setValue(date.toLocalDate());
        }

        txtMobile.setText(acc.getMobile());
    }

    private void initDataBoxGender() {
        boxGender.setItems(FXCollections.observableArrayList(
                new Gender(1, "Male"),
                new Gender(2, "Female"),
                new Gender(3, "Other"))
        );
    }

    @FXML
    private void Validated() {
        boolean flag = false;

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$";
        String MOBILE_PATTERN = "^\\d{10}$";
        String EMAIL_PATTERN = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String fullname = txtFullname.getText();
        String email = txtEmail.getText();
        LocalDate dob = datePickerDob.getValue();
        String mobile = txtMobile.getText();

        errorUsername.setVisible(username.isEmpty());

        if (isPasswordInFocus && (!password.isEmpty() && !password.matches(PASSWORD_PATTERN))) {
            errorPassword.setVisible(true);
            flag = true;
        } else {
            errorPassword.setVisible(false);
        }

        if (fullname.isEmpty()) {
            errorFullname.setVisible(true);

            flag = true;
        } else {
            errorFullname.setVisible(false);
        }

        errorGender.setVisible(false);

        if (email.matches(EMAIL_PATTERN)) {
            errorEmail.setVisible(false);
        } else {
            errorEmail.setVisible(true);

            flag = true;
        }

        if (dob == null) {
            errorDob.setVisible(true);

            flag = true;
        } else {
            errorDob.setVisible(false);
        }

        if (!mobile.matches(MOBILE_PATTERN)) {
            errorMobile.setVisible(true);

            flag = true;
        } else {
            errorMobile.setVisible(false);
        }

        btnSave.setDisable(flag);
    }

    @FXML
    private void checkFocusPassword() {
        isPasswordInFocus = true;
    }

    @FXML
    private void BtnSaveClick() {
        Account existingAcc = AccountEntity.GetAccountByUsername(user.getUserName());

        Account acc = new Account();
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String fullname = txtFullname.getText();
        int gender = boxGender.getValue().getId();
        String email = txtEmail.getText();
        String dob = datePickerDob.getValue().toString();
        String mobile = txtMobile.getText();

        acc.setUsername(username);
        acc.setPassword(password);
        acc.setFull_name(fullname);
        acc.setGender(gender);
        acc.setEmail(email);
        acc.setDob(dob);
        acc.setMobile(mobile);
        acc.setUID(existingAcc.getUID());
        acc.setRoleId(existingAcc.getRoleId());

        Alert alert = new Alert(Alert.AlertType.NONE);
        if (!user.getUserName().equals(username)) {
            if (AccountEntity.GetAccountByUsername(acc.getUsername()) == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmPassword.fxml"));
                try {
                    alert.setDialogPane(loader.load());
                    alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                    alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, c -> {
                        ConfirmPasswordController confirmPage = loader.getController();
                        if (confirmPage.Confirm()) {
                            try {
                                if (AccountEntity.Update(acc, existingAcc)) {
                                    user.setUserSession(acc.getUsername());
                                    switchToCustomerInfomation();
                                }
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    });
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                alert.showAndWait();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Message.fxml"));
                try {
                    alert.setDialogPane(loader.load());
                    MessageController mc = loader.getController();
                    mc.setMessage("This Username is exists! Let's Signup again!");
                } catch (Exception e) {
                }

                alert.showAndWait();
            }
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ConfirmPassword.fxml"));
            try {
                alert.setDialogPane(loader.load());
                alert.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
                alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, c -> {
                    ConfirmPasswordController confirmPage = loader.getController();
                    if (confirmPage.Confirm()) {
                        try {
                            if (AccountEntity.Update(acc, existingAcc)) {
                                user.setUserSession(acc.getUsername());
                                switchToCustomerInfomation();
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            alert.showAndWait();
        }

    }
}
