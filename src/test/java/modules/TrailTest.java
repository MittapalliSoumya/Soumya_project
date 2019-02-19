package modules;

import base.BaseTest;
import com.salesforce.trailhead.pages.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class TrailTest extends BaseTest {

    HeaderComponent headerComponent ;
    TrailsPage trailsPage ;
    TrailmixPage  trailmixPage;


    @Test
    public void testTrails(){


        // Variables specific to the test case
        String expectedAccountName = "soumya mittapalli";
        String trailName = "Learn Privacy and Data Protection Law";
        String name = "lucky" + Math.random();
        String description = "mydescription";

        headerComponent = new HeaderComponent(driver);

        LoginPage login = headerComponent.clickSalesforceLogin();

        //perform login here
        login.enterUsername(props.getProperty("salesforce.username"));
        login.enterPassword(props.getProperty("salesforce.password"));
        login.clickLogin();
        headerComponent.waitforModulePageToLoad();

        //verify login successful
        Assert.assertEquals(expectedAccountName, headerComponent.getAccountName());

        //select trails page
        trailsPage = new TrailsPage(driver);
        trailsPage.selectTrailName(trailName);
        trailmixPage = trailsPage.clickAddToNewTrail();

        //verify new trail page is opened

        Assert.assertEquals("Create a trailmix", trailmixPage.getPageTitle());

        trailmixPage.fillTrailform(name, description);
        trailmixPage.clickSubmit();
        Assert.assertEquals(trailmixPage.getTrailName(),trailName);

        //Verify trail added to trailmix
        trailmixPage.clickSave();
        Assert.assertEquals(name, trailmixPage.getTitle());
        Assert.assertEquals(description, trailmixPage.getDescritption());
        //verify trail is perent in trailmix
        Assert.assertEquals(trailmixPage.getItemTitle(),trailName);
    }

    @AfterMethod
    public void deleteTrailmix(){
        trailmixPage.clickDelete();

    }
}
