package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Entites.BorrowEntity;
import com.project2group4.librarymanagement.Models.Borrow;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.project2group4.librarymanagement.Models.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

/**
 *
 * @author 84563
 */
public class ManagementBorrowingController implements Initializable {

    private Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    @FXML
    private Label labelClock;
    @FXML
    private Label sessionUsername;

    int id, myIndex, manaId;
    @FXML
    private TableView<Borrow> table;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtID, txtSearch;

    @FXML
    private ComboBox txtAccount, txtBook, txtStatus;

    @FXML
    private DatePicker txtBorrowAt, txtRefundAt;

    @FXML
    private Label errorAccount, errorBorrowAt, errorBook, errorRefundAt, errorStatus;

    @FXML
    private TableColumn<Borrow, String> colIndex, colUID, colBook, colBorrow, colRefund, colStatus;

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void CheckId() {
        String id = txtID.getText();
        btnDelete.setDisable(id.isEmpty());
    }

    @FXML
    private void Validated() {
        boolean flag = false;

        LocalDate borrowAt = txtBorrowAt.getValue();
        LocalDate refundAt = txtRefundAt.getValue();
        
        if (txtBook.getValue() == null) {
            errorBook.setVisible(true);
            flag = true;
        } else {
            errorBook.setVisible(false);
        }

        if (txtAccount.getValue() == null) {
            errorAccount.setVisible(true);

            flag = true;
        } else {
            errorAccount.setVisible(false);
        }

        if (txtStatus.getValue()== null) {
            errorStatus.setVisible(true);

            flag = true;
        } else {
            errorStatus.setVisible(false);
        }

        if (borrowAt == null) {
            errorBorrowAt.setVisible(true);

            flag = true;
        } else {
            errorBorrowAt.setVisible(false);
        }

        if (refundAt == null ) {
            errorRefundAt.setVisible(true);

            flag = true;
        } else {
            errorRefundAt.setVisible(false);
        }

        btnSave.setDisable(flag);
    }

    @FXML
    private void BtnSaveClick() {
        Validated();
        try {
            Alert alert = new Alert(Alert.AlertType.NONE);
            if (txtID.getText().isEmpty()) {

                int accountId = BorrowEntity.selectAccountIndex(txtAccount);
                int bookId = BorrowEntity.selectBookIndex(txtBook);
                int statusId = BorrowEntity.selectStatusIndex(txtStatus);
                
                Borrow borrow = new Borrow();
                borrow.setRefundAt(txtRefundAt.getValue().toString());
                borrow.setBorrowAt(txtBorrowAt.getValue().toString());
                borrow.setStatusId(statusId);
                borrow.setAccountID(accountId);
                borrow.setBookID(bookId);
                System.out.println(borrow);
                if (BorrowEntity.Add(borrow)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Borrows Manager");
                    alert.setContentText("Added Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Borrows Manager");
                    alert.setContentText("Added Fail!");
                    alert.showAndWait();
                }
            } else {
                int accountId = BorrowEntity.selectAccountIndex(txtAccount);
                int bookId = BorrowEntity.selectBookIndex(txtBook);
                int statusId = BorrowEntity.selectStatusIndex(txtStatus);
                manaId = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getManageId()));
                myIndex = table.getSelectionModel().getSelectedIndex();
                id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
                Borrow borrow = new Borrow();
                borrow.setId(id);
                borrow.setRefundAt(txtRefundAt.getValue().toString());
                borrow.setBorrowAt(txtBorrowAt.getValue().toString());
                borrow.setStatusId(statusId);
                borrow.setManageId(manaId);
                borrow.setAccountID(accountId);
                borrow.setBookID(bookId);

                if (BorrowEntity.Update(borrow)) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Borrows Manager");
                    alert.setContentText("Updated Successfully!");
                    alert.showAndWait();
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Test Connection");
                    alert.setHeaderText("Borrows Manager");
                    alert.setContentText("Updated Fail!");
                    alert.showAndWait();
                }
            }
        } catch (RuntimeException ignored) {
        }

        RefreshData();
    }

    @FXML
    private void BtnDeleteClick() {
        int id = Integer.parseInt(txtID.getText());
        Alert alert = new Alert(Alert.AlertType.NONE);

        if (BorrowEntity.Delete(id)) {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Borrows Manager");
            alert.setContentText("Deleted Successfully!");
            alert.showAndWait();
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("Test Connection");
            alert.setHeaderText("Borrows Manager");
            alert.setContentText("Deleted Fail!");
            alert.showAndWait();
        }
        RefreshData();
    }

    @FXML
    private void ResetField() {
        txtID.clear();
        txtAccount.setValue(null);
        txtBook.setValue(null);
        txtStatus.setValue(null);
        txtBorrowAt.setValue(null);
        txtSearch.clear();
        txtRefundAt.setValue(null);

        errorAccount.setVisible(false);
        errorBorrowAt.setVisible(false);
        errorBook.setVisible(false);
        errorRefundAt.setVisible(false);
        errorStatus.setVisible(false);

        CheckId();
        btnSave.setDisable(true);
    }

    @FXML
    private void RefreshData() {
        ResetField();
        table();
    }

    @FXML
    private void Search() {
        this.table();
    }

    public void data_Box() {
        BorrowEntity.data_Account(txtAccount);
        BorrowEntity.data_Books(txtBook);
        BorrowEntity.data_Status(txtStatus);
    }

    @FXML
    public void table() {
        if (txtSearch.getText().equals("")) {
            ObservableList<Borrow> borrow = BorrowEntity.GetAll();
            table.setItems(borrow);
        } else {
            String search = txtSearch.getText();
            ObservableList<Borrow> borrow = BorrowEntity.Search(search);
            table.setItems(borrow);
        }
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asString());
        colUID.setCellValueFactory(f -> f.getValue().idProperty().asString());
        colBook.setCellValueFactory(f -> f.getValue().bookNameProperty());
        colBorrow.setCellValueFactory(f -> f.getValue().borrowAtProperty());
        colRefund.setCellValueFactory(f -> f.getValue().refundAtProperty());
        colStatus.setCellValueFactory(f -> f.getValue().statusNameProperty());

        table.setRowFactory(tv -> {
            TableRow<Borrow> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    String[] dateBorrow = table.getItems().get(myIndex).getBorrowAt().split("-");
                    String[] dataRefund = table.getItems().get(myIndex).getRefundAt().split("-");

                    LocalDate dobBorrow = LocalDate.of(Integer.parseInt(dateBorrow[0]), Integer.parseInt(dateBorrow[1]), Integer.parseInt(dateBorrow[2]));
                    LocalDate dobRefund = LocalDate.of(Integer.parseInt(dataRefund[0]), Integer.parseInt(dataRefund[1]), Integer.parseInt(dataRefund[2]));

                    txtBorrowAt.setValue(dobBorrow);
                    txtRefundAt.setValue(dobRefund);

                    txtID.setText(String.valueOf(table.getItems().get(myIndex).getId()));
                    txtAccount.setValue(table.getItems().get(myIndex).getAccountName());
                    txtBook.setValue(table.getItems().get(myIndex).getBookName());
                    txtStatus.setValue(table.getItems().get(myIndex).getStatusName());

                    CheckId();
                    Validated();
                }
            });
            return myRow;
        });
    }

    

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
        prefs.clear();
        App.setRoot("SignIn");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        this.table();
        btnSave.setDisable(true);
        CheckId();
        this.data_Box();
        ResetField();
    }

}
