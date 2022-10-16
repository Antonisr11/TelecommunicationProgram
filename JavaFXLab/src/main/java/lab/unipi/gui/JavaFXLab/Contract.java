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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Contract {

    /* Fields for Contract object */
    private String unique_id;
    private String phone_number;
    private String AFM;
    private Plan plan;
    private LocalDate start_date;
    private int duration_in_months;
    private double discount;
    private double final_cost;
    private String account_type; // Electronic or Physical
    private String payment_method; // Cash or Credit Cart
    private double cancel_cost; // In 3 first months: 10% of total cost, after 3 months: 0
    private boolean is_contract_active;
    private static ArrayList<Contract> contracts = new ArrayList<>(); //This array list holds all contracts

    public Contract(String phone_number, String AFM, Plan plan, LocalDate start_date, int duration_in_months, double cost, String account_type, String payment_method, boolean is_contract_active) {
        //Constructor
        this.phone_number = phone_number;
        this.AFM = AFM;
        this.plan = plan;
        this.start_date = start_date;
        this.duration_in_months = duration_in_months;
        this.account_type = account_type;
        this.payment_method = payment_method;
        this.is_contract_active = is_contract_active;
        this.final_cost = cost;

        this.unique_id = start_date.format(DateTimeFormatter.ofPattern("ddMMyyyy"))+"-<"+AFM+">-"; //ID generates automatically
        if (plan instanceof MobilePlan){
            this.unique_id+="Mobile";
        }
        else {
            this.unique_id+="Landline";
        }

        /* Calculate discount % */
        int discount_percentage = 0;

        for (Client client : Client.getClients()){
            if (client.getAFM().equals(AFM)){
                if (client.getCategory().equals("Individual")){
                    discount_percentage+=10;
                }
                else if (client.getCategory().equals("Student")){
                    discount_percentage+=15;
                }
                break;
            }
        }

        if (this.plan instanceof LandlinePlan && this.plan.getMinutes() > 1000){
            discount_percentage+=8;
        }
        else if (this.plan instanceof MobilePlan && this.plan.getMinutes() > 1000) {
            discount_percentage+=11;
        }

        if (this.account_type.equals("Electronic")){
            discount_percentage+=5;
        }

        if (this.payment_method.equals("Credit Card")){
            discount_percentage+=2;
        }

        this.discount = this.final_cost * discount_percentage / 100; // Discount = cost * discount / 100
        this.final_cost = this.final_cost - discount; //Final_cost = cost - discount

        /* If final cost or discount has more than 2 decimal numbers, simplify */
        if (String.valueOf(this.final_cost).contains(".")){
            this.final_cost = Double.parseDouble(String.valueOf(this.final_cost).substring(0,String.valueOf(this.final_cost).indexOf('.')+String.valueOf(this.final_cost).length()>2? 2 : String.valueOf(this.final_cost).length()));
        }

        if (String.valueOf(this.discount).contains(".")){
            this.discount = Double.parseDouble(String.valueOf(this.discount).substring(0,String.valueOf(this.discount).indexOf('.')+String.valueOf(this.discount).length()>2? 2 : String.valueOf(this.final_cost).length()));
        }

    }

    public double calculate_cancelling_cost(){
        //When client requests cancellation this function returns cost
        LocalDate dateBefore90Days = LocalDate.now().minusDays(90);
        if (start_date.isAfter(dateBefore90Days) && start_date.isBefore(LocalDate.now())){
            cancel_cost = 0.1 * final_cost;
        }
        else{
            cancel_cost = 0;
        }
        return cancel_cost;
    }

    static public class contract_for_tableview{
        //This class is created in order to show this element in table view (we cannot show Plan because it has Plan field)
        private String unique_id;
        private String phone_number;
        private String AFM;
        private int plan_id;
        private LocalDate start_date;
        private int duration_in_months;
        private double discount;
        private double final_cost;
        private String account_type;
        private String payment_method;
        private String is_contract_active;

        public contract_for_tableview(Contract contract){
            //Constructor
            this.unique_id = contract.getUnique_id();
            this.phone_number = contract.getPhone_number();
            this.AFM = contract.getAFM();
            this.plan_id = contract.getPlan().getUnique_id();
            this.start_date = contract.getStart_date();
            this.duration_in_months = contract.getDuration_in_months();
            this.discount = contract.getDiscount();
            this.final_cost = contract.getFinal_cost();
            this.account_type = contract.getAccount_type();
            this.payment_method = contract.getPayment_method();
            this.is_contract_active = contract.isIs_contract_active() ? "YES" : "NO";
        }

        /* Getters & Setters */

        public String getUnique_id() {
            return unique_id;
        }

        public void setUnique_id(String unique_id) {
            this.unique_id = unique_id;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getAFM() {
            return AFM;
        }

        public void setAFM(String AFM) {
            this.AFM = AFM;
        }

        public int getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(int plan_id) {
            this.plan_id = plan_id;
        }

        public LocalDate getStart_date() {
            return start_date;
        }

        public void setStart_date(LocalDate start_date) {
            this.start_date = start_date;
        }

        public int getDuration_in_months() {
            return duration_in_months;
        }

        public void setDuration_in_months(int duration_in_months) {
            this.duration_in_months = duration_in_months;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public double getFinal_cost() {
            return final_cost;
        }

        public void setFinal_cost(double final_cost) {
            this.final_cost = final_cost;
        }

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getIs_contract_active() {
            return is_contract_active;
        }

        public void setIs_contract_active(String is_contract_active) {
            this.is_contract_active = is_contract_active;
        }
    }

    /* Fields for GUI */
    static javafx.scene.control.TableView<Contract.contract_for_tableview> table;
    static TextField textField_unique_id;
    static Label label_unique_id;
    static TextField textField_phone_number;
    static Label label_phone_number;
    static TextField textField_AFM;
    static Label label_AFM;
    static TextField textField_plan_id;
    static Label label_plan_id;
    static DatePicker datePicker_start_date = new DatePicker();
    static Label label_start_date;
    static ComboBox<String> comboBox_duration_in_months;
    static Label label_duration_in_months;
    static TextField textField_cost;
    static Label label_cost;
    static ComboBox<String> comboBox_account_type;
    static Label label_account_type;
    static ComboBox<String> comboBox_payment_method;
    static Label label_payment_method;
    static RadioButton radio_button_is_contract_active_true;
    static RadioButton radio_button_is_contract_active_false;
    static Label label_is_contract_active;
    static Button button_submit;
    static Button button_return;
    static Button button_deactivate;
    static Button button_insert;
    static Button button_search_by_type;
    static Button button_search_by_company;

    public static void D_Control_Contracts_GUI(boolean show_all){

        VBox pane_0_0 = new VBox();//This pane will contain all labels in a vertical box
        label_unique_id = new Label();
        label_unique_id.setText("ID: ");
        label_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_phone_number = new Label();
        label_phone_number.setText("Phone number: ");
        label_phone_number.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_AFM = new Label();
        label_AFM.setText("AFM: ");
        label_AFM.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_plan_id = new Label();
        label_plan_id.setText("Plan ID: ");
        label_plan_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_start_date = new Label();
        label_start_date.setText("Start date: ");
        label_start_date.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_duration_in_months = new Label();
        label_duration_in_months.setText("Duration: ");
        label_duration_in_months.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_cost = new Label();
        label_cost.setText("Cost: ");
        label_cost.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_account_type = new Label();
        label_account_type.setText("Account type: ");
        label_account_type.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_payment_method = new Label();
        label_payment_method.setText("Payment method: ");
        label_payment_method.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        label_is_contract_active = new Label();
        label_is_contract_active.setText("Is contract active: ");
        label_is_contract_active.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        pane_0_0.getChildren().addAll(label_unique_id, label_phone_number, label_AFM,label_plan_id,label_start_date,label_duration_in_months, label_cost, label_account_type, label_payment_method, label_is_contract_active);
        pane_0_0.setSpacing(27);//27 pixels will be from one label to another
        pane_0_0.setPadding(new Insets(8, 0, 0, 0));//8 pixels margin from top

        VBox pane_0_1 = new VBox();//This pane will contain all text fields
        textField_unique_id = new TextField();
        textField_unique_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_unique_id.setDisable(true);
        textField_phone_number = new TextField();
        textField_phone_number.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_AFM = new TextField();
        textField_AFM.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        textField_plan_id = new TextField();
        textField_plan_id.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        datePicker_start_date = new DatePicker();

        comboBox_duration_in_months = new ComboBox<>();
        ObservableList<String> list0 = comboBox_duration_in_months.getItems();
        list0.add("12 months");
        list0.add("24 months");

        textField_cost = new TextField();
        textField_cost.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        comboBox_account_type = new ComboBox<>();
        ObservableList<String> list1 = comboBox_account_type.getItems();
        list1.add("Form");
        list1.add("Electronic");

        comboBox_payment_method = new ComboBox<>();
        ObservableList<String> list2 = comboBox_payment_method.getItems();
        list2.add("Credit Card");
        list2.add("Cash");

        final ToggleGroup radio_buttons_group = new ToggleGroup();
        radio_button_is_contract_active_true = new RadioButton("Active");
        radio_button_is_contract_active_true.setToggleGroup(radio_buttons_group);
        radio_button_is_contract_active_false = new RadioButton("Inactive");
        radio_button_is_contract_active_false.setToggleGroup(radio_buttons_group);

        HBox pane_radio_buttons = new HBox(); //This pane contains radio buttons
        pane_radio_buttons.getChildren().addAll(radio_button_is_contract_active_true, radio_button_is_contract_active_false);
        pane_radio_buttons.setSpacing(20);
        pane_radio_buttons.setAlignment(Pos.CENTER);

        textField_unique_id.setText("Not generated yet!");
        textField_phone_number.setPromptText("Phone number here");
        textField_AFM.setPromptText("AFM here");
        textField_plan_id.setPromptText("Plan ID here");
        comboBox_duration_in_months.setValue("12 months");
        textField_cost.setPromptText("Cost here");
        comboBox_account_type.setValue("Form");
        comboBox_payment_method.setValue("Credit Card");
        radio_button_is_contract_active_true.setSelected(true);

        pane_0_1.getChildren().addAll(textField_unique_id, textField_phone_number, textField_AFM, textField_plan_id, datePicker_start_date, comboBox_duration_in_months, textField_cost,comboBox_account_type,comboBox_payment_method, pane_radio_buttons);
        pane_0_1.setSpacing(20);

        HBox pane_0 = new HBox(); //This pane will contain labels with text boxes
        pane_0.getChildren().addAll(pane_0_0, pane_0_1);
        pane_0.setAlignment(Pos.TOP_CENTER);

        VBox pane_1 = new VBox();//This pane will contain button submit. We use this extra pane in order to put button in middle
        button_submit = new Button();
        button_submit.setText("Submit");
        button_submit.setOnAction(e -> insert());//We are on insert so submit button will call insert function

        button_submit.setPrefWidth(100);
        button_submit.setPrefHeight(50);
        button_submit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        pane_1.getChildren().add(button_submit);
        pane_1.setAlignment(Pos.CENTER);//All elements (in this case: submit button will be at middle)
        pane_1.setPadding(new Insets(25, 0, 0, 0));

        VBox pane_2 = new VBox();// This pane will contain labels, text boxes and button
        pane_2.getChildren().addAll(pane_0, pane_1);
        pane_2.setPadding(new Insets(0, 0, 0, 10));// 10 pixels margin from left

        HBox pane_3 = new HBox();//This pane will contain table will elements

        if (show_all) {
            //If user clicks search, we have to show them a sublist of items. In any other case we will show them all elements
            table = convert_arraylist_to_table(get_all_elements());
        }

        table.setPrefWidth(1050);
        table.setMaxHeight(530);
        pane_3.getChildren().add(table);
        pane_3.setAlignment(Pos.TOP_LEFT); // Table will go to to left corner
        pane_3.setSpacing(500);

        HBox pane_4 = new HBox(); //This pane contains table, labels, text boxes and submit button
        pane_4.getChildren().addAll(pane_3, pane_2);

        HBox pane_5 = new HBox();//This pane contains all buttons (insert, deactivate, search ect)
        button_return = new Button();
        button_deactivate = new Button();
        button_insert = new Button();
        button_search_by_type = new Button();
        button_search_by_company = new Button();
        button_return.setText("Return");
        button_deactivate.setText("Deactivate");
        button_insert.setText("Insert");
        button_search_by_type.setText("Search by type");
        button_search_by_company.setText("Search by company");
        button_return.setOnAction(e -> Main.main_menu_GUI());//Return action will call main menu
        button_deactivate.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                //If user clicks deactivate and haven't choose an element an information box will appear
                delete(table.getSelectionModel().getSelectedItem().getUnique_id());
            } else {
                Main.inform_user("Error", "Please select an element before you hit delete");
            }
        });
        button_insert.setOnAction(e -> D_Control_Contracts_GUI(true));//, null));
        button_search_by_type.setOnAction(e -> search(true));
        button_search_by_company.setOnAction(e -> search(false));
        button_return.setPrefWidth(100);
        button_return.setPrefHeight(50);
        button_deactivate.setPrefWidth(100);
        button_deactivate.setPrefHeight(50);
        button_insert.setPrefWidth(100);
        button_insert.setPrefHeight(50);
        button_search_by_type.setPrefWidth(150);
        button_search_by_type.setPrefHeight(50);
        button_search_by_company.setPrefWidth(150);
        button_search_by_company.setPrefHeight(50);
        button_insert.setDisable(true);

        pane_5.getChildren().addAll(button_return, button_deactivate, button_insert, button_search_by_type, button_search_by_company);
        pane_5.setAlignment(Pos.CENTER);
        pane_5.setPadding(new Insets(30, 0, 0, 0));
        pane_5.setSpacing(30);

        VBox main_pane = new VBox(); //Adding all panes in a main pane
        main_pane.getChildren().addAll(pane_4, pane_5); //Adding all panes in a main pane
        main_pane.setPadding(new Insets(15, 0, 0, 10));

        //Center window at screen
        Main.stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - 1450) / 2);
        Main.stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - 550) / 2);

        Main.stage.setScene(new Scene(main_pane)); //Create Scene
        Main.stage.setTitle("Control Contracts"); //Window title
        Main.stage.setResizable(false); //Disable resizing from user
        Main.stage.setWidth(1450); //Set width
        Main.stage.setHeight(700); //Set Height
        Main.stage.show();
    }

    private static TableView<Contract.contract_for_tableview> convert_arraylist_to_table(ArrayList<Contract> contracts){

        //We convert all Plans objects to plan_for_tableview objects in order to show company name in table
        ArrayList<Contract.contract_for_tableview> contracts_for_tableview_list = new ArrayList<>();
        for (Contract contract: contracts) {
            contracts_for_tableview_list.add(new Contract.contract_for_tableview(contract));
        }

        TableColumn<Contract.contract_for_tableview, String> column_id = new TableColumn<>("ID");
        column_id.setCellValueFactory(new PropertyValueFactory<>("unique_id"));

        TableColumn<Contract.contract_for_tableview, String> column_phone_number = new TableColumn<>("Phone");
        column_phone_number.setCellValueFactory(new PropertyValueFactory<>("phone_number"));

        TableColumn<Contract.contract_for_tableview, String> column_AFM = new TableColumn<>("AFM");
        column_AFM.setCellValueFactory(new PropertyValueFactory<>("AFM"));

        TableColumn<Contract.contract_for_tableview, Integer> column_plan_id = new TableColumn<>("Plan ID");
        column_plan_id.setCellValueFactory(new PropertyValueFactory<>("plan_id"));

        TableColumn<Contract.contract_for_tableview, LocalDate> column_start_date = new TableColumn<>("Start Date");
        column_start_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));

        TableColumn<Contract.contract_for_tableview, Integer> column_duration_in_months = new TableColumn<>("Duration in Months");
        column_duration_in_months.setCellValueFactory(new PropertyValueFactory<>("duration_in_months"));
        column_duration_in_months.setPrefWidth(150);

        TableColumn<Contract.contract_for_tableview, Double> column_discount = new TableColumn<>("Discount");
        column_discount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TableColumn<Contract.contract_for_tableview, Double> column_final_cost = new TableColumn<>("Final cost");
        column_final_cost.setCellValueFactory(new PropertyValueFactory<>("final_cost"));

        TableColumn<Contract.contract_for_tableview, String> column_account_type = new TableColumn<>("Account Type");
        column_account_type.setCellValueFactory(new PropertyValueFactory<>("account_type"));
        column_account_type.setPrefWidth(115);

        TableColumn<Contract.contract_for_tableview, String> column_payment_method = new TableColumn<>("Payment Method");
        column_payment_method.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
        column_payment_method.setPrefWidth(120);

        TableColumn<Contract.contract_for_tableview, String> column_is_contract_active = new TableColumn<>("Active");
        column_is_contract_active.setCellValueFactory(new PropertyValueFactory<>("is_contract_active"));

        table = new TableView<>();

        table.getColumns().add(column_id);
        table.getColumns().add(column_phone_number);
        table.getColumns().add(column_AFM);
        table.getColumns().add(column_start_date);
        table.getColumns().add(column_duration_in_months);
        table.getColumns().add(column_discount);
        table.getColumns().add(column_final_cost);
        table.getColumns().add(column_account_type);
        table.getColumns().add(column_payment_method);
        table.getColumns().add(column_is_contract_active);

        for (Contract.contract_for_tableview contract : contracts_for_tableview_list) {
            table.getItems().add(contract);//Adding all elements from argument in table
        }
        return table;

    }

    private static boolean has_errors(){//This function is used in order to check text boxes from user. If this function returns false it means that all fields are 100% right
        String errors = ""; //This variable will contain all user's errors

        boolean error_in_phone = false;
        String phone_number = "";

        try{
            phone_number = textField_phone_number.getText();
            if (phone_number.equals("")){
                throw new Exception();
            }
            else if (!phone_number.matches("[0-9]+")){
                errors+="Phone must have only numbers\n";
                error_in_phone=true;
            }
            else if (phone_number.length()!=10){
                errors+="Phone must be 10 length long\n";
                error_in_phone=true;
            }
        }
        catch (Exception e){
            errors+="Phone is empty\n";
            error_in_phone=true;
        }

        try{
            String AFM = textField_AFM.getText();
            if (AFM.equals("")){
                throw new Exception();
            }
            else if (!AFM.matches("[0-9]+")){
                errors+="AFM must have only numbers\n";
            }
            else{
                boolean exists = false;
                for (Client client : Client.getClients()){
                    if (client.getAFM().equals(AFM)){
                        exists=true;
                        break;
                    }
                }

                if (!exists){
                    errors+="There isn't any client with this AFM\n";
                }
            }
        }
        catch (Exception e){
            errors+="AFM is empty\n";
        }

        try{
            boolean exists = false;
            int plan_id = Integer.parseInt(textField_plan_id.getText());

            for (Plan plan : Plan.getPlans()){
                if (plan.getUnique_id() == plan_id){
                    exists=true;
                    if (! error_in_phone){
                        if (!((plan instanceof MobilePlan && phone_number.charAt(0) == '6') || ( plan instanceof LandlinePlan && phone_number.charAt(0) == '2'))){
                            errors+="Mobile phones must start with 6 and Landline with 2\n";
                        }
                    }
                    break;
                }
            }

            if (!exists){
                errors+="There isn't any plan with this ID\n";
            }

        }
        catch (Exception e){
            errors+="Plan ID is not a number\n";
        }

        if (datePicker_start_date.getValue() == null){
            errors+="Pick a date\n";
        }

        try {
            double cost = Double.parseDouble(textField_cost.getText());

            if (cost<0){
                errors+="Cost cannot be negative\n";
            }

        } catch (Exception e) {
            errors += "Cost is not a number\n";
        }

        for (Contract contract : Contract.getContracts()){
            if (!error_in_phone && radio_button_is_contract_active_true.isSelected() && contract.isIs_contract_active() && contract.getPhone_number().equals(phone_number) &&
                    contract.getStart_date().isBefore(datePicker_start_date.getValue().plusMonths(comboBox_duration_in_months.getValue().equals("12 months") ? 12 : 24)) &&
                    contract.getStart_date().plusMonths(contract.getDuration_in_months()).isAfter(datePicker_start_date.getValue())){
                errors+="An active contract for this number is active in this period\n";
                break;
            }
        }

        if (errors.equals("")) {
            return false;
        } else {
            Main.inform_user("Error!", "Please fix:\n" + errors);
            return true;
        }
    }

    private static void insert(){
        //This function insert a new element if there are no mistakes in fields

        if (has_errors()){
            return;
        }

        Plan plan = null;
        for (Plan temp_plan : Plan.getPlans()){
            if (temp_plan.getUnique_id() == Integer.parseInt(textField_plan_id.getText())){
                plan = temp_plan;
                break;
            }
        }

        Contract.getContracts().add(new Contract(textField_phone_number.getText(), textField_AFM.getText(), plan, datePicker_start_date.getValue(), comboBox_duration_in_months.getValue().equals("12 months") ? 12 : 24, Double.parseDouble(textField_cost.getText()), comboBox_account_type.getValue(), comboBox_payment_method.getValue(), radio_button_is_contract_active_true.isSelected()));
        Main.inform_user("Success!","Successful insert!");
        D_Control_Contracts_GUI(true);//,null);
    }

    private static void delete(String unique_id){
        for (Contract contract : Contract.getContracts()){
            if (contract.getUnique_id().equals(unique_id)){
                if (!contract.isIs_contract_active()){
                    Main.inform_user("Error!","This contract is already deactivated");
                }
                else {
                    double cost = contract.calculate_cancelling_cost();
                    //We have to inform user for cancellation cost
                    if (Main.ask_user("Want to continue?","There will be a cost at "+String.format("%.02f", cost)+"€. Want to proceed?")){
                        contract.setIs_contract_active(false);
                        Main.inform_user("Success!","Contract deactivated successfully!");
                    }
                }
                break;
            }
        }
        D_Control_Contracts_GUI(true);
    }

    private static void search(boolean is_by_type){

        ArrayList<Contract> temp_contracts = new ArrayList<>();

        List<String> choices = new ArrayList<>();
        if (is_by_type) {
            choices.add("Landline");
            choices.add("Mobile");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Search by type");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose type:");
            dialog.setSelectedItem("Landline");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                for (Contract contract: Contract.getContracts()){
                    if (((contract.getPlan() instanceof MobilePlan) && result.get().equals("Mobile")) || ((contract.getPlan() instanceof LandlinePlan) && result.get().equals("Landline")) ){
                        temp_contracts.add(contract);
                    }
                }
            }
            else {
                //User clicked X or cancel
                return;
            }

        } else {

            for (Contract contract : Contract.getContracts()){
                if (! choices.contains(contract.getPhone_number())){
                    choices.add(contract.getPhone_number());
                }
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
            dialog.setTitle("Search by number");
            dialog.setHeaderText(null);
            dialog.setContentText("Choose number:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equals("")){

                for (Contract contract : Contract.getContracts()){
                    if (contract.getPhone_number().equals(result.get())){
                        temp_contracts.add(contract);
                    }
                }
            }
            else {
                //User clicked X or cancel
                return;
            }
        }
        table = convert_arraylist_to_table(temp_contracts);
        D_Control_Contracts_GUI(false);
    }

    private static ArrayList<Contract> get_all_elements(){
        //This function will return all elements
        return Contract.getContracts();
    }

    /* Getters & Setters */

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAFM() {
        return AFM;
    }

    public void setAFM(String AFM) {
        this.AFM = AFM;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public int getDuration_in_months() {
        return duration_in_months;
    }

    public void setDuration_in_months(int duration_in_months) {
        this.duration_in_months = duration_in_months;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getFinal_cost() {
        return final_cost;
    }

    public void setFinal_cost(double final_cost) {
        this.final_cost = final_cost;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public double getCancel_cost() {
        return cancel_cost;
    }

    public void setCancel_cost(double cancel_cost) {
        this.cancel_cost = cancel_cost;
    }

    public boolean isIs_contract_active() {
        return is_contract_active;
    }

    public void setIs_contract_active(boolean is_contract_active) {
        this.is_contract_active = is_contract_active;
    }

    public static ArrayList<Contract> getContracts() {
        return contracts;
    }

    public static void setContracts(ArrayList<Contract> contracts) {
        Contract.contracts = contracts;
    }
}
