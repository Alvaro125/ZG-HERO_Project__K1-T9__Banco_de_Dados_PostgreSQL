package services

import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.services.LegalPersonService
import org.example.services.NaturalPersonService
import spock.lang.AutoCleanup
import spock.lang.Specification
import utils.DBTest

import java.sql.Connection

class LegalPersonServiceTest extends Specification {

    @AutoCleanup
    Connection conn

    def setup() {
        conn = DBTest.getConnection()
    }


    def "should add and retrieve a legal person"() {
        given:
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new LegalPersonEntity(
                "John Enterprise",
                "email@example.com",
                "password",
                "Description",
                address,
                "27558685000142",
                0
        )

        when:
        legalPersonService.addUser(person)
        LegalPersonEntity retrievedPerson = legalPersonService.oneById(11)
        List<LegalPersonEntity> people = legalPersonService.listAll()

        then:
        people.size() == 6
        people.last().email == retrievedPerson.email
        people.last().email == "email@example.com"
        people.last().name == "John Enterprise"
        people.last().description == "Description"
        people.last().address.country == "Country"
        people.last().address.state == "State"
        people.last().address.cep == "12345678"
        people.last().cnpj == "27558685000142"
    }

    def "should update a legal person"() {
        given:
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new LegalPersonEntity(
                "John Enterprise",
                "email@example.com",
                "password",
                "Description",
                address,
                "27558685000142",
                0
        )

        legalPersonService.addUser(person)
        person = legalPersonService.oneById(11)

        when:
        person.name = "Jane Enterprise"
        person.description = "New Description"
        person.address.country = "New Country"
        person.address.state = "New State"
        person.address.cep = "87654321"
        person.cnpj = "27558685000142"
        legalPersonService.updateById(person)
        LegalPersonEntity updatedPerson = legalPersonService.oneById(11)

        then:
        updatedPerson.id == 11
        updatedPerson.email == "email@example.com"
        updatedPerson.name == "Jane Enterprise"
        updatedPerson.description == "New Description"
        updatedPerson.address.country == "New Country"
        updatedPerson.address.state == "New State"
        updatedPerson.address.cep == "87654321"
        updatedPerson.cnpj == "27558685000142"
    }

    def "should delete a legal person"() {
        given:
        LegalPersonService legalPersonService = new LegalPersonService(conn)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new LegalPersonEntity(
                "John Enterprise",
                "email@example.com",
                "password",
                "Description",
                address,
                "27558685000142",
                0
        )
        legalPersonService.addUser(person)
        person = legalPersonService.oneById(11)

        when:
        legalPersonService.deleteById(person)
        LegalPersonEntity deletedPerson = legalPersonService.oneById(11)

        then:
        deletedPerson == null
    }

    def cleanup() {
        conn.close()
    }
}
