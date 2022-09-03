package dev.vabalas.demointegration;

import dev.vabalas.demointegration.masterhandler.MasterHandlerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("demo")
public class DemoEndpoint {

    private final RestTemplate restTemplate;
    private final MasterHandlerGateway masterHandlerGateway;

    public DemoEndpoint(RestTemplate restTemplate, MasterHandlerGateway masterHandlerGateway) {
        this.restTemplate = restTemplate;
        this.masterHandlerGateway = masterHandlerGateway;
    }

    @PostMapping("v1/flow-a")
    public CaseInstanceResponse createCaseA(@RequestBody FlowARequest request) {
        String duomenys = restTemplate.getForEntity("http://localhost:8080/duomenys", String.class).getBody();

        MasterHandlerEndpoint.UniversalRequest requestBody = new MasterHandlerEndpoint.UniversalRequest(duomenys, null);
        MasterHandlerEndpoint.CaseInstance response = restTemplate.postForEntity("http://localhost:8080/master-handler?success=" + request.isWithCase(), requestBody, MasterHandlerEndpoint.CaseInstance.class).getBody();

        CaseInstanceResponse enrichedResponse = new CaseInstanceResponse(response.value(), "ENRICHED DATA A");

        return enrichedResponse;
    }

    @PostMapping("v1/flow-b")
    public CaseInstanceResponse createCaseB(@RequestBody FlowBRequest request) {
        String duomenys = restTemplate.getForEntity("http://localhost:8080/duomenys", String.class).getBody();

        String additionalProcess = "additional process";

        MasterHandlerEndpoint.UniversalRequest requestBody = new MasterHandlerEndpoint.UniversalRequest(duomenys, additionalProcess);
        MasterHandlerEndpoint.CaseInstance newCase = restTemplate.postForEntity("http://localhost:8080/master-handler?success=" + request.isWithCase(), requestBody, MasterHandlerEndpoint.CaseInstance.class).getBody();

        return new CaseInstanceResponse(newCase.value());
    }

    @PostMapping("v2/flow-a")
    @ResponseStatus(HttpStatus.CREATED)
    public CaseInstanceResponse createCaseA2(@RequestBody FlowARequest request) {
        // gateways are nice for making spring integration to "feel like" we are not using it at all
        // so no need to change habits of how we write business logic in services for example
        return masterHandlerGateway.createCase(request);
    }

    @PostMapping("v2/flow-b")
    @ResponseStatus(HttpStatus.CREATED)
    public CaseInstanceResponse createCaseB2(@RequestBody FlowBRequest request) {
        return masterHandlerGateway.createCase(request);

    }
}
