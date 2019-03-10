package com.salesforce.trailhead.common;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

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

    private List<Map<String, String>> get_testCase_Title(int runId){
        Response res = httpHelper.getRequest("api/v2/get_tests/", runId, auth);
        String json = res.getBody().asString();
      //  List<String> titles = JsonPath.parse(json).read("$..title");

        ReadContext ctx = JsonPath.parse(json);
        List<Map<String, String>> TestsList = ctx.read("$..[?(@.title)]", List.class);


        List<Map<String, String>> finalTestsList = new ArrayList<>();
        Map<String, String> newMap = null;
        try {
            for (Map<String, String> mapObj : TestsList) {
                newMap = new HashMap<String, String>();
                newMap.put("id", (mapObj.get("id") != null) ? String.valueOf(mapObj.get("id")) : "");
                newMap.put("title", (mapObj.get("title") != null) ? mapObj.get("title").toString() : "");
                newMap.put("case_id", (mapObj.get("case_id") != null) ? String.valueOf(mapObj.get("case_id")) : "");
                newMap.put("status_id", (mapObj.get("status_id") != null) ? String.valueOf(mapObj.get("status_id")) : "");
                finalTestsList.add(newMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalTestsList;

    }


    public void update_results(int runId,Map<String,Integer> map ) {

        Response res = httpHelper.postRequest("/api/v2/add_results_for_cases/",runId, auth, generate_payload(map));
    }


    private String generate_payload( Map<String, Integer> map){

        int status;
        String payload = "";
        String item;


        List<Map<String, String>> testcaseDetails = get_testCase_Title(3103);

        for(Map<String,String> x : testcaseDetails){
           if(map.keySet().contains(x.get("case_id"))){
             status =  map.get(x.get("case_id"));
             item = "{\"case_id\": "+x.get("case_id") +",\"status_id\": "+status+"}";
             payload =  payload + item + ",";
           }
        }
        payload = payload.replaceAll(",$", "");

        return "{\"results\":[" + payload + "]}";


    }

    public enum Status{
        SUCCESS(1),
        BLOCKED(2),
        FAILURE(5),
        SKIPPED(4);

        private int value;

        Status(int value){
            this.value = value;
        }

        public int getStatusId(){
            return value;
        }


    }
}
