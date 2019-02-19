package com.salesforce.trailhead.common;

import org.apache.log4j.Logger;

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
}
