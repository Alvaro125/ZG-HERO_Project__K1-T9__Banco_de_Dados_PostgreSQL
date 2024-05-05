package org.example.dto

import groovy.transform.Canonical
import lombok.Data

@Data
@Canonical
class LoginDto {
    String email
    String senha

    LoginDto(String email, String senha) {
        this.email = email
        this.senha = senha
    }
}
