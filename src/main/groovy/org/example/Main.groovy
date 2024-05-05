package org.example

import org.example.config.Database
import org.example.server.Server

class Main {
    public static void main(String[] args) {
        Database.getConnection();
        def app = new Server()
        app.init()
    }
}