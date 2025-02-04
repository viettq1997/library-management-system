package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Models.*;
import com.project2group4.librarymanagement.Entites.*;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

public class CustomerDashboardController implements Initializable {

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
    private TableView<Top5CategoriesInterested> tableCategories;
    @FXML
    private TableColumn<Top5CategoriesInterested, String> colCategoryName;
    @FXML
    private TableColumn<Top5CategoriesInterested, String> colCategoryTotal;
    @FXML
    private TableView<Top5AuthorsIntersted> tableAuthors;
    @FXML
    private TableColumn<Top5AuthorsIntersted, String> colAuthorsName;
    @FXML
    private TableColumn<Top5AuthorsIntersted, String> colAuthorsTotal;
    @FXML
    private Label totalBooksInterested;
    @FXML
    private Label totalAuthorsInterested;
    @FXML
    private Label totalCategoriesInterested;
    @FXML
    private Label totalPublishsingInterested;

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
        Total();
        Top5CategoriesFavorites();
        Top5AuthorsFavorites();
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

    @FXML
    private void Total() {
        String username = user.getUserName();

        Account acc = AccountEntity.GetAccountByUsername(username);

        int totalBook = ManageBookEntity.CountBookInterestedByUID(acc.getUID());
        int totalCategory = ManageBookEntity.CountCategoryInterestedByUID(acc.getUID());
        int totalPublish = ManageBookEntity.CountPublishingInterestedByUID(acc.getUID());
        int totalAuthor = ManageBookEntity.CountAuthorInterestedByUID(acc.getUID());

        totalBooksInterested.setText(String.valueOf(totalBook));
        totalCategoriesInterested.setText(String.valueOf(totalCategory));
        totalPublishsingInterested.setText(String.valueOf(totalPublish));
        totalAuthorsInterested.setText(String.valueOf(totalAuthor));
    }

    @FXML
    private void Top5CategoriesFavorites() {
        String username = user.getUserName();

        Account acc = AccountEntity.GetAccountByUsername(username);
        ObservableList<Top5CategoriesInterested> t5c = ManageBookEntity.Top5CategoryInterestedByUID(acc.getUID());
        tableCategories.setItems(t5c);
        colCategoryName.setCellValueFactory(f -> {
            int id = f.getValue().getId();
            Category c = CategoryEntity.GetCategoryById(id);

            return c.nameProperty();
        });
        colCategoryTotal.setCellValueFactory(f -> f.getValue().totalProperty().asString());
    }

    @FXML
    private void Top5AuthorsFavorites() {
        String username = user.getUserName();

        Account acc = AccountEntity.GetAccountByUsername(username);
        ObservableList<Top5AuthorsIntersted> t5c = ManageBookEntity.Top5AuthorInterestedByUID(acc.getUID());
        tableAuthors.setItems(t5c);
        colAuthorsName.setCellValueFactory(f -> {
            int id = f.getValue().getId();
            Author c = AuthorEntity.GetAuthorWithId(id);

            return c.nameProperty();
        });
        colAuthorsTotal.setCellValueFactory(f -> f.getValue().totalProperty().asString());
    }
}
