package dev.vabalas.demointegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

import static dev.vabalas.demointegration.masterhandler.MasterHandlerEndpoint.*;

@Configuration
@EnableIntegration
public class OurFlowsConfig {

    @Autowired
    private RestTemplate restTemplate;

    // TODO transactional flows: https://stackoverflow.com/questions/49893900/how-to-handle-transactions-for-spring-integration-flows-java-dsl
    // TODO retries on failed operations, graceful exits

    // we have a flow that describes how to route the message to the right channel, in this case mapping by request object
    @Bean
    public IntegrationFlow caseHandlerRouterFlow() {
        return IntegrationFlows.from(caseHandlerChannel())
                .<Object, Class<?>>route(Object::getClass, routerSpec ->
                        routerSpec
                                .channelMapping(FlowARequest.class, channelA())
                                .channelMapping(FlowBRequest.class, channelB()))
                .get();
    }

    // we have two flows - one for each of our scenarios. Some steps are common among them and some are unique

    // flow A description
    @Bean
    public IntegrationFlow flowA() {
        return IntegrationFlows.from(channelA())
                .transform(OurFlowsConfig::prepareScenarioFromRequest)
                .handle(fetchDuomenys())
                .transform(createCaseFromScenarioA())
                .transform(CaseInstanceResponse::fromCaseInstance)
                .enrich(setEnrichedDataValue())
                .get();
    }

    // flow B description
    @Bean
    public IntegrationFlow flowB() {
        return IntegrationFlows.from(channelB())
                .transform(OurFlowsConfig::prepareScenarioFromRequest)
                .handle(fetchDuomenys())
                .handle(doSomeAdditionalProcess())
                .transform(createCaseFromScenarioB())
                .transform(CaseInstanceResponse::fromCaseInstance)
                .get();
    }



    // various building blocks for our flows that could be re-used, could be easily tested, mixed and matched in desired order, etc.
    // could live as @Beans for easy access in all application

    private static Scenario prepareScenarioFromRequest(GenericRequest request) {
        Scenario scenario;
        if (request instanceof FlowBRequest bRequest) {
            scenario = new ScenarioB();
            ((ScenarioB) scenario).setRequest(bRequest);
        }
        else if (request instanceof FlowARequest aRequest) {
            scenario = new ScenarioA();
            ((ScenarioA) scenario).setRequest(aRequest);
        }
        else {
            throw new RuntimeException("yeet");
        }
        return scenario;
    }

    private GenericHandler<Scenario> fetchDuomenys() {
        return (payload, headers) -> {
            String duomenys = restTemplate.getForEntity("http://localhost:8080/duomenys", String.class).getBody();
            payload.setDuomenys(duomenys);
            return payload;
        };
    }

    private GenericHandler<ScenarioB> doSomeAdditionalProcess() {
        return (payload, headers) -> {
            payload.setAdditionalProcess("ADDITIONAL PROCCESS HAPPENED");
            return payload;
        };
    }

    private Consumer<EnricherSpec> setEnrichedDataValue() {
        return enricherSpec -> enricherSpec
                .requestPayload(Message::getPayload)
                .propertyFunction("enrichedData", message -> "ENRICHED DATA");
    }

    // specific for certain flows (example OL, FL, A, B...) integration parts could live inside their own domains
    private GenericHandler<ScenarioA> createCaseFromScenarioA() {
        return (payload, headers) -> {
            // prepares universal request from scenario A
            UniversalRequest requestBody = new UniversalRequest(payload.getDuomenys(), null);
            return restTemplate.postForEntity("http://localhost:8080/master-handler?success=" + payload.getRequest().isWithCase(), requestBody, CaseInstance.class).getBody();
        };
    }

    private GenericHandler<ScenarioB> createCaseFromScenarioB() {
        return (payload, headers) -> {
            // prepares universal request from scenario B
            UniversalRequest requestBody = new UniversalRequest(payload.getDuomenys(), payload.getAdditionalProcess());
            return restTemplate.postForEntity("http://localhost:8080/master-handler?success=" + payload.getRequest().isWithCase(), requestBody, CaseInstance.class).getBody();
        };
    }

    // channels:

    @Bean
    public MessageChannel channelA() {
        return MessageChannels.direct()
                .get();
    }

    @Bean
    public MessageChannel channelB() {
        return MessageChannels.direct()
                .get();
    }

    @Bean
    public MessageChannel caseHandlerChannel() {
        return MessageChannels.direct()
                .get();
    }
}
