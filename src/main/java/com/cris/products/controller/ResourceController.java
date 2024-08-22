package com.cris.products.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ResourceController {

    @GetMapping("/name")
    public ResponseEntity<String> get(@RequestBody String metodo) throws Exception {
        log.info("Logica del metodo");
//        SeguridadES256 es256 = new SeguridadES256();

        log.info("Logica del metodo");
        log.info("Logica del metodo");
        log.info("Fin {}", metodo);
        return ResponseEntity.ok(metodo);
    }

    public ResponseEntity<Map> alternativeMethod(String path){
        Map map = Map.of(
                "mensaje", "servicio no disponible",
                "serviciio", path);
        return ResponseEntity.ok(map);
    }
}
