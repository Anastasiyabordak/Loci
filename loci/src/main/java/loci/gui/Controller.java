package loci.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import loci.desk.Desk;
import loci.entity.Card;
import loci.exception.CustomException;

import loci.parser.DatabaseCreator;
import loci.traning.EnterWord;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    //Tab of "Training"
    @FXML
    private Tab trainTab;
    @FXML
    private AnchorPane trainingPane;
    @FXML
    private AnchorPane trainingSettingsPane;
    //Training
    @FXML
    private Text resultText;
    @FXML
    private Text questionTextArea;
    @FXML
    private TextField answerTextArea;
    @FXML
    private ImageView questionImage;
    @FXML
    private Button buttonVariantA;
    @FXML
    private Button buttonVariantB;
    @FXML
    private Button buttonVariantC;
    @FXML
    private Button buttonVariantD;
    @FXML
    private Button goToSettingsButton;
    @FXML
    private GridPane gridForButtons;
    //Settings for training
    @FXML
    private RadioButton trainingByImageAndDefinition;
    @FXML
    private RadioButton trainingByImage;
    @FXML
    private RadioButton trainingByDefinition;
    @FXML
    private RadioButton trainingBySyllable;
    @FXML
    private Button startTrainingButton;

    //Tab of "Desk"
    @FXML
    private Tab deskTab;
    @FXML
    private ComboBox categoryOfCardBox; //тут все текущие словари
    @FXML
    private TableView<Card> tableViev;
    @FXML
    private TableColumn<Card, String> backTableColumn;
    @FXML
    private TableColumn<Card, String> frontTableColumn;
    @FXML
    private TableColumn statusTableColumn;
    @FXML
    private ImageView deskQuestionImage;

    private EnterWord enterWord = new EnterWord();
    private Card card;
    private Image startImage = new Image("images/question.png");
    private int substringPath = 19;
    private ToggleGroup trainingsGroup = new ToggleGroup();

    //Tab of "Help"
    @FXML
    private Tab helpTab;
    @FXML
    private TextArea helpInformationTextArea;

    @FXML
    public void initialize(final URL location, final ResourceBundle resources) {

        setAllWidgetsUnvisible();
        questionImage.setImage(startImage);
        try {
            DatabaseCreator creator = new DatabaseCreator();
            creator.createDatabase();

        } catch (CustomException e) {
            e.printStackTrace();
        }

        deskQuestionImage.setImage(startImage);

        setDesk();
        setCategoryOfCardBox();
        createToggleGroup();
        //enterWordTraining();
    }

    public void createToggleGroup() {
        trainingByImageAndDefinition.setToggleGroup(trainingsGroup);
        trainingByImage.setToggleGroup(trainingsGroup);
        trainingByDefinition.setToggleGroup(trainingsGroup);
        trainingBySyllable.setToggleGroup(trainingsGroup);
    }

    public void getImage(final MouseEvent event) {
        Card selectedCard;
        selectedCard = tableViev.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            deskQuestionImage.setImage(new Image(selectedCard.getPicturePath().substring(substringPath))); //WTF 19?
        }
    }

    public void checkAnswer(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (this.resultText.isVisible()) {
                this.answerTextArea.setText("");
                this.answerTextArea.setStyle("-fx-text-fill: black"); //please make private constant "-fx-text-fill" and "black"
                this.answerTextArea.setEditable(true);
                this.resultText.setVisible(false);
                selectAndStartTraining();
            } else {
                if (answerTextArea.getText().equalsIgnoreCase(card.getWord()))
                    this.answerTextArea.setStyle("-fx-text-fill: green");
                else
                    this.answerTextArea.setStyle("-fx-text-fill: red");
                this.answerTextArea.setEditable(false);
                this.resultText.setText(card.getWord());
                this.resultText.setVisible(true);
            }
        }
    }

    public void goToTraining(final ActionEvent event) {
        trainingSettingsPane.setVisible(false);
        trainingPane.setVisible(true);
        selectAndStartTraining();
    }

    public void goToSettings(final ActionEvent event) {
        trainingSettingsPane.setVisible(true);
        trainingPane.setVisible(false);
    }

    public void changeCardsOfCategory(final ActionEvent event) {
        String category = categoryOfCardBox.getValue().toString();
        deskQuestionImage.setImage(startImage);
        if (category.equalsIgnoreCase("all"))
            tableViev.setItems(new Desk().setCardsList());
        else
            tableViev.setItems(new Desk().setCardsList(category));
    }

    public void variantASelected(ActionEvent event) {
    }

    public void variantBSelected(ActionEvent event) {
    }

    public void variantCSelected(ActionEvent event) {
    }

    public void variantDSelected(ActionEvent event) {
    }

    public void selectAndStartTraining() {
        if(trainingByImageAndDefinition.isSelected())
            enterWordByImageAndDefinitionTraining();
        if(trainingByImage.isSelected())
            enterWordByImageTraining();
        if(trainingByDefinition.isSelected())
            enterWordByDefinitionTraining();

    }

    public void enterWordByImageAndDefinitionTraining() {
        this.answerTextArea.setVisible(true);
        changeCard();
        this.questionTextArea.setText(card.getDefinition());
        questionImage.setImage(new Image(card.getPicturePath().substring(substringPath)));
    }

    public void enterWordByDefinitionTraining() {
        this.answerTextArea.setVisible(true);
        changeCard();
        this.questionTextArea.setText(card.getDefinition());
        questionImage.setImage(startImage);
    }

    public void enterWordByImageTraining() {
        this.answerTextArea.setVisible(true);
        changeCard();
        questionImage.setImage(new Image(card.getPicturePath().substring(substringPath)));
        questionTextArea.setText("");
    }

    public void changeCard() {
        String category = enterWord.chooseCategory();
        card = enterWord.chooseCard(category);
    }

    public void setAllWidgetsUnvisible() {
        this.gridForButtons.setVisible(false);
        this.answerTextArea.setVisible(false);
        this.resultText.setVisible(false);
    }

    public void setDesk() {

        frontTableColumn.setCellValueFactory(cellData -> (new SimpleStringProperty()));
        frontTableColumn.setCellFactory(param -> {
            TableCell<Card, String> cell = new TableCell<>();
            Text text = new Text();
            text.setStyle("");
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(frontTableColumn.widthProperty());
            return cell;
        });
        backTableColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        frontTableColumn.setCellValueFactory(new PropertyValueFactory<>("definition"));
        tableViev.setItems(new Desk().setCardsList());

    }

    public void setCategoryOfCardBox() {
        categoryOfCardBox.setItems(new Desk().setCategoryList());
    }

    public void openImageDesk(MouseEvent event) {

        if (deskQuestionImage.getImage() != startImage) {

            Alert dialog = new Alert(Alert.AlertType.NONE);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("IMAGE");

            ImageView img = new ImageView();
            img.setImage(deskQuestionImage.getImage()); 
            img.setPreserveRatio(true);
            img.setFitHeight(700);
            img.setFitWidth(500);

            BorderPane pane = new BorderPane();
            pane.setPrefSize(300, 400);
            pane.setCenter(img);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.getDialogPane().setGraphic(pane);
            dialog.showAndWait();
        }
    }
}
