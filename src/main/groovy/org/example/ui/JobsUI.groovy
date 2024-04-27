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
    private static SkillService skillService = new SkillService(Database.conn)
    private static JobsService jobsService = new JobsService(Database.conn)
    private static LegalPersonService legalPersonService = new LegalPersonService(Database.conn)

    void read() {
        println """
@@Lista de Vagas@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
${jobsService.listAll().join("\n")}
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"""
    }

    void create(BufferedReader br) {
        LegalPersonEntity person = getValidLegalPerson(br)
        if (person == null) {
            return
        }
        String name = readNonEmptyInput(br, "Titulo do Emprego:")
        String description = readNonEmptyInput(br, "Descrição do Emprego:")
        AddressEntity address = getAddressFromInput(br)
        JobEntity job = new JobEntity(name, description, address, person, 0)
        jobsService.addJob(job)
    }

    void update(BufferedReader br) {
        LegalPersonEntity person = getValidLegalPerson(br)
        if (person == null) {
            return
        }

        println jobsService.listByPerson(person.id).join("\n")
        print "Vaga: "
        JobEntity job = getValidJob(br, person)
        if (job == null) {
            return
        }

        updateJobDetails(br, job)
        jobsService.updateById(job)
    }

    void delete(BufferedReader br) {
        LegalPersonEntity person = getValidLegalPerson(br)
        if (person == null) {
            return
        }

        println jobsService.listByPerson(person.id).join("\n")
        print "Vaga: "
        JobEntity job = getValidJob(br, person)
        if (job == null) {
            return
        }

        jobsService.deleteById(job)
    }

    private JobEntity getValidJob(BufferedReader br, LegalPersonEntity person) {
        JobEntity job = null
        while (true) {
            String idJob = br.readLine()
            if (idJob.isEmpty()) {
                return null
            }

            job = jobsService.onebyId(idJob.toInteger())
            if (job == null || job.person.id != person.id) {
                println "Id da Vaga Inválido"
            } else {
                return job
            }
        }
    }

    private JobEntity updateJobDetails(BufferedReader br, JobEntity job) {
        String name = readNonEmptyInput(br, "Titulo do Emprego(${job.name}):")
        job.setName(name)
        String description = readNonEmptyInput(br, "Descrição do Emprego(${job.description}):")
        job.setDescription(description)

        AddressEntity address = getAddressFromInput(br)
        job.local = address
        job
    }


    private LegalPersonEntity getValidLegalPerson(BufferedReader br) {
        LegalPersonEntity person = null
        new LegalPersonUI().read()
        while (true) {
            print "Empresa: "
            String idPerson = br.readLine()
            if (idPerson.isEmpty()) {
                return null
            }

            person = legalPersonService.onebyId(idPerson.toInteger())
            if (person == null) {
                println "Id da Empresa Inválido"
            } else {
                return person
            }
        }
    }

    private AddressEntity getAddressFromInput(BufferedReader br) {
        String country = readNonEmptyInput(br, "Endereço:\n\tPais")
        String state = readNonEmptyInput(br, "\tEstado:")
        String cep = readNonEmptyInput(br, "\tCEP:")
        new AddressEntity(country, state, cep)
    }

    private String readNonEmptyInput(BufferedReader br, String prompt) {
        String input
        do {
            print prompt
            input = br.readLine()
        } while (input.isEmpty())
        input
    }
}