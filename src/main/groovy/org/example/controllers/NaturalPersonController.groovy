package org.example.controllers

import com.sun.net.httpserver.HttpExchange
import org.example.dto.LoginDto
import org.example.entity.NaturalPersonEntity
import org.example.services.LoginService
import org.example.services.NaturalPersonService

class NaturalPersonController extends Controller{
    private NaturalPersonService naturalPersonService

    NaturalPersonController(NaturalPersonService naturalPersonService) {
        this.naturalPersonService = naturalPersonService
    }

    @Override
    protected void handleGetRequest(HttpExchange request) {
        handleListAllNaturalPeople(request)
    }

    private void handleListAllNaturalPeople(HttpExchange request) {
        try {
            List<NaturalPersonEntity> candidatos = naturalPersonService.listAll()
            String response = gson.toJson(candidatos)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

}
