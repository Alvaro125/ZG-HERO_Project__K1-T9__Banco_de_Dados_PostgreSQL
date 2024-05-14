package org.example.dto

import groovy.transform.Canonical
import lombok.Data
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity

@Data
@Canonical
class LegalPersonDto {
    String name
    String email
    String description
    AddressDto address
    String password
    String cnpj

    LegalPersonDto(String name, String email, String description, AddressDto address, String password, String cnpj) {
        this.name = name
        this.email = email
        this.description = description
        this.address = address
        this.password = password
        this.cnpj = cnpj
    }

    LegalPersonEntity toEntity(){
        return new LegalPersonEntity(name,email,password,description,
                address.toEntity(),cnpj,0)
    }
}
