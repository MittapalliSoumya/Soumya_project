package modules;

import base.BaseTest;
import com.salesforce.trailhead.pages.FavouritePage;
import com.salesforce.trailhead.pages.LoginPage;
import com.salesforce.trailhead.pages.ModulePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

public class FavoriteTest extends BaseTest {

    @Test
    public void testFavouriteButton(){

        // Variables specific to the test case
        String expectedAccountName = "soumya mittapalli";
        String moduleName_1 = " Business Value of Equality";
        String moduleName_2 = "API Basics";

        ModulePage modulePage = new ModulePage(driver);
        LoginPage login = modulePage.clickSalesforceLogin();

        //perform login here
        login.enterUsername(props.getProperty("salesforce.username"));
        login.enterPassword(props.getProperty("salesforce.password"));
        login.clickLogin();

        modulePage.waitforModulePageToLoad();

        //verify login successful
        Assert.assertEquals(expectedAccountName, modulePage.getAccountName());

        //Modules needs to be made as favorite
        List<String> moduleNames = new LinkedList<>();
        moduleNames.add(moduleName_1);
        moduleNames.add(moduleName_2);

        boolean bFavorite = modulePage.favoriteGivenModules(moduleNames);
        Assert.assertTrue(bFavorite, "Added the given modules to favorite");

        //Verify the selected favorite module
        FavouritePage favPage = modulePage.openFavoritePage();
        Assert.assertTrue(favPage.getFavoriteModuleList().contains(moduleName_1.trim()), moduleName_1.trim()+" not selected as favorite");
        Assert.assertTrue(favPage.getFavoriteModuleList().contains(moduleName_2), moduleName_1+" not selected as favorite");

        //UnFavourite the modules again
        favPage.unFavoriteModules(moduleNames);
        favPage.clickonLogoutLink();
    }
}
