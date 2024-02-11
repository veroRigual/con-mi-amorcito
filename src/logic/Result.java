package logic;

public class Result {

    private String damName;
    private int days;
    private double fsValue;

    public String getDamName() {
        return damName;
    }

    public void setDamName(String damName) {
        this.damName = damName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getFsValue() {
        return fsValue;
    }

    public void setFsValue(double fsValue) {
        this.fsValue = fsValue;
    }

    public Result(String damName, int days, double fsValue) {
        this.damName = damName;
        this.days = days;
        this.fsValue = fsValue;
    }
}
