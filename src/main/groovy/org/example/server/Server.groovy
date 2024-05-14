package org.example.server

import com.sun.net.httpserver.HttpServer
import org.example.controllers.JobController
import org.example.controllers.LegalPersonController
import org.example.controllers.LoginController
import org.example.controllers.NaturalPersonController
import org.example.factorys.ControllerFactory

class Server {
    private LoginController loginController
    private NaturalPersonController naturalPersonController
    private LegalPersonController legalPersonController
    private JobController jobController
    Server(){
        this.loginController = ControllerFactory.createLogin()
        this.naturalPersonController = ControllerFactory.createNaturalPerson()
        this.legalPersonController = ControllerFactory.createLegalPerson()
        this.jobController = ControllerFactory.createJob()
    }
    void init(){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0)
            server.createContext("/candidato", {request -> naturalPersonController.handleRequest(request)})
//            server.createContext("/candidato/vaga", {request -> candidatoVagaController.handleRequest(request)})
//            server.createContext("/candidato/competencia", {request -> "ress"})
//            server.createContext("/competencia", {request -> competenciaController.handleRequest(request)})
            server.createContext("/empresa", {request -> legalPersonController.handleRequest(request)})
//            server.createContext("/vaga/competencia", {request -> vagaCompetenciaController.handleRequest(request)})
            server.createContext("/vaga", {request -> jobController.handleRequest(request)})
            server.createContext("/login", {request -> loginController.handleRequest(request)})

            server.start()
            println('servidor rodando na porta 8081')
        } catch (Exception e) {
            System.err.println("Erro ao inicar o servidor: " + e.message)
        }
    }

}
