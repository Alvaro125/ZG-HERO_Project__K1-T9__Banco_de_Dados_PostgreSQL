package org.example.dto

import groovy.transform.Canonical
import lombok.Data
import org.example.entity.AddressEntity
import org.example.entity.NaturalPersonEntity

@Data
@Canonical
class NaturalPersonDto {
    String name
    String email
    String description
    AddressDto address
    String password
    String cpf
    Integer age

    NaturalPersonDto(String name, String email, String description, AddressDto address, String password, String cpf, Integer age) {
        this.name = name
        this.email = email
        this.description = description
        this.address = address
        this.password = password
        this.cpf = cpf
        this.age = age
    }

    NaturalPersonEntity toEntity(){
        return new NaturalPersonEntity(name,email,password,description,
                address.toEntity(),cpf,age,0)
    }
}
