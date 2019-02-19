package com.salesforce.trailhead.pages;

import com.salesforce.trailhead.exceptions.SalesforceException;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

/***
 * This class is about the header and the links of Signup, login, Home, Modules and so on.
 */
public class HeaderComponent extends BasePage {
    private static Logger LOGGER = Logger.getLogger(HeaderComponent.class);
    private WebDriver driver;
    private int MAX_AJAX_WAIT = 60;

    public HeaderComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, MAX_AJAX_WAIT), this);
        waitforSearchBarToLoad();
    }

    @FindBy(className = "th-button--avatar")
    private WebElement avatarBtn;

    @FindBy(id = "search")
    private WebElement searchBar;

    @FindBy(xpath = "//span[text()='Favorites']")
    private WebElement favoriteLink;

    @FindBy(xpath = "//span[text()='Log Out']")
    private WebElement logoutLink;

    @FindBy(xpath = "//div[@data-test='header-account-name']")
    private WebElement accountNameEle;

    @FindBy(className = "tds-button_base")
    private WebElement loginLink;

    @FindBy(className = "slds-modal__content")
    private WebElement modalContent;

    @FindBy(className = "th-modal-btn__salesforce")
    private WebElement salesforceLogin;

    public WebElement getLoginLink() {
        return loginLink;
    }

    public WebElement getModalContent() {
        return modalContent;
    }

    public WebElement getSalesforceLogin() {
        return salesforceLogin;
    }

    public WebElement getAvatarBtn() {
        return avatarBtn;
    }

    public WebElement getSearchBar() {
        return searchBar;
    }

    public WebElement getFavoriteLink() {
        return favoriteLink;
    }

    public WebElement getLogoutLink() {
        return logoutLink;
    }

    public WebElement getAccountNameEle() {
        return accountNameEle;
    }

    public void waitforSearchBarToLoad() {
        waitForElement(getSearchBar());
        waitForAjaxToComplete();
    }

    public FavouritePage openFavoritePage() {
        return new FavouritePage(driver);
    }

    public void clickonLogoutLink() {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", getAvatarBtn());
        executor.executeScript("arguments[0].click();", getLogoutLink());
    }

    public String getAccountName() {
        return getAccountNameEle().getText();
    }

    public void clickonLoginLink() {
        getLoginLink().click();
    }

    public void waitforModalContent() {
        waitForElement(getModalContent());
    }

    public void clickonSalesforceLogin() {
        getSalesforceLogin().click();
    }

    /***
     * This method is used to login/ connect with salesforce sandbox or developer account
     * @return
     * @throws SalesforceException
     */
    public LoginPage clickSalesforceLogin() throws SalesforceException {
        clickonLoginLink();
        waitforModalContent();
        clickonSalesforceLogin();
        return new LoginPage(driver);
    }

    public void waitforModulePageToLoad() {
        waitforSearchBarToLoad();
    }
}
