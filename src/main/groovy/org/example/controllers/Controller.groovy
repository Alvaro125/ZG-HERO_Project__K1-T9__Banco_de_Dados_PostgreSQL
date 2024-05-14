package org.example.controllers

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange

abstract class Controller {
    protected Gson gson

    Controller() {
        gson = new Gson()
    }

    void handleRequest(HttpExchange request) {
        request.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        request.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        request.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        request.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        request.getResponseHeaders().add("Access-Control-Allow-Credentials-Header", "*");

        if (request.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            request.sendResponseHeaders(200, -1);
            return;
        }

        String method = request.getRequestMethod()

        if (method == "GET") {
            handleGetRequest(request)
        } else if (method == "POST") {
            handlePostRequest(request)
        } else if (method == "PUT") {
            handlePutRequest(request)
        } else if (method == "DELETE") {
            handleDeleteRequest(request)
        }
        else {
            sendResponse(request, 405, "Método não implementado")
        }
    }

    protected void handleGetRequest(HttpExchange request) {
        sendResponse(request, 405, "Método não implementado")
    }

    protected void handlePostRequest(HttpExchange request) {
        sendResponse(request, 405, "Método não implementado")
    }

    protected void handlePutRequest(HttpExchange request) {
        sendResponse(request, 405, "Método não implementado")
    }

    protected void handleDeleteRequest(HttpExchange request) {
        sendResponse(request, 405, "Método não implementado")
    }

    protected void sendResponse(HttpExchange request, Integer statusCode, String response) {
        try {
            request.getResponseHeaders().add("Content-Type", "application/json")
            request.sendResponseHeaders(statusCode, response ? response.getBytes().length : 0)
            OutputStream os = request.getResponseBody()
            if (response != null) {
                os.write(response.getBytes())
            }
            os.close()
        } catch (Exception e) {
            System.err.println("Erro ao enviar resposta: " + e.message)
        }
    }
}
