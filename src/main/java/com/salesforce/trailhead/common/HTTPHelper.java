package com.salesforce.trailhead.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

public class HTTPHelper {

    private static Logger LOGGER = Logger.getLogger(HTTPHelper.class);
    protected CommonHelper commonHelper = new CommonHelper();
    String authorizationHeader;

    public HTTPHelper(String baseURI, String username, String password){
        RestAssured.baseURI = baseURI;
        String authString = username + ":" + password;
        byte[] token = Base64.encodeBase64(authString.getBytes());
        authorizationHeader = "Basic "+ new String(token);
    }

    public Response getRequest(String url, int id){
        Response res = given()
                       .contentType("application/json")
                       .queryParam(url+id)
                       .header("Authorization", authorizationHeader)
                       .get();

        return res;
    }

    public void getRequesttestcaseId(int testId){
    RestAssured.baseURI = "https://mysnaplogic.testrail.net/index.php";
        Response res = given()
                .contentType("application/json")
                .queryParam("/api/v2/get_case/"+testId )
                .header("Authorization", "Basic "+ new String(authorizationHeader))
                .get();


    }

    public void getRequesttestRunId(int runId){
        RestAssured.baseURI = "https://mysnaplogic.testrail.net/index.php";
        Response res = given()
                .contentType("application/json")
                .queryParam("/api/v2/get_run/"+runId)
                .header("Authorization" ,"Basic "+ new String(authorizationHeader))
                .get();
        System.out.println(res.asString());
    }

    public static void main(String[] args) throws IOException  {
        HTTPHelper test = new HTTPHelper("https://mysnaplogic.testrail.net/index.php","smittapalli@snaplogic.com","Snaplogic123");
        /*test.getRequesttestcaseId(93880);
        command alt backslach comment ki
        test.getRequesttestRunId(3103);*/
        test.getRequest("/api/v2/get_case/",93880);

    }
}
