package org.example.interfaces

import org.example.entity.AddressEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.PersonEntity

interface IPerson {
    List<PersonEntity> users

    List<PersonEntity> listAll()

    void addUser(PersonEntity person)
}