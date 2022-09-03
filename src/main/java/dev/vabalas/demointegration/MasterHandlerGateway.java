package dev.vabalas.demointegration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface MasterHandlerGateway {

    @Gateway(requestChannel = "caseHandlerChannel")
    CaseInstanceResponse createCase(GenericRequest request);
}
