package org.example.dao

import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

interface SkillDao {
    List<SkillEntity> getAll()
    SkillEntity create(SkillEntity skill)
    def insertToPerson(SkillEntity skill, Integer id)
    SkillEntity getById(Integer id)
    List<SkillEntity> getByIdPerson(Integer id)
    def updateById(SkillEntity person)
    def deleteById(Integer id)
    def deleteByIdPerson(Integer idPerson)
}