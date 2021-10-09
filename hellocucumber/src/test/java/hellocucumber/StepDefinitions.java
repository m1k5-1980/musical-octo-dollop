package hellocucumber;

import org.openqa.selenium.JavascriptExecutor;
import org.junit.Assert;

import io.cucumber.java.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;

import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

    private WebDriver driver;
    private int statusCode;
    private String pageURL;

    @Given("I opened Chrome")
    public void use_Chrome() {
        ChromeOptions chOpts = new ChromeOptions().setHeadless(true);
        driver = new ChromeDriver(chOpts);
    }

    @Given("I opened Firefox")
    public void use_Firefox() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        
        FirefoxOptions ffOpts = new FirefoxOptions()
                    .addPreference("devtools.console.stdout.content", true)
                    .setHeadless(true)
                    .setBinary(firefoxBinary);

        driver = new FirefoxDriver(ffOpts);
    }
    
    @When("I am on the W3 bad page")
    public void I_visit_bad() {
        pageURL = "https://www.w3.org/standards/badpage";
        driver.get("https://www.w3.org/standards/badpage");
        this.waitForPageLoaded();
    }

    @When("I am on the W3 multimodal page")
    public void I_visit_multimodal(){
        pageURL = "https://www.w3.org/standards/webofdevices/multimodal";
        driver.get("https://www.w3.org/standards/webofdevices/multimodal");
        this.waitForPageLoaded();
    }

    @When("I am on the W3 htmlcss page")
    public void I_visit_htmlcss(){
        this.pageURL = "https://www.w3.org/standards/webdesign/htmlcss";
        driver.get("https://www.w3.org/standards/webdesign/htmlcss");
        this.waitForPageLoaded();
    }

   @Then("The console log has no errors")
   public void checkNoErrorsInConsole() {
        LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
        logs.forEach((log) -> {
            if (log.getLevel() == Level.SEVERE || log.getLevel() == Level.SEVERE) {
                Assert.fail("Message in console:" + log.getMessage());
            }
        });
   }

   @Then("response code")
   public void checkResponseCode() {
        try {
            statusCode= this.getResponseCode(this.pageURL);
        } catch (Throwable error) {
            Assert.fail(error.getMessage());
        }
        
        if(statusCode >= 400 && statusCode < 500){
            // Broken link found -- Test failed
            Assert.fail("Status code returned: " + statusCode);
        }
        Assert.assertEquals(200, statusCode);;
   }

   @Then("Page has no broken links")
   public void checkNoBrokenLinks() {
        List<WebElement> links = driver.findElements(By.tagName("a"));
        for(int i = 0; i < links.size(); i++){
            if(!(links.get(i).getAttribute("href") == null) && !(links.get(i).getAttribute("href").equals(""))){
                if(links.get(i).getAttribute("href").contains("http")){
                    try {
                        statusCode= this.getResponseCode(links.get(i).getAttribute("href").trim());
                    } catch (Throwable error) {
                        Assert.fail("Exception:" + error.getMessage());
                    }
                    
                    if(statusCode >= 400 && statusCode < 500){
                        // Broken link found -- Test failed
                        Assert.fail("Status code returned: " + statusCode);
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
            Assert.fail("Exception:" + error.getMessage());
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
