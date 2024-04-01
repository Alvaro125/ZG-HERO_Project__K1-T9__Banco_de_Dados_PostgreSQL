package org.example.entity

import org.example.entity.AddressEntity
import org.example.entity.PersonEntity

class NaturalPersonEntity extends PersonEntity {
    String cpf
    Integer age

    NaturalPersonEntity(String name, String email, String password,
                        String description, AddressEntity address,
                        String cpf, Integer age, Integer id, List<SkillEntity> skills = []) {
        super(name, email,password, description, address, id, skills)
        this.cpf = cpf
        this.age = age
        this.address = address
    }

    String getCpf() {
        return cpf
    }

    void setCpf(String cpf) {
        this.cpf = cpf
    }

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
    }

    @Override
    String toString() {
        return """${super.toString()}
Idade: ${this.age}
CPF: ${this.cpf}"""
    }
}
