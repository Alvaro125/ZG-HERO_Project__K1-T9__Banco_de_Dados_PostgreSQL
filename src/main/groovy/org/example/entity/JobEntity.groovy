package org.example.entity

class JobEntity {
    String name
    String description
    AddressEntity local
    LegalPersonEntity person
    Integer id

    JobEntity(String name, String description, AddressEntity local,
              LegalPersonEntity person, Integer id) {
        this.name = name
        this.description = description
        this.local = local
        this.person = person
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    AddressEntity getLocal() {
        return local
    }

    void setLocal(AddressEntity local) {
        this.local = local
    }

    LegalPersonEntity getPerson() {
        return person
    }

    void setPerson(LegalPersonEntity person) {
        this.person = person
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }


    @Override
    public String toString() {
        return """#$id
Nome da Vaga: $name
Descrição da Vaga: $description
Empresa: ${person.name}(CNPJ:${person.cnpj})
Endereço:
    $local
"""
    }
}
