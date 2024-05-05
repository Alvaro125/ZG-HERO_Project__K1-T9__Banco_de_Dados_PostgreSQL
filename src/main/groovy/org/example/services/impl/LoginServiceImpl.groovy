package org.example.services.impl

import org.example.dao.NaturalPersonDao
import org.example.dto.LoginDto
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.services.LoginService

class LoginServiceImpl implements LoginService{
    private NaturalPersonDao naturalPersonDao
    LoginServiceImpl(NaturalPersonDao naturalPersonDao){
        this.naturalPersonDao = naturalPersonDao
    }

    @Override
    NaturalPersonEntity loginNaturalPerson(LoginDto req) {
        return naturalPersonDao.loginPerson(req)
    }

    @Override
    LegalPersonEntity loginLegalPerson(LoginDto req) {
        return null
    }
}
