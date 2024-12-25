package com.project2group4.librarymanagement;

import com.project2group4.librarymanagement.Entites.*;
import com.project2group4.librarymanagement.Models.*;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class CustomerBorrowController implements Initializable {

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
    private TextField txtId;
    @FXML
    private ComboBox<Publishing> boxPublishing;
    @FXML
    private ComboBox<Author> boxAuthor;
    @FXML
    private ComboBox<Category> boxCategory;
    @FXML
    private ComboBox<Book> boxBook;
    @FXML
    private TableView<Borrow> table;
    @FXML
    private TableColumn<Borrow, String> colIndex;
    @FXML
    private TableColumn<Borrow, String> colId;
    @FXML
    private TableColumn<Borrow, String> colBook;
    @FXML
    private TableColumn<Borrow, String> colBorrowAt;
    @FXML
    private TableColumn<Borrow, String> colRefundedAt;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;

    @FXML
    private Label errorBook;

    int myIndex;
    int id;

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
        initBoxAuthors();
        initBoxCategory();
        initBoxPublishing();
        InitData();
    }

    @FXML
    private void ValidatedFilter() {

        Publishing pub = boxPublishing.getValue();
        Category cat = boxCategory.getValue();
        Author author = boxAuthor.getValue();

        if (pub == null || cat == null || author == null) {
            boxBook.setPromptText("Choose a Publishing, a Author and a Category!");
            errorBook.setText("Choose a Publishing, a Author and a Category!");
            errorBook.setVisible(true);

        } else {
            initBoxBook(pub, author, cat);
            errorBook.setVisible(false);
        }

    }

    @FXML
    private void ValidatedBook() {

        if (boxBook.getValue() == null) {
            boxBook.setPromptText("Choose a Book!");
            errorBook.setText("Choose a Book!");
            errorBook.setVisible(true);

        } else {
            errorBook.setVisible(false);
        }

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

    @FXML
    private void ResetField() {
        boxAuthor.setValue(null);
        boxBook.setValue(null);
        boxPublishing.setValue(null);
        boxCategory.setValue(null);
        errorBook.setVisible(false);
    }

    @FXML
    private void Search() {
        Account acc = AccountEntity.GetAccountByUsername(user.getUserName());
        BorrowEntity be = new BorrowEntity();
        ObservableList<Borrow> b = be.SearchBorrowByAccountId(acc.getId(), txtSearch.getText());
        table(b);
    }

    @FXML
    private void InitData() {
        Account acc = AccountEntity.GetAccountByUsername(user.getUserName());
        BorrowEntity be = new BorrowEntity();
        ObservableList<Borrow> b = be.GetBorrowByAccountId(acc.getId());
        table(b);
    }

    @FXML
    private void table(ObservableList<Borrow> b) {
        table.setItems(b);
        colIndex.setCellValueFactory(f -> f.getValue().indexProperty().asString());
        colId.setCellValueFactory(f -> f.getValue().idProperty().asString());
        colBook.setCellValueFactory(f -> {
            int mangeId = f.getValue().getManageId();
            ManageBook mg = ManageBookEntity.GetAllBookInfoById(mangeId);
            Book book = BookEntity.GetBookWithBookId(mg.getBook().getId());
            return book.nameProperty();
        });
        colBorrowAt.setCellValueFactory(f -> f.getValue().borrowAtProperty());
        colRefundedAt.setCellValueFactory(f -> f.getValue().refundAtProperty());

        table.setRowFactory(tv -> {
            TableRow<Borrow> myRow = new TableRow<>();
            myRow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                    myIndex = table.getSelectionModel().getSelectedIndex();

                    txtId.setText(String.valueOf(table.getItems().get(myIndex).getId()));
                    ManageBook mg = ManageBookEntity.GetAllBookInfoById(table.getItems().get(myIndex).getId());
                    Book book = BookEntity.GetBookWithBookId(mg.getBook().getId());
                    Publishing pub = PublishingEntity.GetPublishingWithId(book.getPublishingId());
                    Author author = AuthorEntity.GetAuthorWithId(book.getAuthorId());
                    Category cate = CategoryEntity.GetCategoryById(book.getCategoryId());

                    boxAuthor.setValue(author);
                    boxCategory.setValue(cate);
                    boxPublishing.setValue(pub);
                    boxBook.setValue(book);
                    ValidatedFilter();
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

    private void initBoxPublishing() {
        ObservableList<Publishing> publishs = PublishingEntity.GetAll();
        boxPublishing.setItems(publishs);
    }

    private void initBoxAuthors() {
        ObservableList<Author> authors = AuthorEntity.GetAll();
        boxAuthor.setItems(authors);
    }

    private void initBoxCategory() {
        ObservableList<Category> categories = CategoryEntity.GetAll();
        boxCategory.setItems(categories);
    }

    private void initBoxBook(Publishing pub, Author author, Category cat) {
        ObservableList<Book> books = BookEntity.GetBookWithPublishCategoryAuthor(pub, author, cat);
        boxBook.setItems(books);
    }

}
