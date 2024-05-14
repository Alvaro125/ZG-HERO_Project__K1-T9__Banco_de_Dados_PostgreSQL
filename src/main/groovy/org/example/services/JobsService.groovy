package org.example.services

import org.example.config.Database
import org.example.dao.AddressDao
import org.example.dao.JobDao
import org.example.dao.LegalPersonDao
import org.example.dao.SkillDao
import org.example.entity.AddressEntity
import org.example.entity.JobEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.SkillEntity
import org.example.factorys.ServiceFactory
import org.example.ui.JobsUI

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

interface JobsService {

    List<JobEntity> listAll()
    List<JobEntity> listByPerson(Integer idPerson)
    JobEntity oneById(Integer id)
    void updateById(JobEntity job)
    void addJob(JobEntity job)
    def deleteById(JobEntity job)
}
