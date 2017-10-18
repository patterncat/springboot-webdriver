package cn.patterncat.webdriver.component;

import cn.patterncat.webdriver.WebDriverProperties;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by patterncat on 2017-10-18.
 */
public class PooledDriverFactory extends BasePooledObjectFactory<PhantomJSDriver> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PooledDriverFactory.class);

    DesiredCapabilities dcaps;

    WebDriverProperties properties;

    public PooledDriverFactory(DesiredCapabilities dcaps, WebDriverProperties properties) {
        this.dcaps = dcaps;
        this.properties = properties;
    }

    /**
     * 这个方法是BasePooledObjectFactory额外有的,其实PooledObjectFactory接口只有makeObject
     * 这里用create替代
     * @return
     * @throws Exception
     */
    @Override
    public PhantomJSDriver create() throws Exception {
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        driver.setLogLevel(Level.INFO);
        driver.manage().timeouts().pageLoadTimeout(properties.getPageLoadTimeoutMs(), TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(properties.getScriptLoadTimeoutMs(), TimeUnit.MILLISECONDS);
        driver.manage().timeouts().implicitlyWait(properties.getImplicitlyWaitMs(), TimeUnit.MILLISECONDS);
        return driver;
    }

    @Override
    public PooledObject<PhantomJSDriver> wrap(PhantomJSDriver obj) {
        return new DefaultPooledObject<PhantomJSDriver>(obj);
    }

    /**
     * 对象钝化,从激活状态转入非激活状态,returnObject时触发
     * @param p
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<PhantomJSDriver> p) throws Exception {
        super.passivateObject(p);
    }

    /**
     * 对象激活,borrowObject时触发
     * @param p
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<PhantomJSDriver> p) throws Exception {
        super.activateObject(p);
    }

    @Override
    public boolean validateObject(PooledObject<PhantomJSDriver> p) {
        return super.validateObject(p);
    }

    /**
     * 对象销毁(clear时会触发）
     * 这里要关闭driver
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<PhantomJSDriver> p) throws Exception {
        PhantomJSDriver driver = p.getObject();
        try{
            //Quits this driver, closing every associated window
            driver.quit();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    /**
     * 这个方法可以默认是调用warp(create())
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<PhantomJSDriver> makeObject() throws Exception {
        return super.makeObject();
    }
}
