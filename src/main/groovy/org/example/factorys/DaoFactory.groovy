package org.example.factorys

import org.example.config.Database
import org.example.dao.AddressDao
import org.example.dao.JobDao
import org.example.dao.LegalPersonDao
import org.example.dao.LoginDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.dao.SkillDao
import org.example.dao.impl.AddressDaoImpl
import org.example.dao.impl.JobDaoImpl
import org.example.dao.impl.LegalPersonDaoImpl
import org.example.dao.impl.NaturalPersonDaoImpl
import org.example.dao.impl.PersonDaoImpl
import org.example.dao.impl.SkillDaoImpl
import org.example.services.LoginService
import org.example.services.impl.LegalPersonServiceImpl

import java.sql.Connection

class DaoFactory {

    static NaturalPersonDao createNaturalPerson() {
        Connection conn = Database.conn
        return new NaturalPersonDaoImpl(conn)
    }
    static LegalPersonDao createLegalPerson() {
        Connection conn = Database.conn
        return new LegalPersonDaoImpl(conn)
    }
    static AddressDao createAddress() {
        Connection conn = Database.conn
        return new AddressDaoImpl(conn)
    }
    static PersonDao createPerson() {
        Connection conn = Database.conn
        return new PersonDaoImpl(conn)
    }
    static SkillDao createSkill() {
        Connection conn = Database.conn
        return new SkillDaoImpl(conn)
    }
    static JobDao createJob() {
        Connection conn = Database.conn
        return new JobDaoImpl(conn)
    }
}