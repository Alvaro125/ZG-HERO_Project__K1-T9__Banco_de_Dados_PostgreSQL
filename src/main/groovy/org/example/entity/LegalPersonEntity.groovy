package org.example.entity

class LegalPersonEntity extends PersonEntity {
    String cnpj
    LegalPersonEntity(
            String nome, String email, String password, String description, AddressEntity address,
            String cnpj, Integer id, List<SkillEntity> skills=[]) {
        super(nome, email, password, description, address, id, skills)
        this.cnpj = cnpj
    }
    LegalPersonEntity(){
        super()
    }

    String getCnpj() {
        return cnpj
    }

    void setCnpj(String cnpj) {
        this.cnpj = cnpj
    }

    @Override
    String toString() {
        return """${super.toString()}
CNPJ: ${this.cnpj}"""
    }
}
