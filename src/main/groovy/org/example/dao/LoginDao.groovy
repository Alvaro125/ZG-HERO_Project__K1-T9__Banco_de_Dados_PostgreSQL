package org.example.dao

import org.example.dto.LoginDto
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity

interface LoginDao<T> {
    T loginPerson(LoginDto req)
}