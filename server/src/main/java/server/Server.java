package server;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

import handler.ClearHandler;
import handler.EventHandler;
import handler.FillHandler;
import handler.LoginHandler;
import handler.PersonHandler;
import handler.RegisterHandler;
import handler.RootHandler;

public class Server {
    public static void main(String[] args) throws Exception {

        int port = 8080;

        System.out.println("server listening on port: " + port);
        System.out.println();

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new RootHandler());

        server.start();
    }
}
