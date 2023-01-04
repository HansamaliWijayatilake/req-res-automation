package apiTask;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiAutomation {

    public static final String BASE_URL = "https://reqres.in";
    int id;

    @Test
    public void getAllUsers(){
        Response response = given().baseUri(BASE_URL)
                .queryParam("page",1)
                .when()
                .get("/api/users");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        System.out.println("---Response Code Verified---");

        JsonPath jsonpath= new JsonPath(response.asString());
        int size = jsonpath.getInt("data.size()");

        List<Integer> ids = new ArrayList<>();
        for(int i =0; i<size;i++) {
            int id = jsonpath.getInt("data["+i+"].id");
            ids.add(id);
        }

        assertThat(ids,hasItems(4,5,6));
        System.out.println("---Ids Verified---");

    }

    @Test(dependsOnMethods = "getAllUsers")
    public void addUser(){

        JsonObject requestParams = new JsonObject();

        String name = "abc";
        String job = "leader";

        requestParams.addProperty("name",name);
        requestParams.addProperty("job", job);

        Response response = given().baseUri(BASE_URL)
                .when()
                .header("Content-type","application/json")
                .body(requestParams)
                .post("/api/users");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
        System.out.println("---Response Code Verified---");

        JsonPath jsonpath= new JsonPath(response.asString());

        String nameFromResponse = jsonpath.get("name");
        String jobFromResponse = jsonpath.get("job");

        Assert.assertEquals(name,nameFromResponse);
        Assert.assertEquals(job, jobFromResponse);

        id = jsonpath.getInt("id");
        System.out.println(id);

    }

    @Test(dependsOnMethods = "addUser")
    public void updateUser(){

        JsonObject requestParams = new JsonObject();

        String name = "cde";
        String job = "leader";

        requestParams.addProperty("name",name);
        requestParams.addProperty("job", job);

        Response response = given().baseUri(BASE_URL)
                .when()
                .header("Content-type","application/json")
                .body(requestParams.getAsJsonObject())
                .put("/api/users/{id}",id);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        System.out.println("---Response Code Verified---");

        JsonPath jsonpath= new JsonPath(response.asString());

        String nameFromResponse = jsonpath.get("name");
        String jobFromResponse = jsonpath.get("job");

        Assert.assertEquals(name,nameFromResponse);
        Assert.assertEquals(job, jobFromResponse);

    }
}
