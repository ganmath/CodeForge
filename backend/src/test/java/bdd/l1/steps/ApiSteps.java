
package bdd.l1.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiSteps {
    private String baseUrl;
    private Response response;

    @Given("the API base url is {string}")
    public void theApiBaseUrl(String url) { this.baseUrl = url; }

    @When("I GET {string}")
    public void iGET(String path) { response = given().when().get(baseUrl + path); }

    @When("I POST {string}")
    public void iPOST(String path) { response = given().when().post(baseUrl + path); }

    @Then("the response code is {int}")
    public void theResponseCodeIs(int code) { response.then().statusCode(code); }

    @And("the json field {string} equals {string}")
    public void jsonFieldEquals(String field, String val) {
        assertThat(response.jsonPath().getString(field), equalTo(val));
    }

    @And("the json has fields {string},{string},{string}")
    public void jsonHasFields(String f1, String f2, String f3) {
        var json = response.jsonPath().getMap("$");
        assertThat(json, hasKey(f1));
        assertThat(json, hasKey(f2));
        assertThat(json, hasKey(f3));
    }
}
