package com.salesforce.trailhead.pages;

import com.salesforce.trailhead.exceptions.SalesforceException;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    private static Logger LOGGER = Logger.getLogger(LoginPage.class);

    private WebDriver driver;

    @FindBy(id = "content")
    private WebElement loginContent;

    @FindBy(id = "username")
    private WebElement salesforceUsername;

    @FindBy(id = "password")
    private WebElement salesforcePassword;

    @FindBy(id = "Login")
    private WebElement salesforceLogin;

    public WebElement getLoginContent() {
        return loginContent;
    }

    public WebElement getSalesforceUsername() {
        return salesforceUsername;
    }

    public WebElement getSalesforcePassword() {
        return salesforcePassword;
    }

    public WebElement getSalesforceLogin() {
        return salesforceLogin;
    }

    public LoginPage (WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitForElement(getLoginContent());
    }

    public void enterUsername(String username){
        getSalesforceUsername().sendKeys(username);
    }

    public void enterPassword(String password){
        getSalesforcePassword().sendKeys(password);
    }

    public void clickLogin() throws SalesforceException{
        try {
            WebElement loginEle = getSalesforceLogin();
            if(loginEle.isDisplayed() && loginEle.isEnabled()){
                getSalesforceLogin().click();
            } else {
                LOGGER.error("Unable to click on login button");
                throw new SalesforceException("Unable to click the login button as it is not displayed or enabled");
            }
        } catch(NoSuchElementException e) {
            LOGGER.error("Unable to find the element", e);
        }
    }
}
