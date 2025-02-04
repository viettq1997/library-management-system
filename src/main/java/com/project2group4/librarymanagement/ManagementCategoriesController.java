package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Models.*;
import com.project2group4.librarymanagement.Entites.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ManagementCategoriesController implements Initializable {

    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @FXML
    private Label labelClock;
    @FXML
    private Circle avatar;
    @FXML
    private Label sessionUsername;
    @FXML
    private Label errorName;
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
    private TextField txtName;
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
    private TableView<Category> table;
    @FXML
    private TableColumn<Category, Integer> colIndex;
    @FXML
    private TableColumn<Category, String> colId;
    @FXML
    private TableColumn<Category, String> colName;
    @FXML
    private TableColumn<Category, String> colCreatedAt;
    @FXML
    private TableColumn<Category, String> colUpdatedAt;
    @FXML
    private Button btnRefesh;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;

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

    public void table() {
        ObservableList<Category> categories = CategoryEntity.GetAll();

        table.setItems(categories);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asObject());
        colId.setCellValueFactory(f -> f.getValue().idProperty().asString());
        colName.setCellValueFactory(f -> f.getValue().nameProperty());
        colCreatedAt.setCellValueFactory(f -> f.getValue().createdAtProperty());
        colUpdatedAt.setCellValueFactory(f -> f.getValue().updatedAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Category> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    txtId.setText(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtCreatedAt.setText(table.getItems().get(myIndex).getCreatedAt());
                    txtUpdatedAt.setText(table.getItems().get(myIndex).getUpdatedAt());

                    CheckId();
                }
            });
            return myRow;
        });
    }

    public void BtnSearchClick() {
        String search = txtSearch.getText();
        ObservableList<Category> categories = CategoryEntity.Search(search);

        table.setItems(categories);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asObject());
        colId.setCellValueFactory(f -> f.getValue().idProperty().asString());
        colName.setCellValueFactory(f -> f.getValue().nameProperty());
        colCreatedAt.setCellValueFactory(f -> f.getValue().createdAtProperty());
        colUpdatedAt.setCellValueFactory(f -> f.getValue().updatedAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Category> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();
                    txtId.setText(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtName.setText(table.getItems().get(myIndex).getName());
                    txtCreatedAt.setText(table.getItems().get(myIndex).getCreatedAt());
                    txtUpdatedAt.setText(table.getItems().get(myIndex).getUpdatedAt());
                }
            });
            return myRow;
        });
    }

    public void BtnSaveClick() {

        FomartInputName();

        String inpId = txtId.getText();
        String inpName = txtName.getText();

        Category category = new Category();
        category.setName(inpName);

        Alert alert = new Alert(Alert.AlertType.NONE);
        if (inpId.isEmpty()) {

            if (CategoryEntity.GetCategoryByName(inpName) == null) {

                if (CategoryEntity.Add(category)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Categories Manager");
                    alert.setContentText("Added Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Categories Manager");
                    alert.setContentText("Added Fail!");
                    alert.showAndWait();
                }
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Categories Manager");
                alert.setContentText("This Category exists!");
                alert.showAndWait();
            }

        } else {
            category.setId(Integer.parseInt(inpId));
            if (!CategoryEntity.GetCategoryById(category.getId()).getName().equals(category.getName())) {
                if (CategoryEntity.GetCategoryByName(category.getName()).equals(null)) {
                    if (CategoryEntity.Update(category)) {

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Test Connection");
                        alert.setHeaderText("Categories Manager");
                        alert.setContentText("Updated Successfully!");
                        alert.showAndWait();

                    } else {

                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setTitle("Test Connection");
                        alert.setHeaderText("Categories Manager");
                        alert.setContentText("Updated Fail!");
                        alert.showAndWait();

                    }

                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Categories Manager");
                    alert.setContentText("This category exists!");
                    alert.showAndWait();
                }
            } else {
                if (CategoryEntity.Update(category)) {

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Categories Manager");
                    alert.setContentText("Updated Successfully!");
                    alert.showAndWait();

                } else {

                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Categories Manager");
                    alert.setContentText("Updated Fail!");
                    alert.showAndWait();

                }

            }
        }

        RefreshData();
    }

    public void BtnDeleteClick() {

        int id = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(Alert.AlertType.NONE);
        ObservableList<Book> existsBook = BookEntity.GetBookByCategoryId(id);
        ObservableList<Book> noBook = FXCollections.observableArrayList();

        if (existsBook.equals(noBook) || existsBook.equals(null)) {
            if (CategoryEntity.Delete(id)) {

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Categories Manager");
                alert.setContentText("Deleted Successfully!");
                alert.showAndWait();

            } else {

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Test Connection");
                alert.setHeaderText("Categories Manager");
                alert.setContentText("Deleted Fail!");
                alert.showAndWait();

            }
        } else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Categories Manager");
            alert.setContentText("This category have books! Delete books of category");
            alert.showAndWait();
        }

        RefreshData();
    }

    public void CheckInputName() {
        if (txtName.getText().isEmpty()) {
            errorName.setVisible(true);
            btnSave.setDisable(true);
        } else {
            errorName.setVisible(false);
            btnSave.setDisable(false);
        }
        if (txtName.getText().length() > 64) {
            btnSave.setDisable(true);
            Locale.setDefault(new Locale("en", "English"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("STOP");
            alert.setHeaderText("Name must be less than 64 character");
            alert.showAndWait();
        }
    }

    public void FomartInputName() {
        String inpName = txtName.getText();
        String newInpName = "";
        inpName = inpName.trim().replaceAll("//s//s+", " ");
        String[] array = inpName.split(" ");

        for (String name : array) {
            newInpName += name.toUpperCase().charAt(0);
            newInpName += name.substring(1);
            newInpName += " ";
        }

        newInpName = newInpName.trim();

        txtName.setText(newInpName);
    }

    public void RefreshData() {
        ResetField();
        table();
    }

    public void ResetField() {
        txtId.setText("");
        txtName.setText("");
        txtCreatedAt.setText("");
        txtUpdatedAt.setText("");
        txtSearch.setText("");
        errorName.setVisible(false);

        CheckId();
    }

    public void CheckId() {
        String id = txtId.getText();

        btnDelete.setDisable(id.isEmpty());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        User user = User.getInstace();
        String sessionUser = user.getUserName();
        try {

            if (sessionUser.isEmpty()) {
                SignOut();
            } else {
                sessionUsername.setText(sessionUser);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        initClock();
        errorName.setVisible(false);
        btnSave.setDisable(true);
        CheckId();
        table();
    }
}
