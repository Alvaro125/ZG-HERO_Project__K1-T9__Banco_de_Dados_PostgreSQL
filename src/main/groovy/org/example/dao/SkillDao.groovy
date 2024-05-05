package org.example.dao

import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

interface SkillDao {
    List<SkillEntity> getAll()
    SkillEntity create(SkillEntity skill)
    List<SkillEntity> insertToPerson(SkillEntity skill, Integer id)
    SkillEntity getById(Integer id)
    List<SkillEntity> getByIdPerson(Integer id)
    void updateById(SkillEntity person)
    void deleteById(Integer id)
    void deleteByIdPerson(Integer idPerson)
}