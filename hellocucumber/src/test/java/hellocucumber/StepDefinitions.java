package hellocucumber;

import org.openqa.selenium.JavascriptExecutor;

import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    private final WebDriver driver = new FirefoxDriver();
    
    @Given("I am on the W3 bad page")
    public void I_visit_bad() {
        driver.get("https://www.w3.org/standards/badpage");
    }

    @Given("I am on the W3 multimodal page")
    public void I_visit_multimodal(){
        driver.get("https://www.w3.org/standards/webofdevices/multimodal");
    }

    @Given("I am on the W3 htmlcss page")
    public void I_visit_htmlcss(){
        driver.get("https://www.w3.org/standards/webdesign/htmlcss");
    }

    @When("Page loaded")
    public void search_for(String query) {
        WebElement element = driver.findElement(By.name("q"));
        // Enter something to search for
        element.sendKeys(query);
        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();
   }

   @Then("the page title should start with {string}")
   public void checkTitle(String titleStartsWith) {
       // Google's search is rendered dynamically with JavaScript
       // Wait for the page to load timeout after ten seconds
       new WebDriverWait.WebDriverWait(driver,10L).until(new ExpectedCondition<Boolean>() {
           public Boolean apply(WebDriver d) {
               return d.getTitle().toLowerCase().startsWith(titleStartsWith);
           }
       });
   }

   public void waitForPageLoaded() {
    ExpectedCondition<Boolean> expectation = new
            ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                }
            };
    try {
        Thread.sleep(1000);
        WebDriverWait wait = new WebDriverWait.WebDriverWait(driver, 30);
        wait.until(expectation);
    } catch (Throwable error) {
        Assert.fail("Timeout waiting for Page Load Request to complete.");
    }
}

   @After()
   public void closeBrowser() {
       driver.quit();
   }
}
