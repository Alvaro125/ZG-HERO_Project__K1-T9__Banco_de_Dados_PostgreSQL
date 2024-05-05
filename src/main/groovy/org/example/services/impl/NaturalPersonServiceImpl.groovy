package org.example.services.impl


import org.example.dao.AddressDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.entity.NaturalPersonEntity
import org.example.services.NaturalPersonService

class NaturalPersonServiceImpl implements NaturalPersonService{

    private NaturalPersonDao naturalPersonDao
    private PersonDao personDao
    private AddressDao addressDao

    NaturalPersonServiceImpl(NaturalPersonDao naturalPersonDao,
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

    void updateById(NaturalPersonEntity person) {
        addressDao.updateById(person.address)
        personDao.updateById(person)
        naturalPersonDao.updateById(person)
    }

    void addUser(NaturalPersonEntity person) {
        person.address = addressDao.create(person.address)
        person.id = personDao.create(person).getId()
        naturalPersonDao.create(person)
    }

    void deleteById(NaturalPersonEntity person) {
        naturalPersonDao.deleteById(person.id)
        personDao.deleteById(person.id)
        addressDao.deleteById(person.address.id)
    }
}
