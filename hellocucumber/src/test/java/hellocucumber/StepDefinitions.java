package hellocucumber;

import org.openqa.selenium.JavascriptExecutor;

import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    // DesiredCapabilities caps = DesiredCapabilities.firefox();
    // LoggingPreferences logPrefs = new LoggingPreferences();
    // logPrefs.enable(LogType.BROWSER, Level.All);
    // caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
    
    private final WebDriver driver = new FirefoxDriver();
    private int statusCode;
    
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
    public void pageReady(String query) {
        this.waitForPageLoaded();
   }

   @Then("the console log has no errors")
   public void checkNoErrorsInConsole() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        logs.forEach((log) -> {
            if (log.getLevel() == Level.SEVERE || log.getLevel() == Level.SEVERE) {
                Assert.fail("Console has errors");
            }
        });
   }

   @Then("response code")
   public void checkResponseCode(String pageURL) {
        try {
            statusCode= this.getResponseCode(pageURL);
        } catch (Throwable error) {
            Assert.fail("Unable to get status code");
        }
        
        if(statusCode >= 400 || statusCode < 500){
            // Broken link found -- Test failed
            Assert.fail("Broken links found in page");
        }
   }

   @Then("Page has no broken links")
   public void checkNoBrokenLinks(String titleStartsWith) {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for(int i = 0; i < links.size(); i++){
            if(!(links.get(i).getAttribute("href") == null) && !(links.get(i).getAttribute("href").equals(""))){
                if(links.get(i).getAttribute("href").contains("http")){
                    try {
                        statusCode= this.getResponseCode(links.get(i).getAttribute("href").trim());
                    } catch (Throwable error) {
                        Assert.fail("Unable to get status code");
                    }
                    
                    if(statusCode >= 400 || statusCode < 500){
                        // Broken link found -- Test failed
                        Assert.fail("Broken links found in page");
                    }
                }
            }   
        }   
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public int getResponseCode(String urlString) throws MalformedURLException, IOException{
        URL url = new URL(urlString);
        HttpURLConnection huc = (HttpURLConnection)url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        return huc.getResponseCode();
    }

   @After()
   public void closeBrowser() {
       driver.quit();
   }
}
