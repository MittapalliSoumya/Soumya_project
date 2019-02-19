package com.salesforce.trailhead.pages;

import com.salesforce.trailhead.exceptions.SalesforceException;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class TrailmixPage extends HeaderComponent {

    private static Logger LOGGER = Logger.getLogger(FavouritePage.class);
    private WebDriver driver;
    private int MAX_AJAX_WAIT = 6;

    public TrailmixPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, MAX_AJAX_WAIT), this);
        waitforSearchBarToLoad();
    }



    @FindBy(className = "slds-m-vertical_small")
    private WebElement getTitle;

    @FindBy(id = "trailmix_name")
    private WebElement trailmixName;

    @FindBy(id = "trailmix_description")
    private WebElement trailmixDesc;

    @FindBy(xpath = "//button[contains(@class,'slds-button_neutral')]")
    private WebElement trailmix_slug;

    @FindBy(className = "slds-tooltip-trigger")
    private WebElement save;

    @FindBy(css = ".content-title")
    private WebElement getContentTitle;

    @FindBy(css = ".content-description")
    private WebElement getDescritpion;

    @FindBy(css = ".trailmix__destroy .slds-tooltip-trigger")
    private WebElement deleteTrail;

    @FindBy(css = ".slds-button_destructive")
    private WebElement deleteConfirmation;

    @FindBy(css = ".slds-truncate a")
    private WebElement trailName;

    @FindBy(css = ".item-title a")
    private WebElement itemTittle;

    public WebElement getTrailmixName() {
        return trailmixName;
    }

    public WebElement gettrailmixDesc() {return trailmixDesc; }



    public String getPageTitle(){
       return getTitle.getText();
    }

    public String getTrailName() { return trailName.getText();}

    public void  fillTrailform(String name, String description){
            getTrailmixName().sendKeys(name);
            gettrailmixDesc().sendKeys(description);

    }


    public void clickSubmit() throws SalesforceException {
        try {
            WebElement submitEle = trailmix_slug;
            if(submitEle.isDisplayed() && submitEle.isEnabled()){
                submitEle.click();
            } else {
                LOGGER.error("Unable to click on submit button");
                throw new SalesforceException("Unable to click the submit button as it is not displayed or enabled");
            }
        } catch(NoSuchElementException e) {
            LOGGER.error("Unable to find the element", e);
        }
    }


    public void clickSave(){
        save.click();

    }

    public String getTitle(){
        return getContentTitle.getText();
    }

    public String getDescritption(){
        return getDescritpion.getText();
    }

    public String getItemTitle(){ return itemTittle.getText();}


    /***
     * This method is used to click on delete trail button
     */
    public void clickDelete(){

        deleteTrail.click();
        deleteConfirmation.click();
    }

}
