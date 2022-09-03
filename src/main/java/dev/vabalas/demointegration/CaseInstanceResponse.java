package dev.vabalas.demointegration;

import dev.vabalas.demointegration.masterhandler.MasterHandlerEndpoint;

public class CaseInstanceResponse {
    String value, enrichedData;

    public CaseInstanceResponse(String value) {
        this.value = value;
    }

    public static CaseInstanceResponse fromCaseInstance(MasterHandlerEndpoint.CaseInstance caseInstance) {
        return new CaseInstanceResponse(caseInstance.value());
    }

    public CaseInstanceResponse(String value, String enrichedData) {
        this.value = value;
        this.enrichedData = enrichedData;
    }

    public String getValue() {
        return value;
    }

    public String getEnrichedData() {
        return enrichedData;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEnrichedData(String enrichedData) {
        this.enrichedData = enrichedData;
    }

    @Override
    public String toString() {
        return "CaseInstanceEnriched{" +
                "value='" + value + '\'' +
                ", enrichedData='" + enrichedData + '\'' +
                '}';
    }
}
