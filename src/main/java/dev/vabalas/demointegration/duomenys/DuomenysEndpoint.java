package dev.vabalas.demointegration.duomenys;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("duomenys")
public class DuomenysEndpoint {

    @GetMapping
    public ResponseEntity<String> getDuomenys(@RequestParam(defaultValue = "true") boolean success) {
        if (success)
            return ResponseEntity.ok().body("DUOMENYS");
        return ResponseEntity.internalServerError().build();
    }
}
