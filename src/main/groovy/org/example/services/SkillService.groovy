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

public class SkillService {
    private SkillDao skillDao

    SkillService(SkillDao skillDao){
        this.skillDao = skillDao
    }


    void addSkill(SkillEntity skill) {
        skillDao.create(skill)
    }

    void addSkillByPerson(Integer idPerson, List<SkillEntity> skills) {
        for (SkillEntity skill : skills) {
            skillDao.insertToPerson(skill,idPerson)
        }
    }

    void deleteSkillByPerson(Integer idPerson) {
        try {
            String sql = """DELETE FROM skills_people WHERE people_id = ?""";
            PreparedStatement command = db.prepareStatement(sql);
            command.setInt(1, idPerson);
            command.executeUpdate();
            command.close();
        } catch (SQLException exceção_sql) {
            exceção_sql.printStackTrace();
        }
    }


    List<SkillEntity> listSkills() {
        return skillDao.getAll()
    }

    List<SkillEntity> listSkillsByPerson(Integer idPerson) {
        return skillDao.getByIdPerson(idPerson)
    }

    SkillEntity oneById(Integer id) {
        return skillDao.getById(id);
    }

    def updateById(SkillEntity skill) {
        skillDao.updateById(skill)
    }

    def deleteAllbyPerson(Integer id) {
        skillDao.deleteByIdPerson(id)
    }

    def deleteById(Integer id) {
        this.deleteAllbyPerson(id)
        skillDao.deleteById(id)
    }
}
