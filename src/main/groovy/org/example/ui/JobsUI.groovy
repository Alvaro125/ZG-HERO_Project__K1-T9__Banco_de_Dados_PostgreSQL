package org.example.ui

import org.example.config.Database
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.services.JobsService
import org.example.services.LegalPersonService
import org.example.services.SkillService

class JobsUI {
    private static SkillService skillService
    private static JobsService jobsService
    private static LegalPersonService legalPersonService

    JobsUI(){
        skillService = new SkillService(Database.conn)
        legalPersonService = new LegalPersonService(Database.conn)
        jobsService = new JobsService(Database.conn)
    }
    void read() {
        println """
@@Lista de Vagas@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
${jobsService.listAll().join("\n")}
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        JobEntity job = null
        LegalPersonEntity person = null
        new LegalPersonUI().read()
        print "Empresa: "
        String idPerson = br.readLine()
        if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        while (idPerson.isEmpty() || !person){
            if (!person) {
                println "Id da Empresa Inválido"
            }
            print "Empresa: "
            idPerson = br.readLine()
            if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        }
        print "Titulo do Emprego:"
        String name = br.readLine()
        while (name.isEmpty()){
            print "Titulo do Emprego:"
            name = br.readLine()
        }
        print "Descrição do Emprego:"
        String description = br.readLine()
        while (description.isEmpty()){
            print "Descrição do Emprego:"
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
        job = new JobEntity(name,description,
        new AddressEntity(country,state,cep),person,0)
        jobsService.addJob(job)
    }

    void update(BufferedReader br) {
        JobEntity job = null
        LegalPersonEntity person = null
        new LegalPersonUI().read()
        print "Empresa: "
        String idPerson = br.readLine()
        if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        while (idPerson.isEmpty() || !person){
            if (!person) {
                println "Id da Empresa Inválido"
            }
            print "Empresa: "
            idPerson = br.readLine()
            if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        }
        println jobsService.listByPerson(idPerson.toInteger()).join("\n")

        print "Vaga: "
        String idJob = br.readLine()
        if (!idJob.isEmpty()) job = jobsService.onebyId(idJob.toInteger())
        while (idJob.isEmpty() || !person){
            if (!person) {
                println "Id da Vaga Inválido"
            }
            print "Vaga: "
            idJob = br.readLine()
            if (!idJob.isEmpty()) job = jobsService.onebyId(idJob.toInteger())
        }

        print "Titulo do Emprego(${job.name}):"
        String name = br.readLine()
        if (!name.isEmpty()){
            job.setName(name)
        }
        print "Descrição do Emprego(${job.description}):"
        String description = br.readLine()
        if (!description.isEmpty()){
            job.setDescription(description)
        }
        print "Endereço:\n\tPais(${job.local.country}):"
        String country = br.readLine()
        if (!country.isEmpty()){
            job.local.setCountry(country)
        }
        print "\tEstado(${job.local.state}):"
        String state = br.readLine()
        if (!state.isEmpty()){
            job.local.setState(state)
        }
        print "\tCEP(${job.local.cep}):"
        String cep = br.readLine()
        if (!cep.isEmpty()){
            job.local.setCep(cep)
        }
        jobsService.updateById(job)
    }

    void delete(BufferedReader br) {
        JobEntity job = null
        LegalPersonEntity person = null
        new LegalPersonUI().read()
        print "Empresa: "
        String idPerson = br.readLine()
        if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        while (idPerson.isEmpty() || !person){
            if (!person) {
                println "Id da Empresa Inválido"
            }
            print "Empresa: "
            idPerson = br.readLine()
            if (!idPerson.isEmpty()) person = legalPersonService.onebyId(idPerson.toInteger())
        }
        println jobsService.listByPerson(idPerson.toInteger()).join("\n")

        print "Vaga: "
        String idJob = br.readLine()
        if (!idJob.isEmpty()) job = jobsService.onebyId(idJob.toInteger())
        while (idJob.isEmpty() || !person){
            if (!person) {
                println "Id da Vaga Inválido"
            }
            print "Vaga: "
            idJob = br.readLine()
            if (!idJob.isEmpty()) job = jobsService.onebyId(idJob.toInteger())
        }
        jobsService.deleteById(job)
    }
}
