package dev.vabalas.demointegration;

public class FlowARequest extends GenericRequest {
    boolean withDuomenys, withCase;

    public boolean isWithDuomenys() {
        return withDuomenys;
    }

    public boolean isWithCase() {
        return withCase;
    }

    @Override
    public String toString() {
        return "FlowARequest{" +
                "withDuomenys=" + withDuomenys +
                ", withCase=" + withCase +
                "} " + super.toString();
    }
}
