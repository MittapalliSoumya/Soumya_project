package com.salesforce.trailhead.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.LinkedList;
import java.util.List;

public class FavouritePage extends HeaderComponent {
    private static Logger LOGGER = Logger.getLogger(FavouritePage.class);

    private WebDriver driver;
    private int MAX_AJAX_WAIT = 60;

    public FavouritePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, MAX_AJAX_WAIT), this);
        openPage();
        waitForElement(By.className("favorites-container"));
    }

    @FindBy(className = "tile-title")
    private List<WebElement> favoriteModules;


    public List<WebElement> getFavoriteModules() {
        return favoriteModules;
    }


    public void unFavoriteModules(List<String> names) {
        LOGGER.info("==> Un-favourite the given modules");
        for (String moduleName : names) {
            String locator = "//a[@title='"+ moduleName+"'][@class='tile-title']/ancestor::div[@class='tile-content']//ul/li/button";
            WebElement moduleEle = driver.findElement(By.xpath(locator));
            moduleEle.click();
            waitForElementInvisibility(By.className("unloving"));
        }
    }

    /***
     * This method is used to open the favourite page
     */
    public void openPage() {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", getAvatarBtn());
        executor.executeScript("arguments[0].click();", getFavoriteLink());
    }

    /***
     * This method is used to get the list of favourite modules
     * @return
     */
    public List<String> getFavoriteModuleList() {
        List<String> moduleList = new LinkedList<>();
        for (WebElement moduleEle : getFavoriteModules()) {
            moduleList.add(moduleEle.getText());
        }
        return moduleList;
    }
}
