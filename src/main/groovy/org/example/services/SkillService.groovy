package org.example.services

import org.example.config.Database
import org.example.dao.SkillDao
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

interface SkillService {

    void addSkill(SkillEntity skill)
    void addSkillByPerson(Integer idPerson, List<SkillEntity> skills)
    void deleteSkillByPerson(Integer idPerson)
    List<SkillEntity> listSkills()
    List<SkillEntity> listSkillsByPerson(Integer idPerson)
    SkillEntity oneById(Integer id)
    void updateById(SkillEntity skill)
    void deleteAllbyPerson(Integer id)
    void deleteById(Integer id)
}
