
package bdd.l2.steps;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UiSteps {
    private String fe;

    @Given("the FE url is {string}")
    public void theFeUrlIs(String url) {
        this.fe = url;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
        Configuration.browser = "chrome";
        Configuration.timeout = 8000;
    }

    @When("I open the home page")
    public void iOpenHome() { open(fe); }

    @When("I click the {string} button")
    public void iClickButton(String label) {
        $("button").click();
    }

    @Then("I should see text {string}")
    public void iShouldSeeText(String t) {
        $("#result").shouldHave(text("status: ok"));
        Selenide.closeWebDriver();
    }
}
