package dev.vabalas.demointegration.masterhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("master-handler")
public class MasterHandlerEndpoint {

    public record UniversalRequest(String duomenys, String additionalProcess) {}

    public record CaseInstance(String value) {}

    @PostMapping
    public ResponseEntity<CaseInstance> createCase(@RequestBody UniversalRequest request, @RequestParam(defaultValue = "true") boolean success) {
        if (success)
            return ResponseEntity.ok(new CaseInstance("case instance with " + request.duomenys() + " and " + request.additionalProcess()));
        return ResponseEntity.internalServerError().build();
    }
}
