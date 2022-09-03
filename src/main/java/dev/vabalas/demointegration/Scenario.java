package dev.vabalas.demointegration;

public class Scenario {

    private String duomenys;

    public String getDuomenys() {
        return duomenys;
    }

    public void setDuomenys(String duomenys) {
        this.duomenys = duomenys;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "duomenys='" + duomenys + '\'' +
                '}';
    }
}
