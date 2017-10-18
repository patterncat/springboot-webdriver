package cn.patterncat.webdriver.component;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * Created by patterncat on 2017-10-18.
 */
public interface DriverProcessor<T> {
    T execute(PhantomJSDriver driver);
}
