package logic;

import java.util.ArrayList;
import java.util.LinkedList;

public class Result {

    private String damName;
    private int days;
    private LinkedList<Double> fsValue;

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

    public LinkedList<Double> getFsValue() {
        return fsValue;
    }

    public void setFsValue(LinkedList<Double> fsValue) {
        this.fsValue = fsValue;
    }

    public Result(String damName, int days, LinkedList<Double> list) {
        this.damName = damName;
        this.days = days;
        this.fsValue = list;
    }
}
