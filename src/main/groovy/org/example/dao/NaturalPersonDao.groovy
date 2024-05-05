package org.example.dao

import org.example.entity.NaturalPersonEntity

import java.sql.Connection

interface NaturalPersonDao extends LoginDao<NaturalPersonEntity>{

    List<NaturalPersonEntity> getAll()
    NaturalPersonEntity create(NaturalPersonEntity person)
    NaturalPersonEntity getById(Integer id)
    void updateById(NaturalPersonEntity person)
    void deleteById(Integer id)

}
