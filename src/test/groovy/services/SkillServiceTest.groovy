package services

import org.example.config.Database
import org.example.entity.SkillEntity
import org.example.services.SkillService
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import static org.mockito.Mockito.*

class SkillServiceTest extends Specification {
    @AutoCleanup
    Connection conn

    def setup() {
        conn = DriverManager.getConnection("jdbc:h2:mem:test;MODE=PostgreSQL", "sa", "")
        conn.createStatement().execute("""
            CREATE TABLE IF NOT EXISTS skills (
                id SERIAL PRIMARY KEY,
                title VARCHAR(255),
                description TEXT
            )
        """)
    }

    def "should add a skill"() {
        given:
        SkillService skillService = new SkillService(conn)

        when:
        skillService.addSkill("Java", "Programming language")
        List<SkillEntity> skills = skillService.listSkills()

        then:
        skills.size() == 1
        skills[0].title == "Java"
        skills[0].description == "Programming language"
    }

    def "should get a skill by id"() {
        given:
        SkillService skillService = new SkillService(conn)

        when:
        skillService.addSkill("Java", "Programming language")
        skillService.addSkill("Python", "Scripting language")
        SkillEntity skill = skillService.oneById(1)

        then:
        skill.title == "Java"
    }

    def "should upgrade one skill per id"() {
        given:
        SkillService skillService = new SkillService(conn)
        String title = "CSS"
        String description = "Language Cascading Style Sheet"

        when:
        skillService.addSkill("HTML", "Programming language")
        skillService.addSkill("Python", "Scripting language")
        SkillEntity skill = skillService.oneById(1)
        skill.setTitle(title)
        skill.setDescription(description)
        skillService.updateById(skill)
        List<SkillEntity> skills = skillService.listSkills()

        then:
        skills.size() == 2
        skills[0].title == "CSS"
        skills[0].description == "Language Cascading Style Sheet"
    }
    def cleanup() {
        conn.close()
    }
}

