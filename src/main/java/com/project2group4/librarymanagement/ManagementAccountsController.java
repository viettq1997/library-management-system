package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Models.*;
import com.project2group4.librarymanagement.Entites.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.format.*;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;

public class ManagementAccountsController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private Label labelClock;
    @FXML
    private Label sessionUsername;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnManageBooks;
    @FXML
    private Button btnManageAuthors;
    @FXML
    private Button btnManageCategories;
    @FXML
    private Button btnManagePublishing;
    @FXML
    private Button btnManageAccounts;
    @FXML
    private Button btnManageBorrowing;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TextField txtUID;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label errorUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label errorPassword;
    @FXML
    private TextField txtFullname;
    @FXML
    private Label errorFullname;
    @FXML
    private ComboBox<Gender> boxGender;
    @FXML
    private Label errorGender;
    @FXML
    private ComboBox<Role> boxRole;
    @FXML
    private Label errorRole;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label errorEmail;
    @FXML
    private DatePicker txtDob;
    @FXML
    private Label errorDob;
    @FXML
    private TextField txtMobile;
    @FXML
    private Label errorMobile;
    @FXML
    private TextField txtCreatedAt;
    @FXML
    private TextField txtUpdatedAt;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnReset;
    @FXML
    private TableView<Account> table;
    @FXML
    private TableColumn<Account, String> colIndex;
    @FXML
    private TableColumn<Account, String> colUID;
    @FXML
    private TableColumn<Account, String> colUsername;
    @FXML
    private TableColumn<Account, String> colPassword;
    @FXML
    private TableColumn<Account, String> colFullname;
    @FXML
    private TableColumn<Account, String> colGender;
    @FXML
    private TableColumn<Account, String> colRole;
    @FXML
    private TableColumn<Account, String> colEmail;
    @FXML
    private TableColumn<Account, String> colDob;
    @FXML
    private TableColumn<Account, String> colMobile;
    @FXML
    private TableColumn<Account, String> colCreatedAt;
    @FXML
    private TableColumn<Account, String> colUpdatedAt;
    @FXML
    private Button btnRefesh;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;

    int myIndex;
    int id;
    Image av;

    @FXML
    private void switchToAdminDashboard() throws IOException {
        App.setRoot("AdminDashboard");
    }

    @FXML
    private void switchToManagementAuthors() throws IOException {
        App.setRoot("ManagementAuthors");
    }

    @FXML
    private void switchToManagementBooks() throws IOException {
        App.setRoot("ManagementBooks");
    }

    @FXML
    private void switchToManagementCategories() throws IOException {
        App.setRoot("ManagementCategories");
    }

    @FXML
    private void switchToManagementPublishing() throws IOException {
        App.setRoot("ManagementPublishing");
    }

    @FXML
    private void switchToManagementAccounts() throws IOException {
        App.setRoot("ManagementAccounts");
    }

    @FXML
    private void switchToManagementBorrowing() throws IOException {
        App.setRoot("ManagementBorrow");
    }

    @FXML
    private void SignOut() throws Exception {
        App.setRoot("SignIn");
    }

    public void table() {
        ObservableList<Account> accounts = AccountEntity.GetAll();

        table.setItems(accounts);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asString());
        colUID.setCellValueFactory(f -> f.getValue().UIDProperty());
        colUsername.setCellValueFactory(f -> f.getValue().usernameProperty());
        colPassword.setCellValueFactory(f -> f.getValue().passwordProperty());
        colFullname.setCellValueFactory(f -> f.getValue().full_nameProperty());
        colGender.setCellValueFactory((f) -> f.getValue().genderProperty().asString());
        colRole.setCellValueFactory(f -> f.getValue().roleNameProperty());
        colEmail.setCellValueFactory(f -> f.getValue().emailProperty());
        colDob.setCellValueFactory(f -> f.getValue().dobProperty());
        colMobile.setCellValueFactory(f -> f.getValue().mobileProperty());
        colCreatedAt.setCellValueFactory(f -> f.getValue().createdAtProperty());
        colUpdatedAt.setCellValueFactory(f -> f.getValue().updatedAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Account> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    int indexGender = table.getItems().get(myIndex).getGender();

                    boxGender.setValue(boxGender.getItems().get(indexGender - 1));

                    String roleName = table.getItems().get(myIndex).getRoleName();
                    Role role = RoleEntity.GetOneByName(roleName);

                    boxRole.setValue(role);

                    if (table.getItems().get(myIndex).getDob() != null) {
                        String[] date = table.getItems().get(myIndex).getDob().split("-");
                        LocalDate dob = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                        txtDob.setValue(dob);
                    }

                    txtUID.setText(table.getItems().get(myIndex).getUID());
                    txtUsername.setDisable(true);
                    txtUsername.setText(table.getItems().get(myIndex).getUsername());
                    txtPassword.setText("");
                    txtFullname.setText(table.getItems().get(myIndex).getFull_name());
                    txtEmail.setText(table.getItems().get(myIndex).getEmail());
                    txtMobile.setText(table.getItems().get(myIndex).getMobile());
                    txtCreatedAt.setText(table.getItems().get(myIndex).getCreatedAt());
                    txtUpdatedAt.setText(table.getItems().get(myIndex).getUpdatedAt());

                    CheckUID();
                    CheckRole();
                    errorPassword.setVisible(false);
                    btnSave.setDisable(false);
                }
            });
            return myRow;
        });
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss\ndd/MM/yyyy");
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void BtnSaveClick() {
        FormatFullName();

        Account a = new Account();
        String UID = txtUID.getText();
        String password = DigestUtils.md5Hex(txtPassword.getText());
        String userName = txtUsername.getText();
        String fullName = txtFullname.getText();
        Gender gender = boxGender.getValue();
        Role role = boxRole.getValue();
        String email = txtEmail.getText();
        String date = txtDob.getValue().toString();
        String mobile = txtMobile.getText();
        String imgName = UID + ".jpg";

        Alert alert = new Alert(Alert.AlertType.NONE);

        if (UID.isEmpty()) {
            if (AccountEntity.GetAccountByUsername(userName) == null) {

                UID = UUID.randomUUID().toString();
                a.setUID(UID);
                a.setAvatar(imgName);
                a.setPassword(password);
                a.setUsername(userName);
                a.setFull_name(fullName);
                a.setGender(gender.getId());
                a.setEmail(email);
                a.setDob(date);
                a.setMobile(mobile);
                a.setStatus(1);
                a.setAvatar(UID + ".jpg");
                a.setRoleId(role.getId());

                if (AccountEntity.Add(a)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Accounts Manager");
                    alert.setContentText("Added Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Accounts Manager");
                    alert.setContentText("Added Fail!");
                    alert.showAndWait();
                }

            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Accounts Manager");
                alert.setContentText("This username exists!");
                alert.showAndWait();
            }

        } else {
            a.setUID(UID);
            a.setUsername(userName);
            a.setPassword(password);
            a.setFull_name(fullName);
            a.setGender(gender.getId());
            a.setEmail(email);
            a.setDob(date);
            a.setMobile(mobile);
            a.setRoleId(role.getId());
            Account account = AccountEntity.GetAccountByUID(UID);
            if (AccountEntity.Update(a, account)) {
                if (a.getUID().equals(UID)) {
                    sessionUsername.setText(userName);
                }
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Accounts Manager");
                alert.setContentText("Updated Successfully!");
                alert.showAndWait();
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Accounts Manager");
                alert.setContentText("Updated Fail!");
                alert.showAndWait();
            }
        }
        RefreshData();
    }

    @FXML
    public void Search() {
        String search = txtSearch.getText();
        ObservableList<Account> accounts = AccountEntity.SearchByUID(search);

        table.setItems(accounts);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asString());
        colUID.setCellValueFactory(f -> f.getValue().UIDProperty());
        colUsername.setCellValueFactory(f -> f.getValue().usernameProperty());
        colPassword.setCellValueFactory(f -> f.getValue().passwordProperty());
        colFullname.setCellValueFactory(f -> f.getValue().full_nameProperty());
        colGender.setCellValueFactory((f) -> f.getValue().genderProperty().asString());
        colRole.setCellValueFactory(f -> f.getValue().roleNameProperty());
        colEmail.setCellValueFactory(f -> f.getValue().emailProperty());
        colDob.setCellValueFactory(f -> f.getValue().dobProperty());
        colMobile.setCellValueFactory(f -> f.getValue().mobileProperty());
        colCreatedAt.setCellValueFactory(f -> f.getValue().createdAtProperty());
        colUpdatedAt.setCellValueFactory(f -> f.getValue().updatedAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Account> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    int indexGender = table.getItems().get(myIndex).getGender();
                    ObservableList<Gender> gender = FXCollections.observableArrayList();

                    gender.addAll(new Gender(1, "Male"), new Gender(2, "Female"), new Gender(3, "Other"));

                    boxGender.setValue(boxGender.getItems().get(indexGender - 1));

                    String roleName = table.getItems().get(myIndex).getRoleName();
                    Role role = RoleEntity.GetOneByName(roleName);

                    boxRole.setValue(role);

                    String[] date = table.getItems().get(myIndex).getDob().split("-");

                    LocalDate dob = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                    txtDob.setValue(dob);

                    txtUID.setText(table.getItems().get(myIndex).getUID());
                    txtUsername.setText(table.getItems().get(myIndex).getUsername());
                    String password = table.getItems().get(myIndex).getPassword();
                    txtPassword.setText(password);
                    txtFullname.setText(table.getItems().get(myIndex).getFull_name());
                    txtEmail.setText(table.getItems().get(myIndex).getEmail());
                    txtMobile.setText(table.getItems().get(myIndex).getMobile());
                    txtCreatedAt.setText(table.getItems().get(myIndex).getCreatedAt());
                    txtUpdatedAt.setText(table.getItems().get(myIndex).getUpdatedAt());

                    CheckUID();
                    CheckRole();
                }
            });
            return myRow;
        });
    }

    @FXML
    public void BtnDeleteClick() {
        String UID = txtUID.getText();

        Alert alert = new Alert(Alert.AlertType.NONE);

        if (AccountEntity.Delete(UID)) {

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Accounts Manager");
            alert.setContentText("Unactived Successfully!");
            alert.showAndWait();

        } else {

            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Accounts Manager");
            alert.setContentText("Unactived Fail!");
            alert.showAndWait();

        }

        RefreshData();
    }

    @FXML
    public void RefreshData() {
        ResetField();
        table();
    }

    @FXML
    public void ResetField() {
        txtUID.setText("");
        txtUsername.setDisable(false);
        txtUsername.setText("");
        txtPassword.setText("");
        txtFullname.setText("");
        boxGender.setValue(null);
        boxRole.setValue(null);
        txtEmail.setText("");
        txtDob.setValue(null);
        txtMobile.setText("");
        txtCreatedAt.setText("");
        txtUpdatedAt.setText("");
        txtSearch.setText("");

        errorUsername.setVisible(false);
        errorPassword.setVisible(false);
        errorFullname.setVisible(false);
        errorGender.setVisible(false);
        errorRole.setVisible(false);
        errorEmail.setVisible(false);
        errorDob.setVisible(false);
        errorMobile.setVisible(false);

        CheckUID();
    }

    public void CheckUID() {
        String UID = txtUID.getText();
//      Set button delete disable when UID is empty else unset disable
        btnDelete.setDisable(UID.isEmpty());
    }

    public void CheckRole() {
        Role role = boxRole.getValue();
        btnDelete.setDisable(role.getId() == 1);
    }

    @FXML
    private void Validated(KeyEvent event) {
        Node source = (Node) event.getSource();
        boolean flag = false;
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$";
        String MOBILE_PATTERN = "^\\d{10}$";
        String EMAIL_PATTERN = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String fullname = txtFullname.getText();
        Gender gender = boxGender.getValue();
        Role role = boxRole.getValue();
        String email = txtEmail.getText();
        LocalDate dob = txtDob.getValue();
        String mobile = txtMobile.getText();
        if (username.isEmpty() || username.length() > 64) {
            errorUsername.setVisible(true);
            flag = true;

        } else {
            errorUsername.setVisible(false);
        }
        TextField textField = (TextField) source;
        if (textField == txtPassword && (password.isEmpty() || !password.matches(PASSWORD_PATTERN))) {
            errorPassword.setVisible(true);
            flag = true;
        } else {
            errorPassword.setVisible(false);
        }

        if (fullname.isEmpty() || fullname.length() > 64) {
            errorFullname.setVisible(true);
            flag = true;

        } else {
            errorFullname.setVisible(false);
        }

        if (gender == null) {
            errorGender.setVisible(true);
            flag = true;

        } else {
            errorGender.setVisible(false);
        }

        if (role == null) {
            errorRole.setVisible(true);
            flag = true;

        } else {
            errorRole.setVisible(false);
        }

        if (email.isEmpty() || !email.matches(EMAIL_PATTERN)) {
            errorEmail.setVisible(true);
            flag = true;

        } else {
            errorEmail.setVisible(false);
        }

        if (dob == null) {
            errorDob.setVisible(true);
            flag = true;

        } else {
            errorDob.setVisible(false);
        }

        if (mobile.isEmpty() || !mobile.matches(MOBILE_PATTERN)) {
            errorMobile.setVisible(true);

            flag = true;
        } else {
            errorMobile.setVisible(false);
        }

        btnSave.setDisable(flag);
    }

    private void FormatFullName() {
        String inpFullname = txtFullname.getText();
        String newFullname = "";
        inpFullname = inpFullname.trim().replaceAll("//s//s+", " ");
        String[] array = inpFullname.split(" ");

        for (String name : array) {
            newFullname += name.toUpperCase().charAt(0);
            newFullname += name.substring(1);
            newFullname += " ";
        }

        newFullname = newFullname.trim();

        txtFullname.setText(newFullname);
    }

    private void InitItemsRoleBox() {

//      set value for combo box is role
        ObservableList<Role> role = RoleEntity.GetAll();

        if (role == null) {
            boxRole.setPromptText("No roles in this box!");
        } else if (role.isEmpty()) {
            boxRole.setPromptText("No roles in this box!");
        } else {
            boxRole.setItems(role);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        User user = User.getInstace();
        String sessionUser = user.getUserName();
        try {

            if (sessionUser.equals("") || sessionUser.equals(null)) {
                SignOut();
            } else {
                sessionUsername.setText(sessionUser);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//      run real time and replace a time String for labelClock
        initClock();
//      get all data from database 
        table();
//      set button disable
        btnSave.setDisable(true);
        CheckUID();

//      set value for combo box is gender
        ObservableList<Gender> gender = FXCollections.observableArrayList();

        boxGender.setItems(FXCollections.observableArrayList(
                new Gender(1, "Male"),
                new Gender(2, "Female"),
                new Gender(3, "Other"))
        );
        InitItemsRoleBox();
        ResetField();
    }
}
