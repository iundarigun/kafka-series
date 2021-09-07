package br.com.devcave.spring.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Customer {
    private final Long id;
    private final String name;
    private final LocalDate birthday;

    public Customer(
            @JsonProperty("id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("birthday") final LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }
}
