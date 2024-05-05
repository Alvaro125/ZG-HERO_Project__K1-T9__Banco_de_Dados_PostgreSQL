package org.example.entity

abstract class PersonEntity {
    Integer id
    String name
    String email
    String description
    AddressEntity address
    String password
    List<SkillEntity> skills = []

    Integer getId() {
        return id
    }

    void setId(Integer id) {
        this.id = id
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }
    PersonEntity(){}
    PersonEntity(String name, String email, String password,
                 String description, AddressEntity address,Integer id, List<SkillEntity> skills=[]) {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.description = description
        this.address = address
        this.skills = skills
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getDescription() {
        return description
    }

    AddressEntity getAddress() {
        return address
    }

    void setAddress(AddressEntity address) {
        this.address = address
    }

    void setDescription(String description) {
        this.description = description
    }

    void setSkills(List<SkillEntity> skills) {
        this.skills = skills
    }

    List<SkillEntity> getSkills() {
        return this.skills
    }

    void addSkills(SkillEntity skill) {
        this.skills.add(skill)
    }


    @Override
    String toString() {
        return """$id#Nome: ${this.name}
Email: ${this.email}
Descrição: ${this.description}
Endereço: 
    ${this.address}
Competencias: [${this.skills.join("\n\t")}]"""
    }
}
