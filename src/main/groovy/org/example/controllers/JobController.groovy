package org.example.controllers

import com.sun.net.httpserver.HttpExchange
import org.example.dao.JobDao
import org.example.dto.JobDto
import org.example.dto.LoginDto
import org.example.entity.JobEntity
import org.example.entity.NaturalPersonEntity
import org.example.services.JobsService
import org.example.services.LoginService

class JobController extends Controller{
    private JobsService jobsService

    JobController(JobsService jobsService) {
        this.jobsService = jobsService
    }

    @Override
    protected void handleGetRequest(HttpExchange request) {
        handleListAllJobs(request)
    }

    @Override
    protected void handlePostRequest(HttpExchange request) {
        try {
            String requestBody = new String(request.getRequestBody().readAllBytes())
            JobDto job = gson.fromJson(requestBody, JobDto)
            JobEntity vaga = jobsService.addJob(job.toEntity())
            String response = gson.toJson(vaga)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

    private void handleListAllJobs(HttpExchange request) {
        try {
            List<JobEntity> vagas = jobsService.listAll()
            String response = gson.toJson(vagas)
            sendResponse(request, 200, response)
        } catch (Exception e) {
            sendResponse(request, 401, e.message)
        }
    }

}
