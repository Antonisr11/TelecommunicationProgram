package lab.unipi.gui.JavaFXLab;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import java.util.ArrayList;
import java.util.Optional;

public class Client {

    /* Fields for Client object */
    private String ID;
    private String AFM;
    private String name;
    private String category;
    private String address;
    private String phone;
    private String email;
    private static ArrayList<Client> clients = new ArrayList<>(); //This array list holds all clients

    public Client(String ID, String AFM, String name, String category, String address, String phone, String email) {
        //Constructor
        this.ID = ID;
        this.AFM = AFM;
        this.name = name;
        this.category = category;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    static TableView<Client> table;
    static TextField textField_ID;
    static Label label_ID;
    static TextField textField_AFM;
    static Label label_AFM;
    static TextField textField_name;
    static Label label_name;
    static ComboBox<String> comboBox_category;
    static Label label_category;
    static TextField textField_address;
    static Label label_address;
    static TextField textField_phone;
    static Label label_phone;
    static TextField textField_email;
    static Label label_email;
    static Button button_submit;
    static Button button_return;
    static Button button_update;
    static Button button_delete;
    static Button button_insert;
    static Button button_search_by_ID;
    static Button button_search_by_AFM;

    public static void C_Control_Clients_GUI(boolean show_all, Client element_from_update){

        VBox pane_0_0 = new VBox();//This pane will contain all labels in a vertical box
        label_ID = new Label();
        label_ID.setText("ID: ");
        label_ID.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_AFM = new Label();
        label_AFM.setText("AFM: ");
        label_AFM.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_name = new Label();
        label_name.setText("Name: ");
        label_name.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_category = new Label();
        label_category.setText("Category: ");
        label_category.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_address = new Label();
        label_address.setText("Address: ");
        label_address.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_phone = new Label();
        label_phone.setText("Phone: ");
        label_phone.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_email = new Label();
        label_email.setText("E-mail: ");
        label_email.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        pane_0_0.getChildren().addAll(label_ID,label_AFM,label_name,label_category,label_address,label_phone,label_email);
        pane_0_0.setSpacing(27);//27 pixels will be from one label to another
        pane_0_0.setPadding(new Insets(8, 0, 0, 0));//8 pixels margin from top

        VBox pane_0_1 = new VBox();//This pane will contain all text fields
        textField_ID = new TextField();
        textField_ID.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_AFM = new TextField();
        textField_AFM.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_name = new TextField();
        textField_name.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        comboBox_category = new ComboBox<>();
        ObservableList<String> list = comboBox_category.getItems();
        list.add("Individual");
        list.add("Student");
        list.add("Professional");

        textField_address = new TextField();
        textField_address.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_phone = new TextField();
        textField_phone.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_email = new TextField();
        textField_email.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        if (element_from_update != null){
            //If user clicked update, we should fill all text fields with element's attributes
            textField_ID.setText(element_from_update.getID());
            textField_ID.setDisable(true); //User cannot change ID
            textField_AFM.setText(element_from_update.getAFM());
            textField_AFM.setDisable(true); //User cannot change AFM
            textField_name.setText(element_from_update.getName());
            comboBox_category.setPromptText(element_from_update.getCategory());
            textField_address.setText(element_from_update.getAddress());
            textField_phone.setText(element_from_update.getPhone());
            textField_email.setText(element_from_update.getEmail());
        }
        else {
            textField_ID.setPromptText("ID here");
            textField_AFM.setPromptText("AFM here");
            textField_name.setPromptText("Full Name here");
            comboBox_category.setValue("Individual");
            textField_address.setPromptText("Address");
            textField_phone.setPromptText("Phone here");
            textField_email.setPromptText("E-mail here");
        }

        pane_0_1.getChildren().addAll(textField_ID,textField_AFM,textField_name,comboBox_category,textField_address,textField_phone,textField_email);
        pane_0_1.setSpacing(20);

        HBox pane_0 = new HBox();//This pane will contain labels with text boxes
        pane_0.getChildren().addAll(pane_0_0,pane_0_1);
        pane_0.setAlignment(Pos.TOP_CENTER);

        VBox pane_1 = new VBox(); //This pane will contain button submit. We use this extra pane in order to put button in middle
        button_submit = new Button();
        button_submit.setText("Submit");

        if (element_from_update != null ){
            button_submit.setOnAction(e -> update()); //We are on update so submit button will call update function
        }
        else {
            button_submit.setOnAction(e -> insert());//We are on insert so submit button will call insert function
        }

        button_submit.setPrefWidth(100);
        button_submit.setPrefHeight(50);
        button_submit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        pane_1.getChildren().add(button_submit);
        pane_1.setAlignment(Pos.CENTER);//All elements (in this case: submit button will be at middle)
        pane_1.setPadding(new Insets(30, 0, 0, 0));

        VBox pane_2 = new VBox();// This pane will contain labels, text boxes and button
        pane_2.getChildren().addAll(pane_0,pane_1);
        pane_2.setPadding(new Insets(0, 0, 0, 10));// 10 pixels margin from left

        HBox pane_3 = new HBox();//This pane will contain table will elements

        if (show_all){
            //If user clicks search, we have to show them a sublist of items. In any other case we will show them all elements
            table = convert_arraylist_to_table(get_all_elements());
        }

        table.setPrefWidth(700);
        table.setMaxHeight(480);
        pane_3.getChildren().add(table);
        pane_3.setAlignment(Pos.TOP_LEFT); // Table will go to to left corner
        pane_3.setSpacing(500);

        HBox pane_4 = new HBox();//This pane contains table, labels, text boxes and submit button
        pane_4.getChildren().addAll(pane_3, pane_2);

        HBox pane_5 = new HBox();//This pane contains all buttons (update, delete, search ect)
        button_return = new Button();
        button_update = new Button();
        button_delete = new Button();
        button_insert = new Button();
        button_search_by_ID = new Button();
        button_search_by_AFM = new Button();
        button_return.setText("Return");
        button_update.setText("Update");
        button_delete.setText("Delete");
        button_insert.setText("Insert");
        button_search_by_ID.setText("Search by ID");
        button_search_by_AFM.setText("Search by AFM");
        button_return.setOnAction(e -> Main.main_menu_GUI());//Return action will call main menu
        button_update.setOnAction(e -> {if (table.getSelectionModel().getSelectedItem() != null) {C_Control_Clients_GUI(true,table.getSelectionModel().getSelectedItem());} else {Main.inform_user("Error","Please select an element before you hit update");} });//If user clicks update and haven't choose an element an information box will appear
        button_delete.setOnAction(e -> {if (table.getSelectionModel().getSelectedItem() != null) {
            delete(table.getSelectionModel().getSelectedItem().getID(),table.getSelectionModel().getSelectedItem().getAFM());} else {Main.inform_user("Error","Please select an element before you hit delete");} });//If user clicks delete and haven't choose an element an information box will appear
        button_insert.setOnAction(e -> C_Control_Clients_GUI(true,null));
        button_search_by_ID.setOnAction(e -> search(false));
        button_search_by_AFM.setOnAction(e -> search(true));
        button_return.setPrefWidth(100);
        button_return.setPrefHeight(50);
        button_update.setPrefWidth(100);
        button_update.setPrefHeight(50);
        button_delete.setPrefWidth(100);
        button_delete.setPrefHeight(50);
        button_insert.setPrefWidth(100);
        button_insert.setPrefHeight(50);
        button_search_by_ID.setPrefWidth(150);
        button_search_by_ID.setPrefHeight(50);
        button_search_by_AFM.setPrefWidth(150);
        button_search_by_AFM.setPrefHeight(50);

        if (element_from_update != null){
            //User clicked update so we won't let him click this button again
            button_update.setDisable(true);
        }
        else {
            //User clicked insert so we won't let him click this button again
            button_insert.setDisable(true);
        }

        pane_5.getChildren().addAll(button_return, button_update, button_delete, button_insert, button_search_by_ID, button_search_by_AFM);
        pane_5.setAlignment(Pos.CENTER);
        pane_5.setPadding(new Insets(30, 0, 0, 0));
        pane_5.setSpacing(30);

        VBox main_pane = new VBox();//Adding all panes in a main pane
        main_pane.getChildren().addAll(pane_4, pane_5);
        main_pane.setPadding(new Insets(15, 0, 0, 10));

        //Center window at screen
        Main.stage.setX((Screen.getPrimary().getVisualBounds().getWidth()-950)/2);
        Main.stage.setY((Screen.getPrimary().getVisualBounds().getHeight()-550)/2);

        Main.stage.setScene(new Scene(main_pane)); //Create Scene
        Main.stage.setTitle("Control Clients"); //Window title
        Main.stage.setResizable(false); //Disable resizing from user
        Main.stage.setWidth(1100); //Set width
        Main.stage.setHeight(550); //Set Height
        Main.stage.show();
    }

    private static TableView<Client> convert_arraylist_to_table(ArrayList<Client> clients){

        TableColumn<Client, String> column_ID = new TableColumn<>("ID");
        column_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Client, String> column_AFM = new TableColumn<>("AFM");
        column_AFM.setCellValueFactory(new PropertyValueFactory<>("AFM"));

        TableColumn<Client, String> column_name = new TableColumn<>("Name");
        column_name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Client, String> column_category = new TableColumn<>("Category");
        column_category.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Client, String> column_address = new TableColumn<>("Address");
        column_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Client, String> column_phone = new TableColumn<>("Phone");
        column_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Client, String> column_email = new TableColumn<>("E-mail");
        column_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        table = new TableView<>();

        table.getColumns().add(column_ID);
        table.getColumns().add(column_AFM);
        table.getColumns().add(column_name);
        table.getColumns().add(column_category);
        table.getColumns().add(column_address);
        table.getColumns().add(column_phone);
        table.getColumns().add(column_email);

        for (Client client: clients){
            table.getItems().add(client);//Adding all elements from argument in table
        }

        return table;
    }

    private static boolean has_errors(){//This function is used in order to check text boxes from user. If this function returns false it means that all fields are 100% right
        String errors = ""; //This variable will contain all user's errors

        try{
            String ID = textField_ID.getText();
            if (ID.equals("")){
                throw new Exception();
            }
        }
        catch (Exception e){
            errors+="ID is empty\n";
        }

        try{
            String AFM = textField_AFM.getText();
            if (AFM.equals("")){
                throw new Exception();
            }
            else if (!AFM.matches("[0-9]+")){
                errors+="AFM must have only numbers\n";
            }
        }
        catch (Exception e){
            errors+="AFM is empty\n";
        }

        try{
            String name = textField_name.getText();
            if (name.equals("")){
                throw new Exception();
            }
        }
        catch (Exception e){
            errors+="Name is empty\n";
        }

        try{
            String address = textField_address.getText();
            if (address.equals("")){
                throw new Exception();
            }
        }
        catch (Exception e){
            errors+="Address is empty\n";
        }

        try{
            String phone = textField_phone.getText();
            if (phone.equals("")){
                throw new Exception();
            }
            else if (!phone.matches("[0-9]+")){
                errors+="Phone must have only numbers\n";
            }
            else if (phone.length()!=10){
                errors+="Phone must be 10-numbers long\n";
            }
        }
        catch (Exception e){
            errors+="Phone is empty\n";
        }

        try{
            String email = textField_email.getText();
            if (email.equals("")){
                throw new Exception();
            }
            else if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))")){
                errors+="Email is not in right form\n";
            }
        }
        catch (Exception e){
            errors+="Email is empty\n";
        }

        if (errors.equals("")){
            return false;
        }
        else {
            Main.inform_user("Error!","Please fix:\n"+errors);
            return true;
        }

    }

    private static void insert(){
        //This function insert a new element if there are no mistakes in fields

        if (has_errors()){
            return;
        }

        for (Client client : Client.getClients()){
            if (client.getID().equals(textField_ID.getText()) && client.getAFM().equals(textField_AFM.getText())){
                Main.inform_user("Error!","There is a client with this ID and AFM therefore insert failed!");
                return;
            }
        }
        Client.getClients().add(new Client(textField_ID.getText(), textField_AFM.getText(), textField_name.getText(), comboBox_category.getValue(), textField_address.getText(), textField_phone.getText(), textField_email.getText()));
        Main.inform_user("Success!","Successful insert!");
        C_Control_Clients_GUI(true,null);
    }

    private static void update(){
        //This function updates a new element if there are no mistakes in fields

        if (has_errors()){
            return;
        }

        for (Client client : Client.getClients()){
            if (client.getID().equals(textField_ID.getText()) && client.getAFM().equals(textField_AFM.getText())){
                client.setName(textField_name.getText());
                client.setCategory(comboBox_category.getValue());
                client.setAddress(textField_address.getText());
                client.setPhone(textField_phone.getText());
                client.setEmail(textField_email.getText());
                Main.inform_user("Success!","Successful update!");
                break;
            }
        }

        C_Control_Clients_GUI(true,null);
    }

    private static void delete(String ID, String AFM){

        for (Contract contract : Contract.getContracts()){
            //We have to check if there is any Contract with this AFM
            if (contract.getAFM().equals(AFM)){
                Main.inform_user("Error!","Cannot delete this client because there is at least one contract with them");
                return;
            }
        }
        //If we reach here, there isn't any Contract with this AFM
        for (int i=0; i<Client.getClients().size(); i++){
            if (Client.getClients().get(i).getAFM().equals(AFM) && Client.getClients().get(i).getID().equals(ID)){
                Client.getClients().remove(i);
                Main.inform_user("Success!","Successful delete!");
                break;
            }
        }
        C_Control_Clients_GUI(true,null);
    }

    private static void search(boolean is_by_AFM){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search dialog");
        dialog.setHeaderText(null);
        dialog.setContentText("Search by "+( is_by_AFM ? "AFM" : "ID" )+": ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equals("")){
            ArrayList<Client> temp_clients = new ArrayList<>();
            for (Client client: Client.getClients()){
                if ((is_by_AFM && client.getAFM().equals(result.get())) || (!is_by_AFM && client.getID().equals(result.get()))){
                    temp_clients.add(client); //we make the table contain sub-element of companies will given name
                }
            }
            table = convert_arraylist_to_table(temp_clients);
            C_Control_Clients_GUI(false,null);
        }
    }

    private static ArrayList<Client> get_all_elements(){
        //This function will return all elements
        return Client.getClients();
    }

    /* Getters & Setters */

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAFM() {
        return AFM;
    }

    public void setAFM(String AFM) {
        this.AFM = AFM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static void setClients(ArrayList<Client> clients) {
        Client.clients = clients;
    }
}
