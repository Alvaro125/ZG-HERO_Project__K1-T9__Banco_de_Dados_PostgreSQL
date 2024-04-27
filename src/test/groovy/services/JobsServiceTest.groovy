package services

import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.services.JobsService
import org.example.services.LegalPersonService
import org.example.services.SkillService
import spock.lang.AutoCleanup
import spock.lang.Specification
import utils.DBTest

import java.sql.Connection

class JobsServiceTest extends Specification {
    @AutoCleanup
    Connection conn

    def setup() {
        conn = DBTest.getConnection()
    }

    def "should add a job"() {
        given:
        JobsService jobsService = new JobsService(conn)
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        AddressEntity address = new AddressEntity("Country", "State", "12345678")
        LegalPersonEntity person = legalPersonService.oneById(1)
        JobEntity job = new JobEntity(
                "title job",
                "description job",
                address,
                person,
                0
        )

        when:
        jobsService.addJob(job)
        List<JobEntity> jobs = jobsService.listAll()

        then:
        jobs.size() == 1
    }

    def "should get a job by id"() {
        given:
        JobsService jobsService = new JobsService(conn)
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        AddressEntity address = new AddressEntity("Country", "State", "12345678")
        LegalPersonEntity person = legalPersonService.oneById(1)
        JobEntity job = new JobEntity(
                "title job",
                "description job",
                address,
                person,
                0
        )
        jobsService.addJob(job)
        job.name = "other title job"
        job.description = "other description job"
        jobsService.addJob(job)

        when:
        JobEntity retrievedJob = jobsService.oneById(2)

        then:
        retrievedJob.name == "other title job"
        retrievedJob.description == "other description job"
    }

    def "should upgrade one job per id"() {
        given:
        JobsService jobsService = new JobsService(conn)
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        AddressEntity address = new AddressEntity("Country", "State", "12345678")
        LegalPersonEntity person = legalPersonService.oneById(1)
        JobEntity newJob = new JobEntity(
                "title job",
                "description job",
                address,
                person,
                0
        )
        jobsService.addJob(newJob)
        newJob.name = "other title job"
        newJob.description = "other description job"
        jobsService.addJob(newJob)
        String title = "new title job"
        String description = "new description job"

        when:
        JobEntity job = jobsService.oneById(1)
        job.setName(title)
        job.setDescription(description)
        jobsService.updateById(job)
        List<JobEntity> jobs = jobsService.listAll()

        then:
        noExceptionThrown()
        jobs.size() == 2
        jobs.first().name == title
        jobs.first().description == description
    }

    def cleanup() {conn.close()}
}

