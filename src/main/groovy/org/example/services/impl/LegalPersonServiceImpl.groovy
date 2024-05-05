package org.example.services.impl


import org.example.dao.AddressDao
import org.example.dao.LegalPersonDao
import org.example.dao.PersonDao
import org.example.entity.LegalPersonEntity
import org.example.services.LegalPersonService

class LegalPersonServiceImpl implements LegalPersonService{

    private LegalPersonDao legalPersonDao
    private PersonDao personDao
    private AddressDao addressDao

    LegalPersonServiceImpl(LegalPersonDao legalPersonDao,
                           AddressDao addressDao, PersonDao personDao){
        this.legalPersonDao = legalPersonDao
        this.addressDao = addressDao
        this.personDao = personDao
    }

    List<LegalPersonEntity> listAll() {
        return legalPersonDao.all
    }

    LegalPersonEntity oneById(Integer id) {
        return legalPersonDao.getById(id)
    }

    void updateById(LegalPersonEntity person) {
        addressDao.updateById(person.address)
        personDao.updateById(person)
        legalPersonDao.updateById(person)
    }

    void addUser(LegalPersonEntity person) {
        person.address = addressDao.create(person.address)
        person.id = personDao.create(person).getId()
        legalPersonDao.create(person)
    }
    void deleteById(LegalPersonEntity person) {
        legalPersonDao.deleteById(person.id)
        personDao.deleteById(person.id)
        addressDao.deleteById(person.address.id)
    }
}
