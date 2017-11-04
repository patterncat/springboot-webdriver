package cn.patterncat.webdriver.component;

import lombok.experimental.Delegate;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.WebDriver;

/**
 * Created by patterncat on 2017-10-18.
 */
public class WebDriverPool {

    @Delegate
    GenericObjectPool<WebDriver> innerPool;

    public WebDriverPool(PooledObjectFactory<WebDriver> factory, GenericObjectPoolConfig config) {
        this.innerPool = new GenericObjectPool<WebDriver>(factory, config);
    }
}
