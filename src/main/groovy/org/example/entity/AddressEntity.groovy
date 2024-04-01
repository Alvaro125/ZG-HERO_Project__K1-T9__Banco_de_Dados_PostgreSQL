package org.example.entity

class AddressEntity {
    Integer id
    String country
    String state
    String cep

    AddressEntity(String country, String state, String cep) {
        this.country = country
        this.state = state
        this.cep = cep
    }

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getCountry() {
        return country
    }

    void setCountry(String country) {
        this.country = country
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }

    String getCep() {
        return cep
    }

    void setCep(String cep) {
        this.cep = cep
    }

    @Override
    String toString() {
        return """Pais: ${this.country}
    Estado: ${this.state}
    CEP: ${this.cep}"""
    }
}
