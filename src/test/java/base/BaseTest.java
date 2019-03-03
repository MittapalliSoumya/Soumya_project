package base;

import com.salesforce.trailhead.common.CommonHelper;
import com.salesforce.trailhead.pages.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    private static Logger LOGGER = Logger.getLogger(BaseTest.class);
    protected CommonHelper commonHelper = new CommonHelper();
    protected static Properties props;
    protected WebDriver driver;
    protected static String browser;
    protected static String environment;

    @BeforeSuite(alwaysRun=true)
    public void setUpSuite() throws IOException {
        LOGGER.info("==> Before Suite reading properties file");
        props = commonHelper.readProperties("application.properties");
        browser = System.getProperty("browser");
        environment = System.getProperty("environment");
        if(browser == null || browser.equalsIgnoreCase("")){
            browser = props.getProperty("browser");
        }
        if(environment == null || environment.equalsIgnoreCase("")){
            environment = props.getProperty("env.url");
        }
    }

    @BeforeClass(alwaysRun=true)
    public void setupClass(){
        LOGGER.info("==> Before class getting driver object and starting the browser");
        driver = BasePage.getNewDriver(BasePage.BrowserType.valueOf(browser.toUpperCase()));
        driver.get(environment);
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun=true)
    public void teardownClass(){
        if(driver!=null) {
            LOGGER.info("Closing chrome browser");
            driver.quit();
        }
    }


}