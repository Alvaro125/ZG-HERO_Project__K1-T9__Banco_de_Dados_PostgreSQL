package org.example.factorys

import org.example.controllers.JobController
import org.example.controllers.LoginController
import org.example.controllers.NaturalPersonController
import org.example.services.JobsService
import org.example.services.LoginService
import org.example.services.NaturalPersonService

class ControllerFactory {
    static LoginController createLogin() {
        LoginService loginService = ServiceFactory.createLogin()
        return new LoginController(loginService)
    }

    static NaturalPersonController createNaturalPerson() {
        NaturalPersonService createNaturalPerson = ServiceFactory.createNaturalPerson()
        return new NaturalPersonController(createNaturalPerson)
    }

    static JobController createJob() {
        JobsService jobsService = ServiceFactory.createJob()
        return new JobController(jobsService)
    }
}
