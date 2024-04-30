package services

import org.example.dao.AddressDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.dao.impl.AddressDaoImpl
import org.example.dao.impl.NaturalPersonDaoImpl
import org.example.dao.impl.PersonDaoImpl
import org.example.entity.AddressEntity
import org.example.entity.NaturalPersonEntity
import org.example.services.NaturalPersonService
import spock.lang.AutoCleanup
import spock.lang.Specification
import spock.lang.Subject
import utils.DBTest

import java.sql.Connection

class NaturalPersonServiceTest extends Specification {

    @AutoCleanup
    Connection conn

    NaturalPersonDao naturalPersonDao
    AddressDao addressDao
    PersonDao personDao

    def setup() {
        conn = DBTest.getConnection()
        naturalPersonDao = new NaturalPersonDaoImpl(conn)
        addressDao = new AddressDaoImpl(conn)
        personDao = new PersonDaoImpl(conn)
    }


    def "should add and retrieve a natural person"() {
        given:
        NaturalPersonService naturalPersonService = new NaturalPersonService(naturalPersonDao,
                addressDao, personDao)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new NaturalPersonEntity(
                "John Doe",
                "email@example.com",
                "password",
                "Description",
                address,
                "12345678901",
                30,
                0
        )

        when:
        naturalPersonService.addUser(person)
        NaturalPersonEntity retrievedPerson = naturalPersonService.oneById(1)
        List<NaturalPersonEntity> people = naturalPersonService.listAll()

        then:
        people.size() == 6
        people.last().email == "email@example.com"
        people.last().name == "John Doe"
        people.last().description == "Description"
        people.last().address.country == "Country"
        people.last().address.state == "State"
        people.last().address.cep == "12345678"
        people.last().cpf == "12345678901"
        people.last().age == 30
    }

    def "should update a natural person"() {
        given:
        NaturalPersonService naturalPersonService = new NaturalPersonService(naturalPersonDao,
                addressDao, personDao)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new NaturalPersonEntity(
                "John Doe",
                "email@example.com",
                "password",
                "Description",
                address,
                "12345678901",
                30,
                0
        )

        naturalPersonService.addUser(person)
        person = naturalPersonService.oneById(11)

        when:
        person.name = "Jane Doe"
        person.description = "New Description"
        person.address.country = "New Country"
        person.address.state = "New State"
        person.address.cep = "87654321"
        person.cpf = "10987654321"
        person.age = 40
        naturalPersonService.updateById(person)
        NaturalPersonEntity updatedPerson = naturalPersonService.oneById(11)

        then:
        updatedPerson.id == 11
        updatedPerson.email == "email@example.com"
        updatedPerson.name == "Jane Doe"
        updatedPerson.description == "New Description"
        updatedPerson.address.country == "New Country"
        updatedPerson.address.state == "New State"
        updatedPerson.address.cep == "87654321"
        updatedPerson.cpf == "10987654321"
        updatedPerson.age == 40
    }

    def "should delete a natural person"() {
        given:
        NaturalPersonService naturalPersonService = new NaturalPersonService(naturalPersonDao,
                addressDao, personDao)
        def address = new AddressEntity("Country", "State", "12345678")
        def person = new NaturalPersonEntity(
                "John Doe",
                "email@example.com",
                "password",
                "Description",
                address,
                "12345678901",
                30,
                0
        )
        naturalPersonService.addUser(person)
        person = naturalPersonService.oneById(11)

        when:
        naturalPersonService.deleteById(person)
        NaturalPersonEntity deletedPerson = naturalPersonService.oneById(11)

        then:
        deletedPerson == null
    }

    def cleanup() {
        conn.close()
    }
}
