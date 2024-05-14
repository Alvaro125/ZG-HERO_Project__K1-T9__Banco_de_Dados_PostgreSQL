package org.example.dto

import groovy.transform.Canonical
import lombok.Data
import org.example.entity.AddressEntity

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

    AddressEntity toEntity(){
        return new AddressEntity(country,state,cep)
    }
}
