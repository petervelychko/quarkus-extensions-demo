package dev.demo.app;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GreetingResourceTest {

    @Test
    @DisplayName("Returns the default greeting from the REST endpoint")
    void respondsWithDefaultMessage() {
        given().when().get("/hello").then().statusCode(200).body(is("Hello Quarkus"));
    }

    @Test
    @DisplayName("Formats the name provided in the REST path")
    void formatsPathVariable() {
        given().when().get("/hello/Ada").then().statusCode(200).body(is("Hello Ada"));
    }
}
