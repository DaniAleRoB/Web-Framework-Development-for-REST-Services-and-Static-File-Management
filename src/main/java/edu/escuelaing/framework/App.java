package edu.escuelaing.framework;

import static edu.escuelaing.framework.Spark.*;
/**
 * Entry point of the web application.
 * Registers REST routes using lambda functions and starts the HTTP server.
 *
 * Available endpoints:
 *   GET http://localhost:35000/App/hello?name=Pedro  → "Hello Pedro"
 *   GET http://localhost:35000/App/pi                → "3.141592653589793"
 *   GET http://localhost:35000/App/euler             → "2.718281828459045"
 *   GET http://localhost:35000/index.html            → static HTML page
 *   GET http://localhost:35000/<unknown>             → "Hello World!"
 */
public class App {

    public static void main(String[] args) throws Exception {
        staticfiles("/webroot");

        get("/App/hello", (req, res) ->
                "Hello " + req.getValue("name")
        );

        get("/App/pi", (req, res) ->
                String.valueOf(Math.PI)
        );

        get("/App/euler", (req, res) ->
                String.valueOf(Math.E)
        );
        HttpServer.main(args);
    }
}