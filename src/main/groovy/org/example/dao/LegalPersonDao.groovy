package org.example.dao

import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity

interface LegalPersonDao extends LoginDao<LegalPersonEntity>{

    List<LegalPersonEntity> getAll()
    LegalPersonEntity create(LegalPersonEntity person)
    LegalPersonEntity getById(Integer id)
    void updateById(LegalPersonEntity person)
    void deleteById(Integer id)

}
