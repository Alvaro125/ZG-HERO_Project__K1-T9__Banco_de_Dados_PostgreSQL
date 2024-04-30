package services

import org.example.config.Database
import org.example.dao.SkillDao
import org.example.dao.impl.SkillDaoImpl
import org.example.entity.SkillEntity
import org.example.services.SkillService
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import utils.DBTest

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import static org.mockito.Mockito.*

class SkillServiceTest extends Specification {
    @AutoCleanup
    Connection conn

    SkillDao skillDao
    def setup() {
        conn = DBTest.getConnection()
        skillDao = new SkillDaoImpl(conn)
    }

    def "should add a skill"() {
        given:
        SkillService skillService = new SkillService(skillDao)

        when:
        skillService.addSkill(new SkillEntity("Groovy", "Programming language"))
        List<SkillEntity> skills = skillService.listSkills()

        then:
        noExceptionThrown()
        skills.size() == 7
        skills[6].title == "Groovy"
        skills[6].description == "Programming language"
    }

    def "should get a skill by id"() {
        given:
        SkillService skillService = new SkillService(skillDao)

        when:
        SkillEntity skill = skillService.oneById(1)

        then:
        skill.title == "Java"
        skill.description == "Programming language"
    }

    def "should upgrade one skill per id"() {
        given:
        SkillService skillService = new SkillService(skillDao)
        String title = "CSS"
        String description = "Language Cascading Style Sheet"

        when:
        SkillEntity skill = skillService.oneById(1)
        skill.setTitle(title)
        skill.setDescription(description)
        skillService.updateById(skill)
        List<SkillEntity> skills = skillService.listSkills()

        then:
        noExceptionThrown()
        skills.size() == 6
        skills.first().title == title
        skills.first().description == description
    }

    def "should add a skill and link to a person"() {
        given:
        SkillService skillService = new SkillService(skillDao)
        String title = "Groovy"
        String description = "Programming language"

        when:
        skillService.addSkill(new SkillEntity(title,description))
        List<SkillEntity> skills = [skillService.oneById(7)]
        skillService.addSkillByPerson(1, skills)
        List<SkillEntity> skillsByPerson = skillService.listSkillsByPerson(1)

        then:
        skillsByPerson.size() == 3
        skillsByPerson.last().title == title
        skillsByPerson.last().description == description
    }

    def "should delete a skill in links to people"() {
        given:
        SkillService skillService = new SkillService(skillDao)
        Integer idPerson = 1
        Integer id = 1

        when:
        List<SkillEntity> skillsByPerson1 = skillService.listSkillsByPerson(idPerson)

        then:
        skillsByPerson1.size() == 2

        when:
        skillService.deleteAllbyPerson(id)
        List<SkillEntity> skillsByPerson = skillService.listSkillsByPerson(1)

        then:
        skillsByPerson.size() == 1
    }
    def "should delete a skill and its links to people"() {
        given:
        SkillService skillService = new SkillService(skillDao)
        Integer idPerson = 1
        Integer id = 1

        when:
        List<SkillEntity> skillsByPerson1 = skillService.listSkillsByPerson(idPerson)
        List<SkillEntity> skills1 = skillService.listSkills()

        then:
        skillsByPerson1.size() == 2
        skills1.size() == 6

        when:
        skillService.deleteById(id)
        List<SkillEntity> skillsByPerson = skillService.listSkillsByPerson(1)
        List<SkillEntity> skills = skillService.listSkills()

        then:
        skillsByPerson.size() == 1
        skills.size() == 5
    }

    def cleanup() {conn.close()}
}

