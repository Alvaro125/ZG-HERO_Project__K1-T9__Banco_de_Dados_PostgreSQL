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
    private static NaturalPersonService naturalPersonService
    private static SkillService skillService

    NaturalPersonUI(){
        naturalPersonService = new NaturalPersonService(Database.conn)
        skillService = new SkillService(Database.conn)
    }
    void read() {
        println """
@@Lista de Empresas@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
${naturalPersonService.listAll().join("\n")}
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        List<SkillEntity> skills = []
        print "Nome do Candidato:"
        String name = br.readLine()
        while (name.isEmpty()){
            print "Nome do Candidato:"
            name = br.readLine()
        }
        print "Email do Candidato:"
        String email = br.readLine()
        while (email.isEmpty()){
            print "Email do Candidato:"
            email = br.readLine()
        }
        print "Senha:"
        String password = br.readLine()
        while (password.isEmpty()){
            print "Senha:"
            email = br.readLine()
        }
        print "Descrição:"
        String description = br.readLine()
        while (description.isEmpty()){
            print "Descrição:"
            description = br.readLine()
        }
        print "Endereço:\n\tPais:"
        String country = br.readLine()
        while (country.isEmpty()){
            print "\tPais:"
            country = br.readLine()
        }
        print "\tEstado:"
        String state = br.readLine()
        while (state.isEmpty()){
            print "\tEstado:"
            state = br.readLine()
        }
        print "\tCEP:"
        String cep = br.readLine()
        while (cep.isEmpty()){
            print "\tCEP:"
            cep = br.readLine()
        }
        new SkillsUI().read()
        println "caso não queira adicionar um competencia, aperte ENTER"
        int count = 1
        print "Competencia #$count: "
        SkillEntity skill = null
        String idSkill = br.readLine()
        while (!idSkill.isEmpty()){
            skill = skillService.oneById(idSkill.toInteger())
            if (skill!=null) {
                skills.add(skill)
                count++
            }else{
                println "id de Competencia Inválido"
            }
            print "Competencia #$count: "
            idSkill = br.readLine()
        }
        print "CPF:"
        String cpf = br.readLine()
        while (cpf.isEmpty()){
            print "CPF:"
            cpf = br.readLine()
        }

        print "Idade:"
        String age = br.readLine()
        while (age.isEmpty()){
            print "Idade:"
            age = br.readLine()
        }

        NaturalPersonEntity new_candidate = new NaturalPersonEntity(
                name, email, password, description, new AddressEntity(country,state,cep),
                cpf,age.toInteger(),0,skills
        )

        println "\n\n"+new_candidate
        naturalPersonService.addUser(new_candidate)
    }

    void update(BufferedReader br) {
        this.read()
        print "Informe o id do Candidato:"
        Integer id = br.readLine().toInteger()

        List<SkillEntity> skills = []
        NaturalPersonEntity person = naturalPersonService.onebyId(id)
        if(!person){
            println "$id NÃO EXISTE"
        }else{
            print "Nome do Candidato(${person.name}):"
            String name = br.readLine()
            if (!name.isEmpty()){
                person.setName(name)
            }
            print "Email do Candidato(${person.email}):"
            String email = br.readLine()
            if (!email.isEmpty()){
                person.setEmail(email)
            }
            print "Senha(${person.password}):"
            String password = br.readLine()
            if (!password.isEmpty()){
                person.setPassword(password)
            }
            print "Descrição(${person.description}):"
            String description = br.readLine()
            if (!description.isEmpty()){
                person.setDescription(description)
            }
            print "Endereço:\n\tPais(${person.address.country}):"
            String country = br.readLine()
            if (!country.isEmpty()){
                person.address.setCountry(country)
            }
            print "\tEstado:(${person.address.state})"
            String state = br.readLine()
            if (!state.isEmpty()){
                person.address.setState(state)
            }
            print "\tCEP:(${person.address.cep})"
            String cep = br.readLine()
            if (!cep.isEmpty()){
                person.address.setCep(cep)
            }
            new SkillsUI().read()
            println "caso não queira adicionar um competencia, aperte ENTER"
            int count = 1
            print "Competencia #$count: "
            SkillEntity skill = null
            String idSkill = br.readLine()
            while (!idSkill.isEmpty()){
                skill = skillService.oneById(idSkill.toInteger())
                if (skill!=null) {
                    skills.add(skill)
                    count++
                }else{
                    println "id de Competencia Inválido"
                }
                print "Competencia #$count: "
                idSkill = br.readLine()
            }
            if (skills.size()){
                person.setSkills(skills)
            }

            print "CPF(${person.cpf}):"
            String cpf = br.readLine()
            if (!cpf.isEmpty()){
                person.setCpf(cpf)
            }

            print "Idade(${person.age}):"
            String age = br.readLine()
            if (!age.isEmpty()){
                person.setAge(age.toInteger())
            }
            naturalPersonService.updateById(person)
        }
    }
    void delete(BufferedReader br) {
        this.read()
        print "Informe o id do Candidato:"
        Integer id = br.readLine().toInteger()

        NaturalPersonEntity person = naturalPersonService.onebyId(id)
        if(!person){
            println "$id NÃO EXISTE"
        }else{
            naturalPersonService.deleteById(person)
        }
    }
}
