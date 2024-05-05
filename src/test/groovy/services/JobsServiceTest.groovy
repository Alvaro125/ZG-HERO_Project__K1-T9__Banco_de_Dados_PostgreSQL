package services

import org.example.dao.AddressDao
import org.example.dao.JobDao
import org.example.dao.LegalPersonDao
import org.example.dao.PersonDao
import org.example.dao.impl.AddressDaoImpl
import org.example.dao.impl.JobDaoImpl
import org.example.dao.impl.LegalPersonDaoImpl
import org.example.dao.impl.PersonDaoImpl
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.services.JobsService
import org.example.services.LegalPersonService
import org.example.services.SkillService
import org.example.services.impl.LegalPersonServiceImpl
import spock.lang.AutoCleanup
import spock.lang.Specification
import utils.DBTest

import java.sql.Connection

class JobsServiceTest extends Specification {
    @AutoCleanup
    Connection conn

    LegalPersonDao legalPersonDao
    AddressDao addressDao
    PersonDao personDao
    JobDao jobDao

    def setup() {
        conn = DBTest.getConnection()
        legalPersonDao = new LegalPersonDaoImpl(conn)
        addressDao = new AddressDaoImpl(conn)
        personDao = new PersonDaoImpl(conn)
        jobDao = new JobDaoImpl(conn)
    }

    def "should add a job"() {
        given:
        JobsService jobsService = new JobsService(jobDao,legalPersonDao,addressDao)
        LegalPersonService legalPersonService = new LegalPersonServiceImpl(legalPersonDao,
                addressDao, personDao)
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
        JobsService jobsService = new JobsService(jobDao,legalPersonDao,addressDao)
        LegalPersonService legalPersonService = new LegalPersonServiceImpl(legalPersonDao,
                addressDao, personDao)
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
        JobsService jobsService = new JobsService(jobDao,legalPersonDao,addressDao)
        LegalPersonService legalPersonService = new LegalPersonServiceImpl(legalPersonDao,
                addressDao, personDao)
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
        job = jobsService.oneById(1)
        println(jobsService.listAll())
        job.name = "other title job"
        job.description = "other description job"
        jobsService.addJob(job)
        String title = "new title job"
        String description = "new description job"

        when:
        JobEntity job1 = jobsService.oneById(1)
        job1.setName(title)
        job1.setDescription(description)
        jobsService.updateById(job1)
        List<JobEntity> jobs = jobsService.listAll()

        then:
        noExceptionThrown()
        jobs.size() == 2
        jobs.first().name == title
        jobs.first().description == description
    }

    def cleanup() {conn.close()}
}

