package org.example.factorys

import org.example.dao.AddressDao
import org.example.dao.JobDao
import org.example.dao.LegalPersonDao
import org.example.dao.NaturalPersonDao
import org.example.dao.PersonDao
import org.example.dao.SkillDao
import org.example.services.JobsService
import org.example.services.LegalPersonService
import org.example.services.LoginService
import org.example.services.NaturalPersonService
import org.example.services.SkillService
import org.example.services.impl.LoginServiceImpl
import org.example.services.impl.NaturalPersonServiceImpl
import org.example.services.impl.SkillServiceImpl

class ServiceFactory {
    static LoginService createLogin() {
        NaturalPersonDao naturalPersonDao = DaoFactory.createNaturalPerson()
        return new LoginServiceImpl(naturalPersonDao)
    }
    static NaturalPersonService createNaturalPerson() {
        NaturalPersonDao naturalPersonDao = DaoFactory.createNaturalPerson()
        AddressDao addressDao = DaoFactory.createAddress()
        PersonDao personDao = DaoFactory.createPerson()
        return new NaturalPersonServiceImpl(naturalPersonDao,addressDao,personDao)
    }
    static LegalPersonService createLegalPerson() {
        LegalPersonDao legalPersonDao = DaoFactory.createLegalPerson()
        AddressDao addressDao = DaoFactory.createAddress()
        PersonDao personDao = DaoFactory.createPerson()
        return new NaturalPersonServiceImpl(legalPersonDao,addressDao,personDao)
    }
    static SkillService createSkill() {
        SkillDao skillDao = DaoFactory.createSkill()
        return new SkillServiceImpl(skillDao)
    }
    static JobsService createJob() {
        JobDao jobDao = DaoFactory.createJob()
        LegalPersonDao legalPersonDao = DaoFactory.createLegalPerson()
        AddressDao addressDao = DaoFactory.createAddress()
        return new JobsService(jobDao,legalPersonDao,addressDao)
    }
}