package dev.vabalas.demointegration;

public class FlowBRequest extends GenericRequest {
    boolean withCase;

    public boolean isWithCase() {
        return withCase;
    }

    @Override
    public String toString() {
        return "FlowBRequest{" +
                "withCase=" + withCase +
                "} " + super.toString();
    }
}
