package org.example.services

import org.example.dto.LoginDto
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity

interface LoginService {
    NaturalPersonEntity loginNaturalPerson(LoginDto req)
    LegalPersonEntity loginLegalPerson(LoginDto req)
}