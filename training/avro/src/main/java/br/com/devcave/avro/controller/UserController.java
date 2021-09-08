package br.com.devcave.avro.controller;

import br.com.devcave.avro.domain.User;
import br.com.devcave.avro.service.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final Producer producer;

    @GetMapping
    public String user() {
        final User oriolCanalias =
                User.newBuilder()
                        .setFirstName("Oriol")
                        .setLastName("Canalias")
                        .setBirthday(LocalDate.of(2000, 10, 5))
                        .build();
        producer.sendMessage(oriolCanalias);
        return "OK";
    }
}
