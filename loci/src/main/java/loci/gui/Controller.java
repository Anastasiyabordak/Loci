package loci.gui;

import javafx.beans.property.SimpleStringProperty;
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
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URL;
import java.util.*;

import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;


public class Controller implements Initializable {

    /**
     * Value of the object Logger for debug.
     */
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

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
    private List<String> syllables;
    private boolean isMistake = false;

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
            LOGGER.error(e);
            //e.printStackTrace();
        }

        deskQuestionImage.setImage(startImage);

        setDesk();
        setCategoryOfCardBox();
        createToggleGroup();
        
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

    public void checkAnswerForEnter(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && trainingBySyllable.isSelected()) {
            if(resultText.getFill().equals(Color.BLACK))
            {
                resultText.setFill(Color.GREEN);
                selectAndStartTraining();
            }
            else
            {
                if(syllables.isEmpty()) {
                    resultText.setFill(Color.GREEN);
                    selectAndStartTraining();
                }
                else
                {
                    resultText.setFill(Color.BLACK);
                    resultText.setText(card.getWord());
                    isMistake = false;
                    buttonVariantA.setDisable(true);
                    buttonVariantB.setDisable(true);
                    buttonVariantC.setDisable(true);
                    buttonVariantD.setDisable(true);
                }
            }

        }
    }

    public void checkAnswer(final KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!this.resultText.getText().equalsIgnoreCase("")) {
                this.answerTextArea.setText("");
                this.answerTextArea.setStyle("-fx-text-fill: black"); //please make private constant "-fx-text-fill" and "black"
                this.answerTextArea.setEditable(true);
                this.resultText.setVisible(false);
                selectAndStartTraining();
            } else {
                if (answerTextArea.getText().equalsIgnoreCase(card.getWord()))
                {
                    this.answerTextArea.setStyle("-fx-text-fill: green");
                    this.resultText.setFill(Color.GREEN);
                }
                else if(!answerTextArea.getText().equalsIgnoreCase("")){
                    this.answerTextArea.setStyle("-fx-text-fill: red");
                    this.resultText.setFill(Color.GREEN);
                }
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
        analizeAnswer(buttonVariantA);
        buttonVariantA.setDisable(true);
    }

    public void variantBSelected(ActionEvent event) {
        analizeAnswer(buttonVariantB);
        buttonVariantB.setDisable(true);
    }

    public void variantCSelected(ActionEvent event) {
        analizeAnswer(buttonVariantC);
        buttonVariantC.setDisable(true);
    }

    public void variantDSelected(ActionEvent event) {
        analizeAnswer(buttonVariantD);
        buttonVariantD.setDisable(true);
    }

    public void analizeAnswer(Button button) {
        if(!syllables.isEmpty() && !isMistake)
        {
            if(syllables.get(0).equalsIgnoreCase(button.getText()))
            {
                button.setTextFill(Color.GREEN);
                resultText.setText(resultText.getText() + syllables.remove(0));
            }
            else{
                button.setTextFill(Color.RED);
                resultText.setText(resultText.getText() + button.getText());
                isMistake = true;
                resultText.setFill(Color.RED);
                buttonVariantA.setDisable(true);
                buttonVariantB.setDisable(true);
                buttonVariantC.setDisable(true);
                buttonVariantD.setDisable(true);
            }
        }
//        else{
//            isMistake = false;
//            resultText.setFill(Color.GREEN);
//            selectAndStartTraining();
//        }
    }

    public void selectAndStartTraining() {
        setAllWidgetsUnvisible();
        changeCard();
        if(trainingByImageAndDefinition.isSelected())
            enterWordByImageAndDefinitionTraining();
        if(trainingByImage.isSelected())
            enterWordByImageTraining();
        if(trainingByDefinition.isSelected())
            enterWordByDefinitionTraining();
        if(trainingBySyllable.isSelected())
            buttonsTrainingBySyllable();

    }

    public void buttonsTrainingBySyllable()
    {
        prepareScreenForButtonsTraining();
        WordFromParts wordFromParts = new WordFromParts();
        syllables = wordFromParts.wordSplit(card);
        List<String> sortedSyllables = new ArrayList<>();
        sortedSyllables.addAll(syllables);
        questionTextArea.setText(card.getDefinition());
        questionImage.setImage(new Image(card.getPicturePath().substring(substringPath)));
        Collections.sort(sortedSyllables, Comparator.comparing(Object::toString));
        setSyllablesInButtons(sortedSyllables);
    }

    public void setSyllablesInButtons( List<String> sortebSyllables)
    {
        buttonVariantA.setText(sortebSyllables.remove(0));
        buttonVariantB.setText(sortebSyllables.remove(0));
        if(!sortebSyllables.isEmpty())
            buttonVariantC.setText(sortebSyllables.remove(0));
        else
            buttonVariantC.setVisible(false);
        if(!sortebSyllables.isEmpty())
            buttonVariantD.setText(sortebSyllables.remove(0));
        else
            buttonVariantD.setVisible(false);
    }

    public void enterWordByImageAndDefinitionTraining() {
        prepareScreenForEnterTraining();
        this.questionTextArea.setText(card.getDefinition());
        questionImage.setImage(new Image(card.getPicturePath().substring(substringPath)));
    }

    public void enterWordByDefinitionTraining() {
        prepareScreenForEnterTraining();
        this.questionTextArea.setText(card.getDefinition());
    }

    public void enterWordByImageTraining() {
        prepareScreenForEnterTraining();
        questionImage.setImage(new Image(card.getPicturePath().substring(substringPath)));
    }

    public void changeCard() {
        String category = enterWord.chooseCategory();
        card = enterWord.chooseCard(category);
    }

    public void prepareScreenForButtonsTraining()
    {
        this.resultText.setText("");
        this.questionTextArea.setText("");
        this.buttonVariantA.setText("");
        this.buttonVariantB.setText("");
        this.buttonVariantC.setText("");
        this.buttonVariantD.setText("");
        this.questionImage.setImage(startImage);
        this.questionTextArea.setVisible(true);
        this.questionImage.setVisible(true);
        this.resultText.setVisible(true);
        this.gridForButtons.setVisible(true);
        buttonVariantC.setVisible(true);
        buttonVariantD.setVisible(true);
        buttonVariantA.setTextFill(Color.BLACK);
        buttonVariantB.setTextFill(Color.BLACK);
        buttonVariantC.setTextFill(Color.BLACK);
        buttonVariantD.setTextFill(Color.BLACK);
        resultText.setFill(Color.GREEN);
        isMistake = false;
    }

    public void prepareScreenForEnterTraining()
    {
        this.resultText.setFill(Color.BLACK);
        this.answerTextArea.setText("");
        this.resultText.setText("");
        this.questionTextArea.setText("");
        this.questionImage.setImage(startImage);
        this.questionTextArea.setVisible(true);
        this.questionImage.setVisible(true);
        this.answerTextArea.setVisible(true);
        this.resultText.setVisible(true);
        this.answerTextArea.setEditable(true);
    }

    public void setAllWidgetsUnvisible() {
        buttonVariantA.setDisable(false);
        buttonVariantB.setDisable(false);
        buttonVariantC.setDisable(false);
        buttonVariantD.setDisable(false);
        this.gridForButtons.setVisible(false);
        this.questionTextArea.setVisible(false);
        this.questionImage.setVisible(false);
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
        backTableColumn.setResizable(false);
        frontTableColumn.setResizable(false);
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
