package loci.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("sample.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Parent root = loader.load();
        HBox cont = new HBox(root);
        cont.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setCenter(cont);
        Scene scene = new Scene(pane, 900, 600);

        primaryStage.setTitle("LOCI");
        primaryStage.setResizable(false); // закрепить/открепить изменения размера окна пользователем
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
