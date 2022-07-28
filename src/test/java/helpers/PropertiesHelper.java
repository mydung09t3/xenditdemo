package helpers;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesHelper {
    /**
     * Read data in properties file
     *
     * @param sFile
     * @param sKey
     * @return
     */

    public static String getConfigValue(String sFile, String sKey) {
        Properties properties = new Properties();
        String sValue = null;
        try {
            FileInputStream inputStream = new FileInputStream(new File(sFile));
            properties.load(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            sValue = properties.getProperty(sKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sValue;
    }


    /**
     * Set data in properties file
     *
     * @param sFile
     * @param sKey
     * @param sValue
     */

    public static void setConfigValue(String sFile, String sKey, String sValue) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(new File(sFile));
            properties.load(fis);
            fis.close();

            FileOutputStream fos = new FileOutputStream(new File(sFile));
            properties.setProperty(sKey, sValue);
            properties.store(fos, "Update folder path");
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
