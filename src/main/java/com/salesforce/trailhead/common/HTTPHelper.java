package com.salesforce.trailhead.common;

import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;

import static com.jayway.restassured.RestAssured.given;

public class HTTPHelper {

    private static Logger LOGGER = Logger.getLogger(HTTPHelper.class);


    public Response getRequest(String url, int id, Header auth) {
        Response res = given()
                .contentType("application/json")
                .queryParam(url + id)
                .header(auth)
                .get();
        return res;
    }


    public Response postRequest(String url, int id,Header auth ) {
        Response res = given()
                .contentType("application/json")
                .queryParam(url + id)
                .header(auth)
                .post();
        return res;
    }






}
