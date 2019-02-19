package com.salesforce.trailhead.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.InvalidParameterException;

public abstract class BasePage {
    public static int timeOutInSecs = 60;
    protected WebDriver driver;
    private WebDriverWait webDriverWait;

    private static Logger LOGGER = Logger.getLogger(BasePage.class);

    public enum BrowserType {
        FIREFOX,
        CHROME;
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        webDriverWait = new WebDriverWait(driver, timeOutInSecs * 5);
    }

    /***
     * This method is used to get new driver based on the given browser type
     * @param browserType - Enum of browser types
     * @return WebDriver
     */
    public static WebDriver getNewDriver(BrowserType browserType) {
        LOGGER.info("==> Get new driver for browser : " + browserType.name());
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setPlatform(Platform.MAC);

        switch (browserType) {

            case FIREFOX:
                return new FirefoxDriver();
            case CHROME:
                if (null == System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY)) {
                    ClassLoader loader = Thread.currentThread().getContextClassLoader();
                    String path = loader.getResource("chromedriver").getPath();
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, path);
                }
                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-infobars");
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                return new ChromeDriver(desiredCapabilities);
            default:
                throw new InvalidParameterException("You must choose one of the defined driver types");
        }
    }

    /***
     * This method is used to wait for Ajax calls to complete
     */
    public void waitForAjaxToComplete() {
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                // no jQuery present
                return true;
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");

        webDriverWait.until(jQueryLoad);
        webDriverWait.until(jsLoad);
    }

    /***
     * This method is used to wait for a given element
     * @param element - element it has to wait for
     */
    public void waitForElement(WebElement element) {
        waitForElement(element, timeOutInSecs);
    }

    /***
     * This method is used to wait for given element using By locator
     * @param by - locator of the element
     */
    public void waitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecs);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /***
     * This method is used to wait for a given element for a given timeout
     * @param element - element it has to wait for
     * @param maxWaitTimeOut - timeout it has to wait for the element invisibility
     */
    public void waitForElement(WebElement element, long maxWaitTimeOut) {
        WebDriverWait wait = new WebDriverWait(driver, maxWaitTimeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /***
     * This method is used to wait for invisibility of given element using By locator
     * @param locator - locator of the element
     */
    public void waitForElementInvisibility(By locator) {
        waitForElementInvisibility(locator, timeOutInSecs);
    }

    /***
     * This method is used to wait for invisibility of given element using By locator for a given timeout
     * @param locator - locator of the element
     * @param maxTimeout - timeout it has to wait for the element invisibility
     */
    public void waitForElementInvisibility(By locator, long maxTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, maxTimeout);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

}