package logic;

public class Dam {

    String name;
    int daysAmount;
    double percent;
    int high;
    double speed;
    double pound;
    double cohesion;
    double angle;
    double permeability;
    double volume;
    double comprensibility;
    double crown;

    public Dam(String name, int daysAmount, double percent, int high, double speed, double pound, double cohesion,
            double angle, double permeability, double volume, double comprensibility, double crown) {
        this.name = name;
        this.daysAmount = daysAmount;
        this.percent = percent;
        this.high = high;
        this.speed = speed;
        this.pound = pound;
        this.cohesion = cohesion;
        this.angle = angle;
        this.permeability = permeability;
        this.volume = volume;
        this.comprensibility = comprensibility;
        this.crown = crown;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDaysAmount() {
        return daysAmount;
    }
    public void setDaysAmount(int daysAmount) {
        this.daysAmount = daysAmount;
    }
    public double getPercent() {
        return percent;
    }
    public void setPercent(double percent) {
        this.percent = percent;
    }
    public int getHigh() {
        return high;
    }
    public void setHigh(int high) {
        this.high = high;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getPound() {
        return pound;
    }
    public void setPound(double pound) {
        this.pound = pound;
    }
    public double getCohesion() {
        return cohesion;
    }
    public void setCohesion(double cohesion) {
        this.cohesion = cohesion;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angulo) {
        this.angle = angulo;
    }
    public double getPermeability() {
        return permeability;
    }
    public void setPermeability(double permeabilidad) {
        this.permeability = permeabilidad;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volumen) {
        this.volume = volumen;
    }
    public double getComprensibility() {
        return comprensibility;
    }
    public void setComprensibility(double compresibilidad) {
        this.comprensibility = compresibilidad;
    }
    public double getCrown() {
        return crown;
    }
    public void setCrown(double anchoCorona) {
        this.crown = anchoCorona;
    }
    
}
