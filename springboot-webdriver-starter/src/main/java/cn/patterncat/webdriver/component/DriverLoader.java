package cn.patterncat.webdriver.component;

import cn.patterncat.webdriver.WebDriverProperties;
import cn.patterncat.webdriver.util.CapabilityUtil;
import cn.patterncat.webdriver.util.UnPacker;
import net.anthavio.phanbedder.Phanbedder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by patterncat on 2017-11-04.
 */
public class DriverLoader {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";

    public static DesiredCapabilities unpackDriverIfNeeded(WebDriverProperties properties){

        DriverType driverType = properties.getDriverType();
        String deviceName = properties.getDeviceName();

        //根据操作系统类型解压embed driver到指定路径,并配置driver路径
        if(DriverType.CHROME == driverType){
            File chrome = UnPacker.unpack(driverType);
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,chrome.getAbsolutePath());

            DesiredCapabilities dcaps = DesiredCapabilities.chrome();
            dcaps.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chrome.getAbsolutePath());
//            dcaps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

            if(!StringUtils.isEmpty(deviceName)){
                CapabilityUtil.setChromeDeviceEmulation(dcaps,deviceName);
            }

            CapabilityUtil.setLoggingPreferences(dcaps);

            return dcaps;
        }

        if(DriverType.FIREFOX == driverType){
            File firefox = UnPacker.unpack(driverType);
            System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY,firefox.getAbsolutePath());

            DesiredCapabilities dcaps = DesiredCapabilities.firefox();
            dcaps.setCapability(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, firefox.getAbsolutePath());

            FirefoxProfile profile = new FirefoxProfile();
            profile.setEnableNativeEvents(true);
            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(false);

            // Disable Firefox Auto-Updating
            profile.setPreference("app.update.auto", false);
            profile.setPreference("app.update.enabled", false);

            dcaps.setCapability(FirefoxDriver.PROFILE, profile);
            dcaps.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
//            dcaps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

            CapabilityUtil.setLoggingPreferences(dcaps);

            return dcaps;
        }

        //default is phantomjs
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent",USER_AGENT);
        if(properties.isDiskCacheEnabled()){
            dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "disk-cache",true);
        }
//        dcaps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
        CapabilityUtil.setLoggingPreferences(dcaps);

        List<String> cliArgs = new ArrayList<String>();
//        cliArgs.add( "--web-security=false" );
        cliArgs.add( "--ssl-protocol=any" );
        cliArgs.add( "--ignore-ssl-errors=true" );
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgs);
        dcaps.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
        return dcaps;
    }

    public static WebDriver newInstance(DriverType driverType,DesiredCapabilities dcaps){
        WebDriver driver = null;
        if(DriverType.CHROME == driverType){
            driver = new ChromeDriver(dcaps);
            return driver;
        }

        if(DriverType.FIREFOX == driverType){
            driver = new FirefoxDriver(dcaps);
            return driver;
        }

        driver = new PhantomJSDriver(dcaps);
        ((PhantomJSDriver)driver).setLogLevel(Level.INFO);
        return driver;
    }
}
