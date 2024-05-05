package org.example.dao

import org.example.entity.AddressEntity
import org.example.entity.PersonEntity

interface PersonDao {
    PersonEntity create(PersonEntity person)
    PersonEntity getById(Integer id)
    void deleteById(Integer id)
    void updateById(PersonEntity person)
}
