package test.java.main.java.edu.escuelaing.framework;

import main.java.edu.escuelaing.framework.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the web framework components.
 */
class AppTest {

    @Test
    void testRequestParsesPathCorrectly() throws Exception {
        Request req = new Request("GET", "/App/hello?name=Pedro");
        assertEquals("/App/hello", req.getPath());
    }

    @Test
    void testRequestParsesQueryParameter() throws Exception {
        Request req = new Request("GET", "/App/hello?name=Pedro");
        assertEquals("Pedro", req.getValue("name"));
    }

    @Test
    void testRequestGetValuesAlias() throws Exception {
        Request req = new Request("GET", "/App/hello?name=Juan");
        assertEquals("Juan", req.getValues("name")); // alias method
    }

    @Test
    void testRequestWithNoQueryReturnsNull() throws Exception {
        Request req = new Request("GET", "/App/pi");
        assertNull(req.getValue("name"));
    }

    @Test
    void testRequestParsesMethod() throws Exception {
        Request req = new Request("GET", "/App/pi");
        assertEquals("GET", req.getMethod());
    }

    @Test
    void testRequestMultipleQueryParams() throws Exception {
        Request req = new Request("GET", "/search?name=Pedro&age=30");
        assertEquals("Pedro", req.getValue("name"));
        assertEquals("30", req.getValue("age"));
    }

    @Test
    void testResponseDefaultStatus() {
        Response res = new Response();
        assertEquals(200, res.getStatus());
    }

    @Test
    void testResponseDefaultContentType() {
        Response res = new Response();
        assertEquals("text/html", res.getContentType());
    }

    @Test
    void testResponseSetStatus() {
        Response res = new Response();
        res.status(404);
        assertEquals(404, res.getStatus());
    }

    @Test
    void testResponseSetContentType() {
        Response res = new Response();
        res.type("application/json");
        assertEquals("application/json", res.getContentType());
    }

    @Test
    void testSparkRegisterAndRetrieveRoute() {
        Spark.get("/test/route", (req, res) -> "test response");
        assertNotNull(Spark.getRoute("/test/route"));
    }

    @Test
    void testSparkUnknownRouteReturnsNull() {
        assertNull(Spark.getRoute("/this/does/not/exist"));
    }

    @Test
    void testSparkStaticfilesConfiguration() {
        Spark.staticfiles("/custom/folder");
        assertEquals("/custom/folder", Spark.getStaticFolder());
        // Reset to default
        Spark.staticfiles("/webroot");
    }

    @Test
    void testRouteLambdaExecutesPiCorrectly() throws Exception {
        Route piRoute = (req, res) -> String.valueOf(Math.PI);
        Request req = new Request("GET", "/App/pi");
        Response res = new Response();
        assertEquals(String.valueOf(Math.PI), piRoute.handle(req, res));
    }

    @Test
    void testRouteLambdaExecutesHelloCorrectly() throws Exception {
        Route helloRoute = (req, res) -> "Hello " + req.getValue("name");
        Request req = new Request("GET", "/App/hello?name=Pedro");
        Response res = new Response();
        assertEquals("Hello Pedro", helloRoute.handle(req, res));
    }

    @Test
    void testRouteLambdaExecutesEulerCorrectly() throws Exception {
        Route eulerRoute = (req, res) -> String.valueOf(Math.E);
        Request req = new Request("GET", "/App/euler");
        Response res = new Response();
        assertEquals(String.valueOf(Math.E), eulerRoute.handle(req, res));
    }


    @Test
    void testResponseBuilderContainsStatus200() {
        byte[] body = "hello".getBytes();
        String headers = HttpResponseBuilder.build(200, "text/html", body);
        assertTrue(headers.contains("HTTP/1.1 200 OK"));
    }

    @Test
    void testResponseBuilderContainsStatus404() {
        byte[] body = "not found".getBytes();
        String headers = HttpResponseBuilder.build(404, "text/html", body);
        assertTrue(headers.contains("HTTP/1.1 404 Not Found"));
    }

    @Test
    void testResponseBuilderContainsContentType() {
        byte[] body = "data".getBytes();
        String headers = HttpResponseBuilder.build(200, "application/json", body);
        assertTrue(headers.contains("Content-Type: application/json"));
    }

    @Test
    void testResponseBuilderContainsContentLength() {
        byte[] body = "hello".getBytes(); // 5 bytes
        String headers = HttpResponseBuilder.build(200, "text/html", body);
        assertTrue(headers.contains("Content-Length: 5"));
    }

    @Test
    void testContentTypeHtml() {
        assertEquals("text/html; charset=UTF-8", StaticFileHandler.getContentType("/index.html"));
    }

    @Test
    void testContentTypeCss() {
        assertEquals("text/css", StaticFileHandler.getContentType("/style.css"));
    }

    @Test
    void testContentTypeJs() {
        assertEquals("application/javascript", StaticFileHandler.getContentType("/app.js"));
    }

    @Test
    void testContentTypePng() {
        assertEquals("image/png", StaticFileHandler.getContentType("/logo.png"));
    }
}