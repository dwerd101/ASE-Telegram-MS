package ase.digital.graphics.telegram.feign;

import ase.digital.graphics.telegram.model.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "security",url = "http://localhost:8080/ase/security")
public interface SecurityFeignClient {
    @RequestMapping(value = "/find-by-id/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
        method = RequestMethod.GET)
    Users findById(@PathVariable Long id );
}
