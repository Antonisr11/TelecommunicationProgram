package lab.unipi.gui.JavaFXLab;

public class LandlinePlan extends Plan {

    private String power_of_line;
    private String type_of_line;

    public LandlinePlan(TelecommunicationCompany company, int minutes, double price, String power_of_line, String type_of_line) {
        //Constructor
        super(company, minutes, price);
        this.power_of_line = power_of_line;
        this.type_of_line = type_of_line;
    }

    /* Getters & Setters */

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
}
