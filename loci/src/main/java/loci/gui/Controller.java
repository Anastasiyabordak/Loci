package loci.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import loci.entity.Desk;
import loci.entity.Card;
import loci.exception.CustomException;

import loci.parser.DatabaseCreator;
import loci.traning.ChooseOneOfFour;
import loci.traning.EnterWord;
import loci.traning.WordFromParts;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    //Tab of "Training"
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
    private RadioButton trainingByChoosingTheAnswer;

    @FXML //тут все текущие словари
    private ComboBox categoryOfCardBox;
    @FXML
    private TableView<Card> tableViev;
    @FXML
    private TableColumn<Card, String> backTableColumn;
    @FXML
    private TableColumn<Card, String> frontTableColumn;
    @FXML
    private ImageView deskQuestionImage;

    private static final String SUBSTRING_PATH = "src/main/resources/";
    private final Image START_IMAGE = new Image("images/question.png");
    private static final String RED_COLOR = "-fx-text-fill: red";
    private static final String BLACK_COLOR = "-fx-text-fill: black";
    private static final String GREEN_COLOR = "-fx-text-fill: green";


    private EnterWord enterWord = new EnterWord();
    private Card card;
    private ToggleGroup trainingsGroup = new ToggleGroup();
    private List<String> syllables;


    @FXML
    public void initialize(final URL location, final ResourceBundle resources) {

        setAllWidgetsUnvisible();
        questionImage.setImage(START_IMAGE);
        try {
            DatabaseCreator creator = new DatabaseCreator();
            creator.createDatabase();

        } catch (CustomException e) {
            e.printStackTrace();
        }

        setDesk();
        setCategoryOfCardBox();
        createToggleGroup();

    }

    private void createToggleGroup() {
        trainingByImageAndDefinition.setToggleGroup(trainingsGroup);
        trainingByImage.setToggleGroup(trainingsGroup);
        trainingByDefinition.setToggleGroup(trainingsGroup);
        trainingBySyllable.setToggleGroup(trainingsGroup);
        trainingByChoosingTheAnswer.setToggleGroup(trainingsGroup);
        trainingByImageAndDefinition.setSelected(true);
    }

    public void getImage() {
        Card selectedCard;
        selectedCard = tableViev.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            Image image = new Image(selectedCard.getPicturePath().substring(SUBSTRING_PATH.length()));
            deskQuestionImage.setImage(image); //WTF 19?
        }
    }

    public void checkAnswerForEnter(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (trainingBySyllable.isSelected()) {
                checkSyllableTraining();
            } else if (trainingByChoosingTheAnswer.isSelected()) {
                checkChoosingTraining();
            }
        }
    }

    private void checkSyllableTraining() {
        if (!resultText.getText().equals("") && !syllables.isEmpty() && !resultText.getFill().equals(Color.BLACK)) {
            resultText.setFill(Color.BLACK);
            resultText.setText(card.getWord());
            setDisableButtons(true);
        } else if (resultText.getText().equals("")) {
            resultText.setText(card.getWord());
        } else {
            selectAndStartTraining();
        }
    }

    private void checkChoosingTraining() {
        if (resultText.getText().equals("")) {
            resultText.setText(card.getWord());
            setDisableButtons(true);
        } else {
            selectAndStartTraining();
        }
    }

    public void checkAnswer(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!this.resultText.getText().equalsIgnoreCase("")) {
                this.answerTextArea.setText("");
                this.answerTextArea.setStyle(BLACK_COLOR);
                this.answerTextArea.setEditable(true);
                this.resultText.setVisible(false);
                selectAndStartTraining();
            } else {
                if (enterWord.checkEnteredWord(card, answerTextArea.getText())) {
                    this.answerTextArea.setStyle(GREEN_COLOR);
                } else if (!answerTextArea.getText().equalsIgnoreCase("")) {
                    this.answerTextArea.setStyle(RED_COLOR);
                }
                this.resultText.setFill(Color.GREEN);
                this.answerTextArea.setEditable(false);
                this.resultText.setText(card.getWord());
                this.resultText.setVisible(true);
            }
        }
    }

    public void goToTraining() {
        trainingSettingsPane.setVisible(false);
        trainingPane.setVisible(true);
        selectAndStartTraining();
    }

    public void goToSettings() {
        trainingSettingsPane.setVisible(true);
        trainingPane.setVisible(false);
    }

    public void changeCardsOfCategory() {
        String category = categoryOfCardBox.getValue().toString();
        deskQuestionImage.setImage(START_IMAGE);
        if (category.equalsIgnoreCase("all")) {
            tableViev.setItems(new Desk().setCardsList());
        } else {
            tableViev.setItems(new Desk().setCardsList(category));
        }
    }

    public void variantASelected() {
        if (trainingBySyllable.isSelected()) {
            analizeAnswerInSyllableTraining(buttonVariantA);
        } else if (trainingByChoosingTheAnswer.isSelected()) {
            analizeAnswerInChoosingTraining(buttonVariantA);
        }
    }

    public void variantBSelected() {
        if (trainingBySyllable.isSelected()) {
            analizeAnswerInSyllableTraining(buttonVariantB);
        } else if (trainingByChoosingTheAnswer.isSelected()) {
            analizeAnswerInChoosingTraining(buttonVariantB);
        }
    }

    public void variantCSelected() {
        if (trainingBySyllable.isSelected()) {
            analizeAnswerInSyllableTraining(buttonVariantC);
        } else if (trainingByChoosingTheAnswer.isSelected()) {
            analizeAnswerInChoosingTraining(buttonVariantC);
        }
    }

    public void variantDSelected() {
        if (trainingBySyllable.isSelected()) {
            analizeAnswerInSyllableTraining(buttonVariantD);
        } else if (trainingByChoosingTheAnswer.isSelected()) {
            analizeAnswerInChoosingTraining(buttonVariantD);
        }
    }

    private void analizeAnswerInChoosingTraining(final Button button) {
        if (button.getText().equalsIgnoreCase(card.getWord())) {
            button.setTextFill(Color.GREEN);
        } else {
            button.setTextFill(Color.RED);
        }
        resultText.setText(card.getWord());
        resultText.setFill(Color.GREEN);
        setDisableButtons(true);
    }

    private void analizeAnswerInSyllableTraining(final Button button) {
        if (!syllables.isEmpty()) {
            if (syllables.get(0).equals(button.getText())) {
                button.setTextFill(Color.GREEN);
                resultText.setFill(Color.GREEN);
                resultText.setText(resultText.getText() + syllables.remove(0));
            } else {
                button.setTextFill(Color.RED);
                resultText.setText(resultText.getText() + button.getText());
                resultText.setFill(Color.RED);
                setDisableButtons(true);
            }
        }
        button.setDisable(true);
    }

    private void selectAndStartTraining() {
        setAllWidgetsUnvisible();
        changeCard();
        if (trainingByImageAndDefinition.isSelected()) {
            enterWordByImageAndDefinitionTraining();
        }
        if (trainingByImage.isSelected()) {
            enterWordByImageTraining();
        }
        if (trainingByDefinition.isSelected()) {
            enterWordByDefinitionTraining();
        }
        if (trainingBySyllable.isSelected()) {
            buttonsTrainingBySyllable();
        }
        if (trainingByChoosingTheAnswer.isSelected()) {
            buttonsTrainingByChoosingTheAnswer();
        }
    }

    private void buttonsTrainingByChoosingTheAnswer() {
        prepareScreenForButtonsTraining();
        ChooseOneOfFour chooseOneOfFour = new ChooseOneOfFour();
        List<String> words = chooseOneOfFour.getListOfWords(card);
        questionTextArea.setText(card.getDefinition());
        setNewQuestionImage();
        setAnswersInButtons(words);
    }

    private void buttonsTrainingBySyllable() {
        prepareScreenForButtonsTraining();
        WordFromParts wordFromParts = new WordFromParts();
        syllables = wordFromParts.wordSplit(card);
        List<String> sortedSyllables = new ArrayList<>(syllables);
        questionTextArea.setText(card.getDefinition());
        setNewQuestionImage();
        wordFromParts.mixWordParts(sortedSyllables);
        setAnswersInButtons(sortedSyllables);
    }


    private void setAnswersInButtons(final List<String> answers) {
        buttonVariantA.setText(answers.remove(0));
        buttonVariantB.setText(answers.remove(0));
        if (!answers.isEmpty()) {
            buttonVariantC.setText(answers.remove(0));
        } else {
            buttonVariantC.setVisible(false);
        }
        if (!answers.isEmpty()) {
            buttonVariantD.setText(answers.remove(0));
        } else {
            buttonVariantD.setVisible(false);
        }
    }

    private void enterWordByImageAndDefinitionTraining() {
        prepareScreenForEnterTraining();
        this.questionTextArea.setText(card.getDefinition());
        setNewQuestionImage();
    }

    private void setNewQuestionImage() {
        String path = card.getPicturePath().substring(SUBSTRING_PATH.length());
        Image image = new Image(path);
        questionImage.setImage(image);
    }

    private void enterWordByDefinitionTraining() {
        prepareScreenForEnterTraining();
        this.questionTextArea.setText(card.getDefinition());
    }

    private void enterWordByImageTraining() {
        prepareScreenForEnterTraining();
        setNewQuestionImage();
    }

    private void changeCard() {
        String category = enterWord.chooseCategory();
        card = enterWord.chooseCard(category);
    }

    private void prepareScreenForButtonsTraining() {
        prepareScreen();
        this.buttonVariantA.setText("");
        this.buttonVariantB.setText("");
        this.buttonVariantC.setText("");
        this.buttonVariantD.setText("");
        setTextFillButtons(Color.BLACK);
        this.gridForButtons.setVisible(true);
        buttonVariantC.setVisible(true);
        buttonVariantD.setVisible(true);
    }

    private void prepareScreenForEnterTraining() {
        prepareScreen();
        this.answerTextArea.setText("");
        this.answerTextArea.setVisible(true);
        this.answerTextArea.setEditable(true);
    }

    private void prepareScreen() {
        this.resultText.setFill(Color.BLACK);
        this.resultText.setText("");
        this.resultText.setVisible(true);
        this.questionTextArea.setText("");
        this.questionTextArea.setVisible(true);
        this.questionImage.setImage(START_IMAGE);
        this.questionImage.setVisible(true);
    }

    private void setAllWidgetsUnvisible() {
        setDisableButtons(false);
        this.gridForButtons.setVisible(false);
        this.questionTextArea.setVisible(false);
        this.questionImage.setVisible(false);
        this.answerTextArea.setVisible(false);
        this.resultText.setVisible(false);
    }

    private void setDesk() {

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
        backTableColumn.setResizable(false);
        frontTableColumn.setResizable(false);
        tableViev.setItems(new Desk().setCardsList());

    }

    private void setTextFillButtons(Color color) {
        buttonVariantA.setTextFill(color);
        buttonVariantB.setTextFill(color);
        buttonVariantC.setTextFill(color);
        buttonVariantD.setTextFill(color);
    }

    private void setDisableButtons(boolean isDisable) {
        buttonVariantA.setDisable(isDisable);
        buttonVariantB.setDisable(isDisable);
        buttonVariantC.setDisable(isDisable);
        buttonVariantD.setDisable(isDisable);
    }

    private void setCategoryOfCardBox() {
        categoryOfCardBox.setItems(new Desk().setCategoryList());
    }

    public void openImageDesk() {

        if (deskQuestionImage.getImage() != START_IMAGE) {

            Alert dialog = new Alert(Alert.AlertType.NONE);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setTitle("IMAGE");

            ImageView img = new ImageView(deskQuestionImage.getImage());
            img.setPreserveRatio(true);
            img.setFitHeight(700);
            img.setFitWidth(500);

            BorderPane pane = new BorderPane(img);
            pane.setPrefSize(300, 400);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.getDialogPane().setGraphic(pane);
            dialog.showAndWait();
        }
    }
}
