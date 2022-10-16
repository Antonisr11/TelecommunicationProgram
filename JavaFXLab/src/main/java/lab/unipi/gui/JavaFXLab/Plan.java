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
import java.util.List;
import java.util.Optional;

public class Plan {

    /* Fields for Plan object */
    private int unique_id;
    private TelecommunicationCompany company;
    private int minutes;
    private double price;
    private static int count = 0; //This is used to generate ID
    private static ArrayList<Plan> plans = new ArrayList<>(); //This array list holds all plans

    public Plan(TelecommunicationCompany company, int minutes, double price){
        //Constructor
        this.unique_id = ++count; //ID calculates automatically
        this.company = company;
        this.minutes = minutes;
        this.price = price;
    }

    static public class plan_for_tableview {
        //This class is created in order to show this element in table view (we cannot show Plan because it has TelecommunicationCompany field)
        private int unique_id;
        private String company_name;
        private int minutes;
        private double price;
        private String power_of_line;
        private String type_of_line;
        private String free_sms;
        private String free_GB;

        public plan_for_tableview(Plan plan){
            //Constructor
            this.unique_id = plan.getUnique_id();
            this.company_name = plan.getCompany().getCompany_name();
            this.minutes = plan.getMinutes();
            this.price = plan.getPrice();

            if (plan instanceof MobilePlan){
                this.power_of_line = "-";
                this.type_of_line = "-";
                this.free_sms = String.valueOf(((MobilePlan) plan).getFree_sms());
                this.free_GB = String.valueOf(((MobilePlan) plan).getFree_GB());
            }
            else if (plan instanceof LandlinePlan){
                this.power_of_line = ((LandlinePlan) plan).getPower_of_line();
                this.type_of_line = ((LandlinePlan) plan).getType_of_line();
                this.free_sms = "-";
                this.free_GB = "-";
            }
        }

        public Plan toPlan() {
            //This function converts this object to Plan
            TelecommunicationCompany company = null;
            for (TelecommunicationCompany temp_company : TelecommunicationCompany.getCompanies()) {
                if (this.company_name.equals(temp_company.getCompany_name())) {
                    company = temp_company;
                    break;
                }
            }

            Plan toReturn;
            if (this.free_GB.equals("-")) {
                //Plan is Landline
                toReturn = new LandlinePlan(company, this.minutes, this.price, this.power_of_line, this.type_of_line);
            } else {
                //Plan is Mobile
                toReturn = new MobilePlan(company, this.minutes, this.price, Integer.parseInt(this.free_sms), Integer.parseInt(this.free_GB));
            }
            toReturn.setUnique_id(this.unique_id);
            Plan.setCount(Plan.getCount()-1); //Because we create new Plan, plan count will be
            return toReturn;
        }

        /* Getters & Setters */

        public int getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(int unique_id) {
            this.unique_id = unique_id;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getPower_of_line() {
            return power_of_line;
        }

        public void setPower_of_line(String power_of_line) {
            this.power_of_line = power_of_line;
        }

        public String getType_of_line() {
            return type_of_line;
        }

        public void setType_of_line(String type_of_line) {
            this.type_of_line = type_of_line;
        }

        public String getFree_sms() {
            return free_sms;
        }

        public void setFree_sms(String free_sms) {
            this.free_sms = free_sms;
        }

        public String getFree_GB() {
            return free_GB;
        }

        public void setFree_GB(String free_GB) {
            this.free_GB = free_GB;
        }
    }

    /* Fields for GUI */
    static TableView<Plan.plan_for_tableview> table;
    static TextField textField_unique_id;
    static Label label_unique_id;
    static TextField textField_company_id;
    static Label label_company_id;
    static TextField textField_minutes;
    static Label label_minutes;
    static TextField textField_price;
    static Label label_price;
    static TextField textField_field_1;
    static Label label_type;
    static RadioButton radio_button_mobile;
    static RadioButton radio_button_landline;
    static Label label_field_1;
    static TextField textField_field_2;
    static Label label_field_2;
    static Button button_submit;
    static Button button_return;
    static Button button_update;
    static Button button_delete;
    static Button button_insert;
    static Button button_search_by_type;
    static Button button_search_by_company;

    public static void B_Control_Plans_GUI(boolean show_all, Plan element_from_update) {

        VBox pane_0_0 = new VBox();//This pane will contain all labels in a vertical box
        label_unique_id = new Label();
        label_unique_id.setText("ID: ");
        label_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_company_id = new Label();
        label_company_id.setText("Company ID: ");
        label_company_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_minutes = new Label();
        label_minutes.setText("Minutes: ");
        label_minutes.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_price = new Label();
        label_price.setText("Price: ");
        label_price.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_type = new Label();
        label_type.setText("Type: ");
        label_type.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_field_1 = new Label();
        label_field_1.setText("SMS/Line Power: ");
        label_field_1.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_field_2 = new Label();
        label_field_2.setText("GB/Type: ");
        label_field_2.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        pane_0_0.getChildren().addAll(label_unique_id, label_company_id, label_minutes, label_price, label_type, label_field_1, label_field_2);
        pane_0_0.setSpacing(27);//27 pixels will be from one label to another
        pane_0_0.setPadding(new Insets(8, 0, 0, 0)); //8 pixels margin from top

        VBox pane_0_1 = new VBox();//This pane will contain all text fields
        textField_unique_id = new TextField();
        textField_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_company_id = new TextField();
        textField_company_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_minutes = new TextField();
        textField_minutes.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_price = new TextField();
        textField_price.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        final ToggleGroup radio_buttons_group = new ToggleGroup();
        radio_button_mobile = new RadioButton("Mobile");
        radio_button_mobile.setToggleGroup(radio_buttons_group);
        radio_button_landline = new RadioButton("Landline");
        radio_button_landline.setToggleGroup(radio_buttons_group);

        HBox pane_radio_buttons = new HBox(); //This pane contains radio buttons
        pane_radio_buttons.getChildren().addAll(radio_button_mobile, radio_button_landline);
        pane_radio_buttons.setSpacing(20);
        pane_radio_buttons.setAlignment(Pos.CENTER);

        textField_field_1 = new TextField();
        textField_field_1.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_field_2 = new TextField();
        textField_field_2.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_unique_id.setDisable(true);

        if (element_from_update != null) {
            //If user clicked update, we should fill all text fields with element's attributes
            textField_unique_id.setText(String.valueOf(element_from_update.getUnique_id()));
            textField_company_id.setText(String.valueOf(element_from_update.getCompany().getUnique_id()));
            textField_company_id.setDisable(true); //User cannot change company ID
            textField_minutes.setText(String.valueOf(element_from_update.getMinutes()));
            textField_price.setText(String.valueOf(element_from_update.getPrice()));

            if (element_from_update instanceof MobilePlan) {
                textField_field_1.setText(String.valueOf(((MobilePlan) element_from_update).getFree_sms()));
                textField_field_2.setText(String.valueOf(((MobilePlan) element_from_update).getFree_GB()));
                radio_button_mobile.setSelected(true);

            } else if (element_from_update instanceof LandlinePlan) {
                textField_field_1.setText(String.valueOf(((LandlinePlan) element_from_update).getPower_of_line()));
                textField_field_2.setText(String.valueOf(((LandlinePlan) element_from_update).getType_of_line()));
                radio_button_landline.setSelected(true);
            }
        } else {
            textField_unique_id.setText(String.valueOf(Plan.getCount() + 1));
            textField_company_id.setPromptText("Write company ID here");
            textField_minutes.setPromptText("Write minutes here");
            textField_price.setPromptText("Write price here");
            radio_button_mobile.setSelected(true);
            textField_field_1.setPromptText("SMS for mobile");
            textField_field_2.setPromptText("Type for landline");
        }

        pane_0_1.getChildren().addAll(textField_unique_id, textField_company_id, textField_minutes, textField_price, pane_radio_buttons, textField_field_1, textField_field_2);
        pane_0_1.setSpacing(20);

        HBox pane_0 = new HBox(); //This pane will contain labels with text boxes
        pane_0.getChildren().addAll(pane_0_0, pane_0_1);
        pane_0.setAlignment(Pos.TOP_CENTER);

        VBox pane_1 = new VBox();//This pane will contain button submit. We use this extra pane in order to put button in middle
        button_submit = new Button();
        button_submit.setText("Submit");

        if (element_from_update != null) {
            button_submit.setOnAction(e -> update()); //We are on update so submit button will call update function
        } else {
            button_submit.setOnAction(e -> insert());//We are on insert so submit button will call insert function
        }

        button_submit.setPrefWidth(100);
        button_submit.setPrefHeight(50);
        button_submit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        pane_1.getChildren().add(button_submit);
        pane_1.setAlignment(Pos.CENTER); //All elements (in this case: submit button will be at middle)
        pane_1.setPadding(new Insets(25, 0, 0, 0));

        VBox pane_2 = new VBox();// This pane will contain labels, text boxes and button
        pane_2.getChildren().addAll(pane_0, pane_1);
        pane_2.setPadding(new Insets(0, 0, 0, 10));// 10 pixels margin from left

        HBox pane_3 = new HBox();//This pane will contain table will elements

        if (show_all) {
            //If user clicks search, we have to show them a sublist of items. In any other case we will show them all elements
            table = convert_arraylist_to_table(get_all_elements());
        }

        table.setPrefWidth(600);
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
        button_search_by_type = new Button();
        button_search_by_company = new Button();
        button_return.setText("Return");
        button_update.setText("Update");
        button_delete.setText("Delete");
        button_insert.setText("Insert");
        button_search_by_type.setText("Search by type");
        button_search_by_company.setText("Search by company");
        button_return.setOnAction(e -> Main.main_menu_GUI()); //Return action will call main menu
        button_update.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                //If user clicks update and haven't choose an element an information box will appear
                B_Control_Plans_GUI(true, table.getSelectionModel().getSelectedItem().toPlan());
            } else {
                Main.inform_user("Error", "Please select an element before you hit update");
            }
        });
        button_delete.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                //If user clicks delete and haven't choose an element an information box will appear
                delete(table.getSelectionModel().getSelectedItem().getUnique_id());
            } else {
                Main.inform_user("Error", "Please select an element before you hit delete");
            }
        });
        button_insert.setOnAction(e -> B_Control_Plans_GUI(true, null));
        button_search_by_type.setOnAction(e -> search(true));
        button_search_by_company.setOnAction(e -> search(false));
        button_return.setPrefWidth(100);
        button_return.setPrefHeight(50);
        button_update.setPrefWidth(100);
        button_update.setPrefHeight(50);
        button_delete.setPrefWidth(100);
        button_delete.setPrefHeight(50);
        button_insert.setPrefWidth(100);
        button_insert.setPrefHeight(50);
        button_search_by_type.setPrefWidth(150);
        button_search_by_type.setPrefHeight(50);
        button_search_by_company.setPrefWidth(150);
        button_search_by_company.setPrefHeight(50);

        if (element_from_update != null) {
            //User clicked update so we won't let him click this button again
            button_update.setDisable(true);
        } else {
            //User clicked insert so we won't let him click this button again
            button_insert.setDisable(true);
        }

        pane_5.getChildren().addAll(button_return, button_update, button_delete, button_insert, button_search_by_type, button_search_by_company);
        pane_5.setAlignment(Pos.CENTER);
        pane_5.setPadding(new Insets(30, 0, 0, 0));
        pane_5.setSpacing(30);

        VBox main_pane = new VBox(); //Adding all panes in a main pane
        main_pane.getChildren().addAll(pane_4, pane_5);
        main_pane.setPadding(new Insets(15, 0, 0, 10));

        //Center window at screen
        Main.stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 1000) / 2);
        Main.stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 550) / 2);

        Main.stage.setScene(new Scene(main_pane)); //Create Scene
        Main.stage.setTitle("Control Plans"); //Window title
        Main.stage.setResizable(false); //Disable resizing from user
        Main.stage.setWidth(1000); //Set width
        Main.stage.setHeight(550); //Set Height
        Main.stage.show();

    }

    private static TableView<Plan.plan_for_tableview> convert_arraylist_to_table(ArrayList<Plan> plans) {

        //We convert all Plans objects to plan_for_tableview objects in order to show company name in table
        ArrayList<Plan.plan_for_tableview> plans_for_tableview_list = new ArrayList<>();
        for (Plan plan : plans) {
            plans_for_tableview_list.add(new Plan.plan_for_tableview(plan));
        }

        TableColumn<Plan.plan_for_tableview, Integer> column_id = new TableColumn<>("ID");
        column_id.setCellValueFactory(new PropertyValueFactory<>("unique_id"));
        column_id.setPrefWidth(45);

        TableColumn<Plan.plan_for_tableview, String> column_company_name = new TableColumn<>("Company Name");
        column_company_name.setCellValueFactory(new PropertyValueFactory<>("company_name"));

        TableColumn<Plan.plan_for_tableview, Integer> column_minutes = new TableColumn<>("Minutes");
        column_minutes.setCellValueFactory(new PropertyValueFactory<>("minutes"));

        TableColumn<Plan.plan_for_tableview, Double> column_price = new TableColumn<>("Price");
        column_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Plan.plan_for_tableview, String> column_power_of_line = new TableColumn<>("Line Power");
        column_power_of_line.setCellValueFactory(new PropertyValueFactory<>("power_of_line"));

        TableColumn<Plan.plan_for_tableview, String> column_type_of_line = new TableColumn<>("Line Type");
        column_type_of_line.setCellValueFactory(new PropertyValueFactory<>("type_of_line"));

        TableColumn<Plan.plan_for_tableview, String> column_free_sms = new TableColumn<>("SMS");
        column_free_sms.setCellValueFactory(new PropertyValueFactory<>("free_sms"));
        column_free_sms.setPrefWidth(45);

        TableColumn<Plan.plan_for_tableview, String> column_free_GB = new TableColumn<>("GB");
        column_free_GB.setCellValueFactory(new PropertyValueFactory<>("free_GB"));
        column_free_GB.setPrefWidth(45);

        table = new TableView<>();

        table.getColumns().add(column_id);
        table.getColumns().add(column_company_name);
        table.getColumns().add(column_minutes);
        table.getColumns().add(column_price);
        table.getColumns().add(column_power_of_line);
        table.getColumns().add(column_type_of_line);
        table.getColumns().add(column_free_sms);
        table.getColumns().add(column_free_GB);

        for (Plan.plan_for_tableview plan : plans_for_tableview_list) {
            table.getItems().add(plan); //Adding all elements from argument in table
        }
        return table;
    }

    private static boolean has_errors() {//This function is used in order to check text boxes from user. If this function returns false it means that all fields are 100% right
        String errors = ""; //This variable will contain all user's errors

        try {
            int company_id = Integer.parseInt(textField_company_id.getText());

            boolean exists = false;
            for (TelecommunicationCompany company : TelecommunicationCompany.getCompanies()) {
                if (company.getUnique_id() == company_id) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                errors += "There isn't any company with this ID\n";
            }
        } catch (Exception e) {
            errors += "Company ID is not a number\n";
        }

        try {
            int minutes = Integer.parseInt(textField_minutes.getText());
            if (minutes < 0) {
                errors += "Minutes must be at least 0\n";
            }
        } catch (Exception e) {
            errors += "Minutes is not a number\n";
        }

        try {
            double price = Double.parseDouble(textField_price.getText());

            if (price < 0) {
                errors += "Price must be at least 0\n";
            }
        } catch (Exception e) {
            errors += "Price is not double\n";
        }

        String field_1 = textField_field_1.getText();
        String field_2 = textField_field_2.getText();

        if (radio_button_mobile.isSelected()) {
            try {
                int number = Integer.parseInt(field_1);
                if (number < 0) {
                    errors += "SMS has to be at least 0\n";
                }
            } catch (Exception e) {
                errors += "SMS must be a number\n";
            }

            try {
                int number = Integer.parseInt(field_2);
                if (number < 0) {
                    errors += "GB has to be at least 0\n";
                }
            } catch (Exception e) {
                errors += "GB must be a number\n";
            }
        } else {
            if (field_1.equals("")) {
                errors += "Fill power of line field\n";
            }

            if (field_2.equals("")) {
                errors += "Fill type field\n";
            }
        }

        if (errors.equals("")) {
            return false;
        } else {
            Main.inform_user("Error!", "Please fix:\n" + errors);
            return true;
        }

    }

    private static void insert() {
        //This function insert a new element if there are no mistakes in fields

        if (has_errors()) {
            return;
        }

        TelecommunicationCompany company = null;

        for (TelecommunicationCompany temp_company : TelecommunicationCompany.getCompanies()) {
            if (temp_company.getUnique_id() == Integer.parseInt(textField_company_id.getText())) {
                company = temp_company;
                break;
            }
        }

        if (radio_button_mobile.isSelected()) {
            Plan.getPlans().add(new MobilePlan(company, Integer.parseInt(textField_minutes.getText()), Double.parseDouble(textField_price.getText()), Integer.parseInt(textField_field_1.getText()), Integer.parseInt(textField_field_2.getText())));
        } else {
            Plan.getPlans().add(new LandlinePlan(company, Integer.parseInt(textField_minutes.getText()), Double.parseDouble(textField_price.getText()), textField_field_1.getText(), textField_field_2.getText()));
        }

        Main.inform_user("Success!","Successful insert!");

        B_Control_Plans_GUI(true, null);
    }

    private static void update() {
        //This function updates a new element if there are no mistakes in fields

        if (has_errors()) {
            return;
        }

        TelecommunicationCompany company = null;
        for (TelecommunicationCompany temp_company : TelecommunicationCompany.getCompanies()) {
            if (temp_company.getUnique_id() == Integer.parseInt(textField_company_id.getText())) {
                company = temp_company;
                break;
            }
        }

        for (int i=0; i<Plan.getPlans().size(); i++){
            if (Plan.getPlans().get(i).getUnique_id() == Integer.parseInt(textField_unique_id.getText())){
                Plan.getPlans().remove(i);

                if (radio_button_mobile.isSelected()) {
                    Plan.getPlans().add(new MobilePlan(company, Integer.parseInt(textField_minutes.getText()), Double.parseDouble(textField_price.getText()), Integer.parseInt(textField_field_1.getText()), Integer.parseInt(textField_field_2.getText())));
                } else {
                    Plan.getPlans().add(new LandlinePlan(company, Integer.parseInt(textField_minutes.getText()), Double.parseDouble(textField_price.getText()), textField_field_1.getText(), textField_field_2.getText()));
                }

                Plan.getPlans().get(Plan.getPlans().size() - 1).setUnique_id(Integer.parseInt(textField_unique_id.getText()));
                Plan.setCount(Plan.getCount()-1);

                Main.inform_user("Success!","Successful update");

                break;
            }
        }
        B_Control_Plans_GUI(true, null);
    }

    private static void delete(int unique_id) {

        outer: for (int i = 0; i < Plan.getPlans().size(); i++) {
            if (Plan.getPlans().get(i).getUnique_id() == unique_id) {
               //We have to check if there is any Contract with this Plan
                for (Contract contract : Contract.getContracts()) {
                    if (contract.getPlan().getUnique_id() == unique_id) {
                        Main.inform_user("Error!","There is at least one contract with this plan therefore deletion was impossible.");
                        break outer;
                    }
                }
                //If we reach here, there isn't any Contract with this Plan
                Plan.getPlans().remove(i);
                Main.inform_user("Success!","Successful delete");
                break;
            }
        }
        B_Control_Plans_GUI(true,null);
    }

    private static void search(boolean is_by_type) {

        ArrayList<Plan> temp_plans = new ArrayList<>();

        List<String> choices = new ArrayList<>();
        if (is_by_type) {
            choices.add("Landline");
            choices.add("Mobile");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Landline", choices);
            dialog.setTitle("Search by type");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose type:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                for (Plan plan: Plan.getPlans()){
                    if ((result.get().equals("Mobile") && plan instanceof MobilePlan) || (result.get().equals("Landline") && plan instanceof LandlinePlan)){
                        temp_plans.add(plan); //We make the table contain sub-element of companies will given name
                    }
                }
            }
            else {
                //User clicked X or cancel
                return;
            }

        } else {

            for (TelecommunicationCompany company : TelecommunicationCompany.getCompanies()){
                choices.add(company.getCompany_name());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Search by company name");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose company name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equals("")){

                TelecommunicationCompany company = null;
                for (TelecommunicationCompany temp_company : TelecommunicationCompany.getCompanies()){
                    if (temp_company.getCompany_name().equals(result.get())){
                        company=temp_company;
                        break;
                    }
                }

                if (company == null){
                    return;
                }

                for (Plan plan : Plan.getPlans()){

                    if (company.getUnique_id() == plan.getCompany().getUnique_id()){
                        temp_plans.add(plan);
                    }
                }
            }
            else {
                //User clicked X or cancel
                return;
            }
        }
        table = convert_arraylist_to_table(temp_plans);
        B_Control_Plans_GUI(false,null);
    }

    private static ArrayList<Plan> get_all_elements() {
        //This function will return all elements
        return Plan.getPlans();
    }

    /* Getters & Setters */

    public int getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(int unique_id) {
        this.unique_id = unique_id;
    }

    public TelecommunicationCompany getCompany() {
        return company;
    }

    public void setCompany(TelecommunicationCompany company) {
        this.company = company;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Plan.count = count;
    }

    public static ArrayList<Plan> getPlans() {
        return plans;
    }

    public static void setPlans(ArrayList<Plan> plans) {
        Plan.plans = plans;
    }
}
