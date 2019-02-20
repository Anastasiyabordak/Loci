package loci.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import loci.exception.CustomException;
import loci.parser.DatabaseCreator;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    //Tab of "Training"
    @FXML
    private Tab trainTab;
    @FXML
    private Text resultText;
    @FXML
    private TextArea questionTextArea;
    @FXML
    private TextArea answerTextArea;
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
    private TableView tableViev;
    @FXML
    private TableColumn backTableColumn;
    @FXML
    private TableColumn frontTableColumn;
    @FXML
    private TableColumn statusTableColumn;
    @FXML
    private GridPane gridForButtons;

    Image im = new Image("images/question.png");


    //Tab of "Help"
    @FXML
    private Tab helpTab;
    @FXML
    private TextArea helpInformationTextArea;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
       this.answerTextArea.setVisible(true);
       //this.gridForButtons.setVisible(false);
        // this.answerTextArea.setManaged(false); // искл. из родит. вычислений
        questionImage.setImage(im);

        try {
            DatabaseCreator creator = new DatabaseCreator();
            creator.createDatabase();

        } catch (CustomException e) {
            e.printStackTrace();
        }

    }


    public void variantASelected(ActionEvent event)
    {
        this.resultText.setVisible(true);
        this.answerTextArea.setVisible(false);
        this.resultText.setText("Ты нажал на кнопку А");
    }

    public void variantBSelected(ActionEvent event)
    {
        this.resultText.setVisible(true);
        this.answerTextArea.setVisible(false);
        this.resultText.setText("Ты нажал на кнопку B");
    }

    public void variantCSelected(ActionEvent event)
    {
        this.resultText.setVisible(true);
        this.answerTextArea.setVisible(false);
        this.resultText.setText("Ты нажал на кнопку C");
    }

    public void variantDSelected(ActionEvent event)
    {
    }

    public void AnswerSelected(ActionEvent event)
    {
        this.variantA_Button.setVisible(true);
        this.variantB_Button.setVisible(true);
        this.variantC_Button.setVisible(true);
        this.variantD_Button.setVisible(true);
        this.answerTextArea.setText("Вы отаетили на вопрос");
    }
}
