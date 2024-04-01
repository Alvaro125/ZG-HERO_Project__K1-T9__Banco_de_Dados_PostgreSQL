package org.example

import org.example.config.Database
import org.example.ui.Terminal

class Main {
    public static void main(String[] args) {
        Database.getConnection();
        def app = new Terminal()
        app.run()
    }
}