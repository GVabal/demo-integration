package dev.vabalas.demointegration;

public class ScenarioB extends Scenario {

    private FlowBRequest request;
    private String additionalProcess;

    public FlowBRequest getRequest() {
        return request;
    }

    public void setRequest(FlowBRequest request) {
        this.request = request;
    }


    public String getAdditionalProcess() {
        return additionalProcess;
    }

    public void setAdditionalProcess(String additionalProcess) {
        this.additionalProcess = additionalProcess;
    }

    @Override
    public String toString() {
        return "ScenarioB{" +
                "request=" + request +
                ", additionalProcess='" + additionalProcess + '\'' +
                "} " + super.toString();
    }
}
