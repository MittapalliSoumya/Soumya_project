package com.salesforce.trailhead.common;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class TestRailAPI {

    private static Logger LOGGER = Logger.getLogger(TestRailAPI.class);
    protected CommonHelper commonHelper = new CommonHelper();
    protected static Properties props;
    protected HTTPHelper httpHelper ;
    private String authorizationHeader;
    private Header auth;


    public  TestRailAPI() throws IOException {
        LOGGER.info("==> logged in to test rail api");
        props = commonHelper.readProperties("application.properties");
        RestAssured.baseURI = props.getProperty("testrail.url");
        String password = props.getProperty("testrail.password");
        String username = props.getProperty("testrail.username");
        String authString = username + ":" + password;
        byte[] token = Base64.encodeBase64(authString.getBytes());
        authorizationHeader = "Basic " + new String(token);
        auth = new Header("Authorization", authorizationHeader);
        httpHelper = new HTTPHelper();

    }

    public  List<String> get_testCase_Title(int runId){
        Response res = httpHelper.getRequest("api/v2/get_tests/", runId, auth);
        String json = res.getBody().asString();
        List<String> titles = JsonPath.parse(json).read("$..title");
        return titles;
    }


    public void update_results(int runId){
      List<String> tcTitle = get_testCase_Title(runId);

     // Response res = httpHelper.postRequest("/api/v2/add_results_for_cases/",runId, auth);

    }

    public static void main(String[] args) throws IOException {

        TestRailAPI test = new TestRailAPI();
        List<String> titles = test.get_testCase_Title(3103);

       System.out.println(titles);

    }
}
