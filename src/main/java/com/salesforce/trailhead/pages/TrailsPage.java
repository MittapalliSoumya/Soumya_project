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

public class TrailsPage extends HeaderComponent{

    private static Logger LOGGER = Logger.getLogger(FavouritePage.class);
    private WebDriver driver;
    private int MAX_AJAX_WAIT = 6;

    public TrailsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, MAX_AJAX_WAIT), this);
        open();
        waitforSearchBarToLoad();
    }

    @FindBy(linkText = "Trails")
    private WebElement trailsLink;


    @FindBy(xpath = "//ul[contains(@class,'content-actions')]//button[contains(@class,'tds-button_icon-medium')]")
    private WebElement addbutton;

    @FindBy(css = ".th-trailmix-list__heading-link")
    private WebElement addToNewTrail;



    public WebElement getTrailsLink() {
        return trailsLink;
    }


    /***
     * This method is used to open Modules page
     */
    public void open() {
        getTrailsLink().click();
    }

    /***
     * This method is used to favourite given module with their titles
     * @param
     * @return
     * @throws SalesforceException
     */
    public void selectTrailName(String trailName) throws SalesforceException {

        try {
            //Learn Privacy and Data Protection Law

          String name = trailName.replaceAll("\\s+","-").toLowerCase();
            String locator="[data-test='tile-title'] a[href*='"+name+"']";

                WebElement moduleEle = driver.findElement(By.cssSelector(locator));
                moduleEle.click();

        } catch (NoSuchElementException e) {
            LOGGER.error("Unable to find one of the given module", e);
            throw new SalesforceException("Unable to find one of the given module name", e);
        }

    }



    public TrailmixPage clickAddToNewTrail(){
        addbutton.click();
        addToNewTrail.click();
        return new TrailmixPage(driver);

    }





}
