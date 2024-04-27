package org.example.ui

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.services.LegalPersonService
import org.example.services.SkillService

class LegalPersonUI {
    private static LegalPersonService legalPersonService = new LegalPersonService(Database.conn)
    private static SkillService skillService = new SkillService(Database.conn)

    void read() {
        println """
            @@Lista de Empresas@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            ${legalPersonService.listAll().join("\n")}
            @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        String name = readNonEmptyInput(br, "Nome da Empresa:")
        String email = readNonEmptyInput(br, "Email da Empresa:")
        String password = readNonEmptyInput(br, "Senha:")
        String description = readNonEmptyInput(br, "Descrição:")
        AddressEntity address = readAddress(br)
        List<SkillEntity> skills = readSkills(br)

        String cnpj = readNonEmptyInput(br, "CNPJ:")

        LegalPersonEntity new_company = new LegalPersonEntity(
                name, email, password, description, address, cnpj, 0, skills
        )

        println "\n\n" + new_company
        legalPersonService.addUser(new_company)
    }

    void update(BufferedReader br) {
        this.read()
        print "Informe o id da Empresa:"
        Integer id = br.readLine().toInteger()

        LegalPersonEntity person = legalPersonService.onebyId(id)
        if (!person) {
            println "$id NÃO EXISTE"
            return
        }

        person = updatePersonDetails(br, person)
        List<SkillEntity> skills = readSkills(br)
        if (!skills.empty) {
            person.setSkills(skills)
        }

        legalPersonService.updateById(person)
    }

    void delete(BufferedReader br) {
        this.read()
        print "Informe o id da Empresa:"
        Integer id = br.readLine().toInteger()

        LegalPersonEntity person = legalPersonService.onebyId(id)
        if (!person) {
            println "$id NÃO EXISTE"
            return
        }

        legalPersonService.deleteById(person)
    }

    private String readNonEmptyInput(BufferedReader br, String prompt) {
        String input
        do {
            print prompt
            input = br.readLine()
        } while (input.isEmpty())
        input
    }

    private AddressEntity readAddress(BufferedReader br) {
        String country = readNonEmptyInput(br, "Endereço:\n\tPais:")
        String state = readNonEmptyInput(br, "\tEstado:")
        String cep = readNonEmptyInput(br, "\tCEP:")
        new AddressEntity(country, state, cep)
    }

    private List<SkillEntity> readSkills(BufferedReader br) {
        List<SkillEntity> skills = []
        new SkillsUI().read()
        println "caso não queira adicionar uma competência, aperte ENTER"
        int count = 1
        String idSkill
        do {
            print "Competência #$count: "
            idSkill = br.readLine()
            if (!idSkill.isEmpty()) {
                SkillEntity skill = skillService.oneById(idSkill.toInteger())
                if (skill) {
                    skills.add(skill)
                    count++
                } else {
                    println "ID de Competência Inválido"
                }
            }
        } while (!idSkill.isEmpty())
        skills
    }

    private LegalPersonEntity updatePersonDetails(BufferedReader br, LegalPersonEntity person) {
        def updateField = { field, prompt ->
            print "${prompt}(${field}):"
            String value = br.readLine()
            if (!value.isEmpty()) {
                field = value
            }
            field
        }

        person.name = updateField(person.name, "Nome da Empresa")
        person.email = updateField(person.email, "Email da Empresa")
        person.password = updateField(person.password, "Senha")
        person.description = updateField(person.description, "Descrição")
        person.address.country = updateField(person.address.country, "Endereço:\n\tPais")
        person.address.state = updateField(person.address.state, "\tEstado")
        person.address.cep = updateField(person.address.cep, "\tCEP")
        person.cnpj = updateField(person.cnpj, "CNPJ")
        return person
    }
}

