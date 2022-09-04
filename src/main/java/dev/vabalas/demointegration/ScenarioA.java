package dev.vabalas.demointegration;

public class ScenarioA extends Scenario {

    private FlowARequest request;

    private ScenarioA(Builder builder) {
        this.request = builder.request;
    }

    public FlowARequest getRequest() {
        return request;
    }

    public void setRequest(FlowARequest request) {
        this.request = request;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private FlowARequest request;

        public Builder withRequest(FlowARequest request) {
            this.request = request;
            return this;
        }

        public ScenarioA build() {
            return new ScenarioA(this);
        }
    }

    @Override
    public String toString() {
        return "ScenarioA{" +
                "request=" + request +
                "} " + super.toString();
    }
}
