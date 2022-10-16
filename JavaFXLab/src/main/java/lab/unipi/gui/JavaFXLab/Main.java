package lab.unipi.gui.JavaFXLab;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;

public class Main extends Application {

    static Button button_to_control_A = new Button();
    static Button button_to_control_B = new Button();
    static Button button_to_control_C = new Button();
    static Button button_to_control_D = new Button();
    static Stage stage; //This is the main stage

    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        main_menu_GUI();
    }

    public static void main_menu_GUI(){
        //This is main menu with 4 buttons

        button_to_control_A.setText("Telecommunication Company Controller");
        button_to_control_B.setText("Plan's Controller");
        button_to_control_C.setText("Client's Controller");
        button_to_control_D.setText("Contract's Controller");

        button_to_control_A.setOnAction(e -> TelecommunicationCompany.A_Control_Companies_GUI(true,null));
        button_to_control_B.setOnAction(e -> Plan.B_Control_Plans_GUI(true,null));
        button_to_control_C.setOnAction(e -> Client.C_Control_Clients_GUI(true,null));
        button_to_control_D.setOnAction(e -> Contract.D_Control_Contracts_GUI(true));

        //Creating pane to add buttons
        HBox pane = new HBox();
        pane.setSpacing(50); //There will be 50 pixel from one button to an other
        pane.getChildren().addAll(button_to_control_A, button_to_control_B, button_to_control_C, button_to_control_D); //Adding all buttons at pane
        pane.setAlignment(Pos.CENTER); //Buttons will go in center of window

        //Center window at screen
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth()-1000)/2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight()-300)/2);

        stage.setScene(new Scene(pane)); //Create Scene
        stage.setTitle("Main Menu"); //Window title
        stage.setResizable(false); //Disable resizing from user
        stage.setWidth(1000); //Set width
        stage.setHeight(300); //Set Height

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void inform_user(String title, String message){
        //This function is used to inform user about a thing
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean ask_user(String title, String message){
        //This function is used to ask a user for confirmation. Returns true if user clicks OK, false if user clicks Cancel
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
}