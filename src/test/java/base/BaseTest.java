package base;

import com.salesforce.trailhead.common.CommonHelper;
import com.salesforce.trailhead.common.TestRailAPI;
import com.salesforce.trailhead.pages.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.*;

public class BaseTest {

    private static Logger LOGGER = Logger.getLogger(BaseTest.class);
    protected CommonHelper commonHelper = new CommonHelper();
    protected TestRailAPI testRailAPI ;
    protected static Properties props;
    protected WebDriver driver;
    protected static String browser;
    protected static String environment;
    protected static String caseId;
    protected static int status;
    protected static int runId;
    protected Map<String,Integer> map;

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
        if(runId != -1){
            runId  = Integer.parseInt(props.getProperty("runId"));
        }
    }

    @BeforeClass(alwaysRun=true)
    public void setupClass(){
        LOGGER.info("==> Before class getting driver object and starting the browser");
        driver = BasePage.getNewDriver(BasePage.BrowserType.valueOf(browser.toUpperCase()));
        driver.get(environment);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        status = 0;
        caseId = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestData.class).testCaseId();

     if(ITestResult.SUCCESS == result.getStatus()){
            status = TestRailAPI.Status.SUCCESS.getStatusId();
        }else if(ITestResult.FAILURE == result.getStatus()){
            commonHelper.captureScreenshot(driver, result.getName());
            status = TestRailAPI.Status.FAILURE.getStatusId();
        }else if(ITestResult.SKIP == result.getStatus()){
            status = TestRailAPI.Status.SKIPPED.getStatusId();
        }
        map =new HashMap<String,Integer>();
        map.put(caseId,status);
        System.out.println(map + "<================>");
    }

    @AfterClass(alwaysRun=true)
    public void teardownClass()throws IOException{

        if(driver!=null) {
            LOGGER.info("Closing chrome browser");
            driver.quit();
        }
        testRailAPI =  new TestRailAPI();
        testRailAPI.update_results(runId,map);

    }


}