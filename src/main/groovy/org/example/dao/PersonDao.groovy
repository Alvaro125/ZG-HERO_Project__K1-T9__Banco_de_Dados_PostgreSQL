package org.example.dao

import org.example.entity.AddressEntity
import org.example.entity.PersonEntity

interface PersonDao {
    PersonEntity create(PersonEntity person)
    PersonEntity getById(Integer id)
    def deleteById(Integer id)
    def updateById(PersonEntity person)
}
