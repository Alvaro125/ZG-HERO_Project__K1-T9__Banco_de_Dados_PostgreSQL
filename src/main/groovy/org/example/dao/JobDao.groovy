package org.example.dao

import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.SkillEntity

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

interface JobDao {
    List<JobEntity> getAll()
    List<JobEntity> getByIdPerson(Integer idPerson)
    JobEntity getById(Integer id)
    void updateById(JobEntity job)
    JobEntity create(JobEntity job)
    void deleteById(Integer id)
}