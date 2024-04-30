package org.example.dao

import org.example.entity.NaturalPersonEntity

import java.sql.Connection

interface NaturalPersonDao {

    List<NaturalPersonEntity> getAll()
    NaturalPersonEntity create(NaturalPersonEntity person)
    NaturalPersonEntity getById(Integer id)
    def updateById(NaturalPersonEntity person)
    def deleteById(Integer id)

}
