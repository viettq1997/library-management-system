package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Models.*;
import com.project2group4.librarymanagement.Entites.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.collections.*;

public class ManagementAuthorsController implements Initializable {

    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSignname;
    @FXML
    private DatePicker txtDob;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtId;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnReset;
    @FXML
    private TextField txtUpdatedAt;
    @FXML
    private TextField txtCreatedAt;
    @FXML
    private Label errorName;
    @FXML
    private Label errorSignname;
    @FXML
    private Label errorDob;
    @FXML
    private TableView<Author> table;
    @FXML
    private TableColumn<Author, String> colIndex;
    @FXML
    private TableColumn<Author, String> colId;
    @FXML
    private TableColumn<Author, String> colName;
    @FXML
    private TableColumn<Author, String> colSignName;
    @FXML
    private TableColumn<Author, String> colDob;
    @FXML
    private TableColumn<Author, String> colCreatedAt;
    @FXML
    private TableColumn<Author, String> colUpdatedAt;
    @FXML
    private Button btnRefesh;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
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

        initClock();
        RefreshData();
    }

    int myIndex;
    int id;

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

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss\ndd/MM/yyyy");
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void table(ObservableList<Author> authors) {
        table.setItems(authors);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asString());
        colId.setCellValueFactory(f -> f.getValue().idProperty().asString());
        colName.setCellValueFactory(f -> f.getValue().nameProperty());
        colSignName.setCellValueFactory(f -> f.getValue().signNameProperty());
        colDob.setCellValueFactory(f -> f.getValue().dobProperty());
        colCreatedAt.setCellValueFactory(f -> f.getValue().createdAtProperty());
        colUpdatedAt.setCellValueFactory(f -> f.getValue().updatedAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Author> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    String[] date = table.getItems().get(myIndex).getDob().split("-");
                    LocalDate dob = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
                    txtDob.setValue(dob);

                    txtId.setText(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtSignname.setText(table.getItems().get(myIndex).getSign_name());
                    txtCreatedAt.setText(table.getItems().get(myIndex).getCreatedAt());
                    txtUpdatedAt.setText(table.getItems().get(myIndex).getUpdatedAt());

                    CheckId();
                    Validated();
                }
            });
            return myRow;
        });
    }

    @FXML
    public void initData() {
        ObservableList<Author> authors = AuthorEntity.GetAll();

        table(authors);
    }

    @FXML
    public void Search() {
        String search = txtSearch.getText();
        ObservableList<Author> authors = AuthorEntity.SearchByName(search);

        table(authors);
    }

    @FXML
    public void BtnSaveClick() {
        String id = txtId.getText();
        String name = txtName.getText();
        String signName = txtSignname.getText();
        String dob = txtDob.getValue().toString();

        Author author = new Author();
        author.setName(name);
        author.setSign_name(signName);
        author.setDob(dob);

        Alert alert = new Alert(Alert.AlertType.NONE);
        if (id.isEmpty()) {

            if (AuthorEntity.GetAuthorWithSignName(signName) == null) {

                if (AuthorEntity.Add(author)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Authors Manager");
                    alert.setContentText("Added Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Authors Manager");
                    alert.setContentText("Added Fail!");
                    alert.showAndWait();
                }
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Authors Manager");
                alert.setContentText("This Author exists!");
                alert.showAndWait();
            }

        } else {
            author.setId(Integer.parseInt(id));

            if (AuthorEntity.GetAuthorWithId(author.getId()).equals(author.getName())) {
                if (AuthorEntity.Update(author)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Authors Manager");
                    alert.setContentText("Updated Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Authors Manager");
                    alert.setContentText("Updated Fail!");
                    alert.showAndWait();
                }
            } else {
                if (AuthorEntity.GetAuthorWithSignName(signName) == null) {
                    if (AuthorEntity.Update(author)) {
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Test Connection");
                        alert.setHeaderText("Authors Manager");
                        alert.setContentText("Updated Successfully!");
                        alert.showAndWait();
                    } else {
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Test Connection");
                        alert.setHeaderText("Authors Manager");
                        alert.setContentText("Updated Fail!");
                        alert.showAndWait();
                    }
                } else {

                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Authors Manager");
                    alert.setContentText("This Author exists!");
                    alert.showAndWait();
                }
            }

        }
        RefreshData();
    }

    @FXML
    public void BtnDeleteClick() {
        int id = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(Alert.AlertType.NONE);
        ObservableList<Book> existsBook = BookEntity.GetBookByAuthorId(id);
        ObservableList<Book> noBook = FXCollections.observableArrayList();

        if (existsBook.equals(noBook) || existsBook == null) {
            if (AuthorEntity.Delete(id)) {

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Authors Manager");
                alert.setContentText("Deleted Successfully!");
                alert.showAndWait();

            } else {

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Authors Manager");
                alert.setContentText("Deleted Fail!");
                alert.showAndWait();

            }
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Authors Manager");
            alert.setContentText("This author have books! Delete books of author");
            alert.showAndWait();
        }

        RefreshData();
    }

    @FXML
    public void RefreshData() {
        ResetField();
        initData();
        CheckId();
        btnSave.setDisable(true);
    }

    @FXML
    public void ResetField() {
        txtCreatedAt.setText("");
        txtDob.setValue(null);
        txtId.setText("");
        txtName.setText("");
        txtSearch.setText("");
        txtSignname.setText("");
        txtUpdatedAt.setText("");

        errorDob.setVisible(false);
        errorName.setVisible(false);
        errorSignname.setVisible(false);
    }

    public void CheckId() {
        String id = txtId.getText();

        if (id.isEmpty()) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }
    }

    public void Validated() {
        boolean flag = false;

        String name = txtName.getText();
        String signName = txtSignname.getText();
        LocalDate dob = txtDob.getValue();
        LocalDate curDate = LocalDate.now();

        if (name.isEmpty() || name.length() > 64) {
            errorName.setVisible(true);

            flag = true;
        } else {
            errorName.setVisible(false);
        }

        if (signName.isEmpty() || signName.length() > 64) {
            errorSignname.setVisible(true);

            flag = true;
        } else {
            errorSignname.setVisible(false);
        }

        if (dob == null) {
            errorDob.setVisible(true);

            flag = true;
        } else {
            int age = Period.between(dob, curDate).getYears();
            if (age < 18) {
                errorDob.setVisible(true);

                flag = true;
            }
            errorDob.setVisible(false);
        }

        btnSave.setDisable(flag);
    }
}
