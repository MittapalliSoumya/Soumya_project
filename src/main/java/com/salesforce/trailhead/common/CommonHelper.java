package com.salesforce.trailhead.common;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonHelper {
    private static Logger LOGGER = Logger.getLogger(CommonHelper.class);


    /***
     * This method is used to read a given properties file
     * @param filename - name of the properties file it has to read
     * @return Properties
     */
    public Properties readProperties(String filename) throws IOException{
        LOGGER.info("==> readProperties of : " + filename);

        Properties appProps = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
        appProps.load(stream);

        LOGGER.info("<== readProperties of : " + filename);
        return appProps;
    }


    /***
     * This method is used to capture screen shot
     * @param driver,screenshotName - parameters capture screenshot
     * @return Properties
     */
    public static void captureScreenshot(WebDriver driver, String screenshotName)

    {

            try
            {
                TakesScreenshot ts=(TakesScreenshot)driver;

                File source=ts.getScreenshotAs(OutputType.FILE);

                FileUtils.copyFile(source, new File("./Screenshots/"+screenshotName+".png"));

                System.out.println("Screenshot taken");
            }
            catch (Exception e)
            {

                System.out.println("Exception while taking screenshot "+e.getMessage());
            }
        }
    }

