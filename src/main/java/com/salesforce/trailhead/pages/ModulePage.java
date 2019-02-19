package com.salesforce.trailhead.pages;

import com.salesforce.trailhead.exceptions.SalesforceException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ModulePage extends HeaderComponent {
    private static Logger LOGGER = Logger.getLogger(FavouritePage.class);

    private WebDriver driver;
    private int MAX_AJAX_WAIT = 60;
    private String ADD_FAVORITE_MSG = "Added to Favorites";

    public ModulePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, MAX_AJAX_WAIT), this);
        open();
        waitforModulePageToLoad();
    }

    @FindBy(className = "slds-notify__content")
    private WebElement growlText;

    @FindBy(linkText = "Modules")
    private WebElement modulesLink;

    public WebElement getGrowlText() {
        return growlText;
    }

    public WebElement getModulesLink() {
        return modulesLink;
    }

    /***
     * This method is used to favourite given module with their titles
     * @param names - list of titles
     * @return
     * @throws SalesforceException
     */
    public boolean favoriteGivenModules(List<String> names) throws SalesforceException {
        boolean bFavorite = true;
        try {
            for (String moduleName : names) {
                String locator = "//a[@title='"+ moduleName+"'][@class='tile-title']/ancestor::div[@class='tile-content']//ul/li/button";
                WebElement moduleEle = driver.findElement(By.xpath(locator));
                moduleEle.click();
                waitForElementInvisibility(By.className("loving"));
                System.out.println(getGrowlText().getText());
                if (!getGrowlText().getText().equalsIgnoreCase(ADD_FAVORITE_MSG)) {
                    bFavorite = false;
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            bFavorite = false;
            LOGGER.error("Unable to find one of the given module", e);
            throw new SalesforceException("Unable to find one of the given module name", e);
        }
        return bFavorite;
    }



    /***
     * This method is used to open Modules page
     */
    public void open() {
        getModulesLink().click();
    }


}
