package lab.unipi.gui.JavaFXLab;

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

public class TelecommunicationCompany {

    /* Fields for TelecommunicationCompany object */
    private final int unique_id;
    private String company_name;
    private String phone;
    private String email;
    private static int count = 0;
    private static ArrayList<TelecommunicationCompany> companies = new ArrayList<>(); //This array list holds all companies

    public TelecommunicationCompany(String company_name, String phone, String email) {
        //Constructor
        this.unique_id = ++count;
        this.company_name = company_name;
        this.phone = phone;
        this.email = email;
    }

    /* Fields for GUI */
    static TableView<TelecommunicationCompany> table;
    static TextField textField_unique_id;
    static Label label_unique_id;
    static TextField textField_company_name;
    static Label label_company_name;
    static TextField textField_phone;
    static Label label_phone;
    static TextField textField_email;
    static Label label_email;
    static Button button_submit;
    static Button button_return;
    static Button button_update;
    static Button button_delete;
    static Button button_insert;
    static Button button_search;

    public static void A_Control_Companies_GUI(boolean show_all, TelecommunicationCompany element_from_update){

        VBox pane_0_0 = new VBox(); //This pane will contain all labels in a vertical box
        label_unique_id = new Label();
        label_unique_id.setText("ID: ");
        label_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        label_company_name = new Label();
        label_company_name.setText("Company name: ");
        label_company_name.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        label_phone = new Label();
        label_phone.setText("Phone: ");
        label_phone.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        label_email = new Label();
        label_email.setText("E-mail: ");
        label_email.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 18));
        pane_0_0.getChildren().addAll(label_unique_id, label_company_name,label_phone,label_email);
        pane_0_0.setSpacing(35); //35 pixels will be from one label to another
        pane_0_0.setPadding(new Insets(4, 0, 0, 0)); //4 pixels margin from top

        VBox pane_0_1 = new VBox(); //This pane will contain all text fields
        textField_unique_id = new TextField();
        textField_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_company_name = new TextField();
        textField_company_name.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_phone = new TextField();
        textField_phone.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_email = new TextField();
        textField_email.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_unique_id.setDisable(true); //User cannot change ID

        if (element_from_update != null){
            //if user clicked update, we should fill all text fields with element's attributes
            textField_unique_id.setText(String.valueOf(element_from_update.getUnique_id()));
            textField_company_name.setText(element_from_update.getCompany_name());
            textField_company_name.setDisable(true); //User cannot change company name
            textField_phone.setText(element_from_update.getPhone());
            textField_email.setText(element_from_update.getEmail());
        }
        else {
            textField_unique_id.setText(String.valueOf(TelecommunicationCompany.getCount()+1));
            textField_company_name.setPromptText("Company name here");
            textField_phone.setPromptText("Phone here");
            textField_email.setPromptText("E-mail here");
        }


        pane_0_1.getChildren().addAll(textField_unique_id,textField_company_name,textField_phone,textField_email);
        pane_0_1.setSpacing(30);

        HBox pane_0 = new HBox(); //This pane will contain labels with text boxes
        pane_0.getChildren().addAll(pane_0_0,pane_0_1);
        pane_0.setAlignment(Pos.TOP_CENTER);

        VBox pane_1 = new VBox(); //This pane will contain button submit. We use this extra pane in order to put button in middle
        button_submit = new Button();
        button_submit.setText("Submit");

        if (element_from_update != null ){
            button_submit.setOnAction(e -> update()); //We are on update so submit button will call update function
        }
        else {
            button_submit.setOnAction(e -> insert()); //We are on insert so submit button will call insert function
        }

        button_submit.setPrefWidth(100);
        button_submit.setPrefHeight(50);
        button_submit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        pane_1.getChildren().add(button_submit);
        pane_1.setAlignment(Pos.CENTER); //All elements (in this case: submit button will be at middle)
        pane_1.setPadding(new Insets(30, 0, 0, 0));

        VBox pane_2 = new VBox(); // This pane will contain labels, text boxes and button
        pane_2.getChildren().addAll(pane_0,pane_1);
        pane_2.setPadding(new Insets(0, 0, 0, 10)); // 10 pixels margin from left

        HBox pane_3 = new HBox(); //This pane will contain table will elements

        if (show_all){
            //If user clicks search, we have to show them a sublist of items. In any other case we will show them all elements
            table = convert_arraylist_to_table(get_all_elements());
        }

        table.setPrefWidth(550);
        table.setMaxHeight(480);
        pane_3.getChildren().add(table);
        pane_3.setAlignment(Pos.TOP_LEFT); // Table will go to to left corner

        HBox pane_4 = new HBox(); //This pane contains table, labels, text boxes and submit button
        pane_4.getChildren().addAll(pane_3, pane_2);

        HBox pane_5 = new HBox(); //This pane contains all buttons (update, delete, search ect)
        button_return = new Button();
        button_update = new Button();
        button_delete = new Button();
        button_insert = new Button();
        button_search = new Button();
        button_return.setText("Return");
        button_update.setText("Update");
        button_delete.setText("Delete");
        button_insert.setText("Insert");
        button_search.setText("Search by name");
        button_return.setOnAction(e -> Main.main_menu_GUI()); //Return action will call main menu
        button_update.setOnAction(e -> {if (table.getSelectionModel().getSelectedItem() != null) {A_Control_Companies_GUI(true,table.getSelectionModel().getSelectedItem());} else {Main.inform_user("Error","Please select an element before you hit update");} }); //If user clicks update and haven't choose an element an information box will appear
        button_delete.setOnAction(e -> {if (table.getSelectionModel().getSelectedItem() != null) {delete(table.getSelectionModel().getSelectedItem().getUnique_id());} else {Main.inform_user("Error","Please select an element before you hit delete");} }); //If user clicks delete and haven't choose an element an information box will appear
        button_insert.setOnAction(e -> A_Control_Companies_GUI(true,null));
        button_search.setOnAction(e -> search_by_name());
        button_return.setPrefWidth(100);
        button_return.setPrefHeight(50);
        button_update.setPrefWidth(100);
        button_update.setPrefHeight(50);
        button_delete.setPrefWidth(100);
        button_delete.setPrefHeight(50);
        button_insert.setPrefWidth(100);
        button_insert.setPrefHeight(50);
        button_search.setPrefWidth(150);
        button_search.setPrefHeight(50);

        if (element_from_update != null){
            //User clicked update so we won't let him click this button again
            button_update.setDisable(true);
        }
        else {
            //User clicked insert so we won't let him click this button again
            button_insert.setDisable(true);
        }

        pane_5.getChildren().addAll(button_return, button_update, button_delete, button_insert, button_search);
        pane_5.setAlignment(Pos.CENTER);
        pane_5.setPadding(new Insets(30, 0, 0, 0));
        pane_5.setSpacing(30);

        VBox main_pane = new VBox();//Adding all panes in a main pane
        main_pane.getChildren().addAll(pane_4, pane_5);
        main_pane.setPadding(new Insets(15, 0, 0, 10));

        //Center window at screen
        Main.stage.setX((Screen.getPrimary().getVisualBounds().getWidth()-1000)/2);
        Main.stage.setY((Screen.getPrimary().getVisualBounds().getHeight()-550)/2);

        Main.stage.setScene(new Scene(main_pane)); //Create Scene
        Main.stage.setTitle("Control Companies"); //Window title
        Main.stage.setResizable(false); //Disable resizing from user
        Main.stage.setWidth(950); //Set width
        Main.stage.setHeight(550); //Set Height
        Main.stage.show();
    }

    private static TableView<TelecommunicationCompany> convert_arraylist_to_table(ArrayList<TelecommunicationCompany> telecommunicationCompanies){

        TableColumn<TelecommunicationCompany, Integer> column_id = new TableColumn<>("ID");
        column_id.setCellValueFactory(new PropertyValueFactory<>("unique_id"));

        TableColumn<TelecommunicationCompany, String> column_company_name = new TableColumn<>("Name");
        column_company_name.setCellValueFactory(new PropertyValueFactory<>("company_name"));

        TableColumn<TelecommunicationCompany, String> column_phone = new TableColumn<>("Phone");
        column_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<TelecommunicationCompany, String> column_email = new TableColumn<>("E-mail");
        column_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        table = new TableView<>();

        table.getColumns().add(column_id);
        table.getColumns().add(column_company_name);
        table.getColumns().add(column_phone);
        table.getColumns().add(column_email);

        for (TelecommunicationCompany telecommunicationCompany: telecommunicationCompanies){
            table.getItems().add(telecommunicationCompany); //Adding all elements from argument in table
        }

        return table;
    }

    private static boolean has_errors(){ //This function is used in order to check text boxes from user. If this function returns false it means that all fields are 100% right
        String errors = ""; //This variable will contain all user's errors

        try{
            String company_name = textField_company_name.getText();
            if (company_name.equals("")){
                throw new Exception();
            }
        }
        catch (Exception e){
            errors+="Company name is empty\n";
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
                //we check email be regex made by https://www.emailregex.com
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

        if (has_errors()) {
            return;
        }

        TelecommunicationCompany.getCompanies().add(new TelecommunicationCompany(textField_company_name.getText(), textField_phone.getText(), textField_email.getText()));
        Main.inform_user("Success!","Successful insert!");
        A_Control_Companies_GUI(true,null);

    }

    private static void update(){
        //This function updates a new element if there are no mistakes in fields

        if (has_errors()) {
            return;
        }

        int unique_id = Integer.parseInt(textField_unique_id.getText());

        for (TelecommunicationCompany company : TelecommunicationCompany.getCompanies()){
            if (company.getUnique_id() == unique_id){
                company.setCompany_name(textField_company_name.getText());
                company.setPhone(textField_phone.getText());
                company.setEmail(textField_email.getText());
                Main.inform_user("Success!","Successful update!");
                break;
            }
        }
        A_Control_Companies_GUI(true,null);
    }

    private static void delete(int unique_id){

        outer: for (int i=0; i<TelecommunicationCompany.getCompanies().size(); i++){
            if (TelecommunicationCompany.getCompanies().get(i).getUnique_id() == unique_id ){
                //We have to check if there is a Plan with this company
                for (Plan plan : Plan.getPlans()){
                    if (plan.getCompany().getUnique_id() == unique_id){
                        Main.inform_user("Error!","There is at least one plan with this company therefore deletion was impossible.");
                        break outer;
                    }
                }
                //If we reach here, there isn't any Plan with this company
                TelecommunicationCompany.getCompanies().remove(i);
                Main.inform_user("Success!","Successful delete!");
                break;
            }
        }
        A_Control_Companies_GUI(true,null);
    }

    private static void search_by_name(){

        ArrayList <TelecommunicationCompany> temp_array = new ArrayList<>();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search dialog");
        dialog.setHeaderText(null);
        dialog.setContentText("Search in company names:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equals("")){
            for (TelecommunicationCompany company : TelecommunicationCompany.getCompanies()){
                if (company.getCompany_name().equalsIgnoreCase(result.get())){
                    temp_array.add(company);
                }
            }
            convert_arraylist_to_table(temp_array); //we make the table contain sub-element of companies will given name
            A_Control_Companies_GUI(false,null);
        }
        else {
            //User clicked X or cancel
            A_Control_Companies_GUI(true,null);
        }
    }

    private static ArrayList<TelecommunicationCompany> get_all_elements(){
        //This function will return all elements
        return TelecommunicationCompany.getCompanies();
    }

    /* Getters & Setters */

    public int getUnique_id() {
        return unique_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        TelecommunicationCompany.count = count;
    }

    public static ArrayList<TelecommunicationCompany> getCompanies() {
        return companies;
    }

    public static void setCompanies(ArrayList<TelecommunicationCompany> companies) {
        TelecommunicationCompany.companies = companies;
    }
}
