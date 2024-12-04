package com.mycompany.mavenproject1;

import Entites.*;
import Models.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class AdminDashboardController implements Initializable {

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
    private Label totalBooks;
    @FXML
    private Label totalCategories;
    @FXML
    private Label totalAuthors;
    @FXML
    private Label totalPublishs;
    @FXML
    private Label totalAccounts;
    @FXML
    private Label totalReaders;

    /**
     * Initializes the controller class.
     */
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
        Total();
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

    public void Total() {
        Role readerRole = RoleEntity.GetOneByName("Reader");
        ObservableList<Book> books = BookEntity.GetAll();
        ObservableList<Category> categories = CategoryEntity.GetAll();
        ObservableList<Author> authors = AuthorEntity.GetAll();
        ObservableList<Publishing> publishs = PublishingEntity.GetAll();
        ObservableList<Account> accounts = AccountEntity.GetAll();
        ObservableList<Account> readers = AccountEntity.GetAccountByRole(readerRole.getId());

        totalBooks.setText(String.valueOf(books.size()));
        totalCategories.setText(String.valueOf(categories.size()));
        totalAuthors.setText(String.valueOf(authors.size()));
        totalPublishs.setText(String.valueOf(publishs.size()));
        totalAccounts.setText(String.valueOf(accounts.size()));
        totalReaders.setText(String.valueOf(readers.size()));
    }
}
