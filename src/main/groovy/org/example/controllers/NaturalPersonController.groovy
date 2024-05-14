package org.example.controllers

import com.sun.net.httpserver.HttpExchange
import org.example.dto.LoginDto
import org.example.dto.NaturalPersonDto
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

    @Override
    protected void handlePostRequest(HttpExchange request) {
        handleCreateNaturalPeople(request)
    }

    @Override
    protected void handleDeleteRequest(HttpExchange request) {
        try {
            String[] url = request.getRequestURI().getPath().split("/")
            naturalPersonService.deleteById(
                    naturalPersonService.oneById(url.last().toInteger())
            )
            String response = gson.toJson("ok")
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
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
    private void handleCreateNaturalPeople(HttpExchange request) {
        try {
            String requestBody = new String(request.getRequestBody().readAllBytes())
            NaturalPersonDto candidatoRequest = gson.fromJson(requestBody, NaturalPersonDto)
            NaturalPersonEntity candidato = naturalPersonService.addUser(candidatoRequest.toEntity())
            String response = gson.toJson(candidato)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

}
