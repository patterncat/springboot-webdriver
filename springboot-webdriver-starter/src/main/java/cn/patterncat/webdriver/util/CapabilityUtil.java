package cn.patterncat.webdriver.util;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by patterncat on 2017-11-05.
 */
public class CapabilityUtil {

    public static void setChromeDeviceEmulation(DesiredCapabilities dcaps,String deviceName) {
        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", deviceName);

        Map<String, Object> chromeOptions = new HashMap<>();
        chromeOptions.put("mobileEmulation", mobileEmulation);
        dcaps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    }

    public static void setLoggingPreferences(DesiredCapabilities dcaps) {
        LoggingPreferences logging = new LoggingPreferences();
        logging.enable(LogType.PERFORMANCE, Level.ALL);
        logging.enable(LogType.BROWSER, Level.ALL);
        dcaps.setCapability(CapabilityType.LOGGING_PREFS, logging);
    }
}
