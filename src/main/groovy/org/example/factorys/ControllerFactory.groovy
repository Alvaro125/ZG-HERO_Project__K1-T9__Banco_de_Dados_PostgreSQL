package org.example.factorys

import org.example.controllers.JobController
import org.example.controllers.LegalPersonController
import org.example.controllers.LoginController
import org.example.controllers.NaturalPersonController
import org.example.services.JobsService
import org.example.services.LegalPersonService
import org.example.services.LoginService
import org.example.services.NaturalPersonService

class ControllerFactory {
    static LoginController createLogin() {
        LoginService loginService = ServiceFactory.createLogin()
        return new LoginController(loginService)
    }

    static NaturalPersonController createNaturalPerson() {
        NaturalPersonService naturalPersonService = ServiceFactory.createNaturalPerson()
        return new NaturalPersonController(naturalPersonService)
    }

    static LegalPersonController createLegalPerson() {
        LegalPersonService legalPersonService = ServiceFactory.createLegalPerson()
        return new LegalPersonController(legalPersonService)
    }


    static JobController createJob() {
        JobsService jobsService = ServiceFactory.createJob()
        return new JobController(jobsService)
    }
}
