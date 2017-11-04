package cn.patterncat.webdriver.component;

import cn.patterncat.webdriver.util.UnPacker;
import net.anthavio.phanbedder.Phanbedder;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by patterncat on 2017-11-04.
 */
public class DriverLoader {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";

    public static DesiredCapabilities unpackDriverIfNeeded(DriverType driverType){
        DesiredCapabilities dcaps = new DesiredCapabilities();

        //根据操作系统类型解压embed driver到指定路径,并配置driver路径
        if(DriverType.CHROME == driverType){
            File chrome = UnPacker.unpack(driverType);
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,chrome.getAbsolutePath());
            dcaps.setCapability(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chrome.getAbsolutePath());
            return dcaps;
        }

        if(DriverType.FIREFOX == driverType){

            return dcaps;
        }

        //default is phantomjs
        File phantomjs = Phanbedder.unpack();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent",USER_AGENT);
        return dcaps;
    }

    public static WebDriver newInstance(DriverType driverType,DesiredCapabilities dcaps){
        WebDriver driver = null;
        if(DriverType.CHROME == driverType){
            driver = new ChromeDriver(dcaps);
            return driver;
        }

        if(DriverType.FIREFOX == driverType){
            driver = new FirefoxDriver();
            return driver;
        }

        driver = new PhantomJSDriver(dcaps);
        ((PhantomJSDriver)driver).setLogLevel(Level.INFO);
        return driver;
    }
}
