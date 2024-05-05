package org.example.services

import org.example.config.Database
import org.example.dao.AddressDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.dao.impl.NaturalPersonDaoImpl
import org.example.entity.AddressEntity
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.entity.SkillEntity

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

interface NaturalPersonService {

    List<NaturalPersonEntity> listAll()

    NaturalPersonEntity oneById(Integer id)

    void updateById(NaturalPersonEntity person)

    void addUser(NaturalPersonEntity person)

    void deleteById(NaturalPersonEntity person)
}
