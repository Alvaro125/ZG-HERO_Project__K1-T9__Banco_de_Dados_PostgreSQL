package org.example.services

import org.example.config.Database
import org.example.dao.AddressDao
import org.example.dao.LegalPersonDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.PersonEntity
import org.example.entity.SkillEntity
import org.example.interfaces.IPerson

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

interface LegalPersonService{

    List<LegalPersonEntity> listAll()
    LegalPersonEntity oneById(Integer id)
    void updateById(LegalPersonEntity person)
    void addUser(LegalPersonEntity person)
    void deleteById(LegalPersonEntity person)
}
