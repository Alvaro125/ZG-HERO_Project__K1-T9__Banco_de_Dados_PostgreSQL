package org.example.ui

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity
import org.example.services.LegalPersonService
import org.example.services.NaturalPersonService
import org.example.services.SkillService

class NaturalPersonUI {
    private static NaturalPersonService naturalPersonService = new NaturalPersonService(Database.conn)
    private static SkillService skillService = new SkillService(Database.conn)

    void read() {
        println """
            @@Lista de Empresas@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            ${naturalPersonService.listAll().join("\n")}
            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        List<SkillEntity> skills = []

        String name = readNonEmptyInput(br, "Nome do Candidato:")
        String email = readNonEmptyInput(br, "Email do Candidato:")
        String password = readNonEmptyInput(br, "Senha:")
        String description = readNonEmptyInput(br, "Descrição:")
        AddressEntity address = readAddress(br)
        skills.addAll(readSkills(br))

        String cpf = readNonEmptyInput(br, "CPF:")
        int age = readIntegerInput(br, "Idade:")

        NaturalPersonEntity newCandidate = new NaturalPersonEntity(
                name, email, password, description, address, cpf, age, 0, skills
        )

        println "\n\n" + newCandidate
        naturalPersonService.addUser(newCandidate)
    }

    void update(BufferedReader br) {
        this.read()
        Integer id = readIntegerInput(br, "Informe o id do Candidato:")

        List<SkillEntity> skills = []
        NaturalPersonEntity person = naturalPersonService.oneById(id)
        if (!person) {
            println "$id NÃO EXISTE"
            return
        }

        person = updatePersonDetails(br, person)
        skills.addAll(readSkills(br))

        if (!skills.empty) {
            person.setSkills(skills)
        }

        naturalPersonService.updateById(person)
    }

    void delete(BufferedReader br) {
        this.read()
        Integer id = readIntegerInput(br, "Informe o id do Candidato:")

        NaturalPersonEntity person = naturalPersonService.oneById(id)
        if (!person) {
            println "$id NÃO EXISTE"
        } else {
            naturalPersonService.deleteById(person)
        }
    }

    private String readNonEmptyInput(BufferedReader br, String prompt) {
        print prompt
        String input = br.readLine()
        while (input.isEmpty()) {
            print prompt
            input = br.readLine()
        }
        return input
    }

    private AddressEntity readAddress(BufferedReader br) {
        print "Endereço:\n\tPais:"
        String country = readNonEmptyInput(br, "\tPais:")
        String state = readNonEmptyInput(br, "\tEstado:")
        String cep = readNonEmptyInput(br, "\tCEP:")
        return new AddressEntity(country, state, cep)
    }

    private List<SkillEntity> readSkills(BufferedReader br) {
        List<SkillEntity> skills = []
        new SkillsUI().read()
        println "caso não queira adicionar um competencia, aperte ENTER"
        int count = 1
        print "Competencia #$count: "
        String idSkill = br.readLine()
        while (!idSkill.isEmpty()) {
            SkillEntity skill = skillService.oneById(idSkill.toInteger())
            if (skill != null) {
                skills.add(skill)
                count++
            } else {
                println "id de Competencia Inválido"
            }
            print "Competencia #$count: "
            idSkill = br.readLine()
        }
        return skills
    }

    private int readIntegerInput(BufferedReader br, String prompt) {
        print prompt
        String input = br.readLine()
        while (input.isEmpty()) {
            print prompt
            input = br.readLine()
        }
        return input.toInteger()
    }

    private void updatePersonDetails(BufferedReader br, NaturalPersonEntity person) {
        def updateField = { field, prompt ->
            print "${prompt}(${field}):"
            String value = br.readLine()
            if (!value.isEmpty()) {
                field = value
            }
            field
        }

        person.name = updateField(person.name, "Nome do Candidato")
        person.email = updateField(person.email, "Email do Candidato")
        person.password = updateField(person.password, "Senha")
        person.description = updateField(person.description, "Descrição")
        person.address.country = updateField(person.address.country, "Endereço:\n\tPais")
        person.address.state = updateField(person.address.state, "\tEstado")
        person.address.cep = updateField(person.address.cep, "\tCEP")
        person.cpf = updateField(person.cpf, "CPF")
        person.age = updateField(person.age, "Idade")
    }
}
