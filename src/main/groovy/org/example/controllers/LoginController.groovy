package org.example.controllers

import com.sun.net.httpserver.HttpExchange
import org.example.dto.LoginDto
import org.example.entity.NaturalPersonEntity
import org.example.services.LoginService

class LoginController extends Controller{
    private LoginService loginService

    LoginController(LoginService loginService) {
        this.loginService = loginService
    }

    @Override
    protected void handlePostRequest(HttpExchange request) {
        String url = request.getRequestURI().getPath()
        if (url == '/login/candidato') {
            handleLoginCandidato(request)
        } else if (url == '/login/empresa') {
//            handleLoginEmpresa(request)
        } else {
            sendResponse(request, 400, "Rota incorreta")
        }
    }

    private void handleLoginCandidato(HttpExchange request) {
        try {
            String requestBody = new String(request.getRequestBody().readAllBytes())
            LoginDto loginRequest = gson.fromJson(requestBody, LoginDto)
            NaturalPersonEntity candidato = loginService.loginNaturalPerson(loginRequest)
            String response = gson.toJson(candidato)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

}
