package ru.ankhell;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class REST_Test {
    @BeforeMethod
    public void configureRestAssured(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        requestSpecification = given().
                header("Accept-Language", "ru-RU,en-US").
                header("User-Agent","Mozilla/5.0 (Windows NT 10.0; " +
                        "Win64; x64; rv:71.0) Gecko/20100101 Firefox/71.0").
                header("Accept","application/json");
    }

    @Test (description = "Проверяем, что значение полученного поля tile соответствует ожидаемому")
    public void postGetTest(){
        final String URL = "/posts/1";
        JsonPath body = given().
                when().get(URL)
                .then().extract().body().jsonPath();
//        System.out.println((String)body.get("title"));
        Assert.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",body.get("title"));
    }

    @Test (description = "Проверяем соответствие полученного json файла (posts) схеме")
    public void jsonPostsAssurance(){
        final String URL = "/posts/";
        get(URL).then().assertThat().body(matchesJsonSchemaInClasspath("postsArray.json"));
    }
}