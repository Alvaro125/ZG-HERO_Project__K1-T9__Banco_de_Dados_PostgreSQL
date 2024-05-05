package org.example.dto

import groovy.transform.Canonical
import lombok.Data
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity

@Data
@Canonical
class JobDto {
    String name
    String description
    AddressDto local
    Integer person

    JobDto(String name, String description, AddressDto local,
              Integer person) {
        this.name = name
        this.description = description
        this.local = local
        this.person = person
    }

    JobEntity toEntity(){
        LegalPersonEntity legalPerson = new LegalPersonEntity()
        legalPerson.setId(person)
        return new JobEntity(name,description,
                new AddressEntity(local.country,local.state,local.cep),legalPerson,0)
    }
}
