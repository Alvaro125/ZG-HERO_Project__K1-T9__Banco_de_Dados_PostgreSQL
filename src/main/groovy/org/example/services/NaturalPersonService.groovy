package org.example.services

import org.example.config.Database
import org.example.dao.AddressDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.dao.impl.NaturalPersonDaoImpl
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class NaturalPersonService {

    private NaturalPersonDao naturalPersonDao
    private PersonDao personDao
    private AddressDao addressDao

    NaturalPersonService(NaturalPersonDao naturalPersonDao,
                         AddressDao addressDao, PersonDao personDao){
        this.naturalPersonDao = naturalPersonDao
        this.addressDao = addressDao
        this.personDao = personDao
    }

    List<NaturalPersonEntity> listAll() {
        return naturalPersonDao.getAll()
    }


    NaturalPersonEntity oneById(Integer id) {
        return naturalPersonDao.getById(id)
    }

    def updateById(NaturalPersonEntity person) {
        addressDao.updateById(person.address)
        personDao.updateById(person)
        naturalPersonDao.updateById(person)
    }

    def addUser(NaturalPersonEntity person) {
        person.address = addressDao.create(person.address)
        person.id = personDao.create(person).getId()
        naturalPersonDao.create(person)
    }

    def deleteById(NaturalPersonEntity person) {
        naturalPersonDao.deleteById(person.id)
        personDao.deleteById(person.id)
        addressDao.deleteById(person.address.id)
    }
}
