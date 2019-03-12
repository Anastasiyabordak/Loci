package loci.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.util.Optional;
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
    private Button variantA_Button;
    @FXML
    private Button variantB_Button;
    @FXML
    private Button variantC_Button;
    @FXML
    private Button variantD_Button;
    @FXML
    private Button goToSettingsButton;
    @FXML
    private GridPane gridForButtons;
    //Settings for training
    @FXML
    private RadioButton isTraining1Set;
    @FXML
    private RadioButton isTraining2Set;
    @FXML
    private RadioButton isTraining3Set;
    @FXML
    private RadioButton isTraining4Set;
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

    EnterWord enterWord = new EnterWord();
    Card card;
    String startPath = new String("images/question.png");
    Image startImage = new Image(startPath);

    //Tab of "Help"
    @FXML
    private Tab helpTab;
    @FXML
    private TextArea helpInformationTextArea;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        setAllWidgetsUnvisible();
        questionImage.setImage(startImage);
        try {
            DatabaseCreator creator = new DatabaseCreator();
            creator.createDatabase();

        } catch (CustomException e) {
            e.printStackTrace();
        }

        setDesk();
        setCategoryOfCardBox();
        enterWordTraning();
    }

    public void getImage(MouseEvent event){
        Card selectedCard;
        selectedCard = tableViev.getSelectionModel().getSelectedItem();
        deskQuestionImage.setImage(new Image(selectedCard.getPicturePath().substring(19)));
    }

    public void checkAnswer(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER){
            if(this.resultText.isVisible()){
                this.answerTextArea.setText("");
                this.answerTextArea.setStyle("-fx-text-fill: black");
                this.answerTextArea.setEditable(true);
                this.resultText.setVisible(false);
                enterWordTraning();
            }
            else{
                if(answerTextArea.getText().equalsIgnoreCase(card.getWord()))
                    this.answerTextArea.setStyle("-fx-text-fill: green");
                else
                    this.answerTextArea.setStyle("-fx-text-fill: red");
                this.answerTextArea.setEditable(false);
                this.resultText.setText(card.getWord());
                this.resultText.setVisible(true);
            }
        }
    }

    public void goToTraining(ActionEvent event){

        trainingSettingsPane.setVisible(false);
        trainingPane.setVisible(true);

    }

    public void goToSettings(ActionEvent event){

        trainingSettingsPane.setVisible(true);
        trainingPane.setVisible(false);

    }

    public void changeCardsOfCategory(ActionEvent event){

        String category = categoryOfCardBox.getValue().toString();
        deskQuestionImage.setImage(new Image(startPath));
        if(category.equalsIgnoreCase("all"))
            tableViev.setItems(new Desk().setCardsList());
        else
            tableViev.setItems(new Desk().setCardsList(category));
    }

    public void variantASelected(ActionEvent event)
    {
//        this.resultText.setVisible(true);
//        this.answerTextArea.setVisible(false);
//        this.resultText.setText("Ты нажал на кнопку А");
    }

    public void variantBSelected(ActionEvent event)
    {
//        this.resultText.setVisible(true);
//        this.answerTextArea.setVisible(false);
//        this.resultText.setText("Ты нажал на кнопку B");
    }

    public void variantCSelected(ActionEvent event)
    {
//        this.resultText.setVisible(true);
//        this.answerTextArea.setVisible(false);
//        this.resultText.setText("Ты нажал на кнопку C");
    }

    public void variantDSelected(ActionEvent event)
    {
    }

    public void answerSelected(ActionEvent event)
    {
//        this.variantA_Button.setVisible(true);
//        this.variantB_Button.setVisible(true);
//        this.variantC_Button.setVisible(true);
//        this.variantD_Button.setVisible(true);
//        this.answerTextArea.setText("Вы отаетили на вопрос");
    }

    public void enterWordTraning() {
        this.answerTextArea.setVisible(true);
        this.answerTextArea.requestFocus();
        String category = enterWord.chooseCategory();
        card = enterWord.chooseCard(category);
        this.questionTextArea.setText(card.getDefinition());
        questionImage.setImage(new Image(card.getPicturePath().substring(19)));
    }

    public void setAllWidgetsUnvisible(){
        this.gridForButtons.setVisible(false);
        this.answerTextArea.setVisible(false);
        this.resultText.setVisible(false);
    }

    public void setDesk()
    {

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

    public void setCategoryOfCardBox()
    {
        categoryOfCardBox.setItems(new Desk().setCategoryList());
    }

    public void openImageDesk(MouseEvent event){

        Alert dialog = new Alert(Alert.AlertType.NONE);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("IMAGE");

        Card setCard;
        setCard = tableViev.getSelectionModel().getSelectedItem();
        ImageView img;
        if(setCard == null)
            img = new ImageView(new Image(startPath));//deskQuestionImage;
        else
            img = new ImageView(new Image(setCard.getPicturePath().substring(19)));//deskQuestionImage;

        img.setPreserveRatio(true);
        img.setFitHeight(700);
        img.setFitWidth(500);

        BorderPane pane = new BorderPane();
        pane.setPrefSize(300, 400);
        pane.setCenter(img);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setGraphic(pane);

        Optional<ButtonType> r = dialog.showAndWait();
    }
}
