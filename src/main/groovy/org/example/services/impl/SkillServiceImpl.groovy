package org.example.services.impl


import org.example.dao.SkillDao
import org.example.entity.SkillEntity
import org.example.services.SkillService

public class SkillServiceImpl implements SkillService{
    private SkillDao skillDao

    SkillServiceImpl(SkillDao skillDao){
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
        skillDao.deleteByIdPerson(idPerson)
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

    void updateById(SkillEntity skill) {
        skillDao.updateById(skill)
    }

    void deleteAllbyPerson(Integer id) {
        skillDao.deleteByIdPerson(id)
    }

    void deleteById(Integer id) {
        this.deleteAllbyPerson(id)
        skillDao.deleteById(id)
    }
}
