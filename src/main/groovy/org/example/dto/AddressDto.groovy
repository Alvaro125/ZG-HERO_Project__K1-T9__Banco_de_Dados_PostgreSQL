package org.example.dto

import groovy.transform.Canonical
import lombok.Data

@Data
@Canonical
class AddressDto {
    String country
    String state
    String cep

    AddressDto(String country, String state, String cep) {
        this.country = country
        this.state = state
        this.cep = cep
    }
}
