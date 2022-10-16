package lab.unipi.gui.JavaFXLab;

public class MobilePlan extends Plan {

    private int free_sms;
    private int free_GB;

    public MobilePlan(TelecommunicationCompany company, int minutes, double price, int free_sms, int free_GB) {
        //Constructor
        super(company, minutes, price);
        this.free_sms = free_sms;
        this.free_GB = free_GB;
    }

    /* Getters & Setters */

    public int getFree_sms() {
        return free_sms;
    }

    public void setFree_sms(int free_sms) {
        this.free_sms = free_sms;
    }

    public int getFree_GB() {
        return free_GB;
    }

    public void setFree_GB(int free_GB) {
        this.free_GB = free_GB;
    }
}
