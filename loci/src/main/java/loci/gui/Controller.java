package loci.gui;

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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
    @FXML
    private GridPane gridForButtons;

    EnterWord enterWord = new EnterWord();
    Card card;
    Image startImage = new Image("images/question.png");

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
        selectedCard.getPicturePath();
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

    public void changeCardsOfCategory(ActionEvent event){

        String category = categoryOfCardBox.getValue().toString();
        deskQuestionImage.setImage(new Image("images/question.png"));
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
        this.variantA_Button.setVisible(false);
        this.variantB_Button.setVisible(false);
        this.variantC_Button.setVisible(false);
        this.variantD_Button.setVisible(false);
        this.answerTextArea.setVisible(false);
        this.resultText.setVisible(false);
    }

    public void setDesk()
    {
        backTableColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("word"));
        frontTableColumn.setCellValueFactory(new PropertyValueFactory<Card, String>("definition"));
//        tableViev.setItems(new Desk().setCardsList("animals"));
        tableViev.setItems(new Desk().setCardsList());
    }

    public void setCategoryOfCardBox()
    {
        categoryOfCardBox.setItems(new Desk().setCategoryList());
    }

}
