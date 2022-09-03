package dev.vabalas.demointegration;

public class ScenarioA extends Scenario {

    private FlowARequest request;

    public FlowARequest getRequest() {
        return request;
    }

    public void setRequest(FlowARequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "ScenarioA{" +
                "request=" + request +
                "} " + super.toString();
    }
}
