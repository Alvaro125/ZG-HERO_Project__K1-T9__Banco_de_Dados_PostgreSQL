package org.example.controllers

import com.sun.net.httpserver.HttpExchange
import org.example.dto.LegalPersonDto
import org.example.dto.NaturalPersonDto
import org.example.entity.LegalPersonEntity
import org.example.entity.NaturalPersonEntity
import org.example.services.LegalPersonService
import org.example.services.NaturalPersonService

class LegalPersonController extends Controller{
    private LegalPersonService legalPersonService

    LegalPersonController(LegalPersonService legalPersonService) {
        this.legalPersonService = legalPersonService
    }

    @Override
    protected void handleGetRequest(HttpExchange request) {
        handleListAllLegalPeople(request)
    }

    @Override
    protected void handlePostRequest(HttpExchange request) {
        handleCreateLegalPeople(request)
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

    private void handleListAllLegalPeople(HttpExchange request) {
        try {
            List<LegalPersonEntity> empresas = legalPersonService.listAll()
            String response = gson.toJson(empresas)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }
    private void handleCreateLegalPeople(HttpExchange request) {
        try {
            String requestBody = new String(request.getRequestBody().readAllBytes())
            LegalPersonDto empresaRequest = gson.fromJson(requestBody, LegalPersonDto)
            LegalPersonEntity empresa = legalPersonService.addUser(empresaRequest.toEntity())
            String response = gson.toJson(empresa)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

}
