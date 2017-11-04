package cn.patterncat.webdriver.component;

import cn.patterncat.webdriver.exception.NoDriverAvailableException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;

/**
 * Created by patterncat on 2017-10-18.
 */
public class WebDriverTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverTemplate.class);

    WebDriverPool pool;

    public WebDriverTemplate(WebDriverPool pool) {
        this.pool = pool;
    }

    public <T> T execute(long timeoutInMs,DriverProcessor<T> processor) throws Exception {
        WebDriver driver = null;
        try{
            driver = pool.borrowObject(timeoutInMs); //borrow不到会抛异常
            T result = processor.execute(driver);
            return result;
        }catch (NoSuchElementException e){
            //pool资源耗尽
//            LOGGER.error(e.getMessage(),e);
            throw new NoDriverAvailableException(e.getMessage(),e);
        }finally{
            if(driver != null){
                pool.returnObject(driver);
            }
        }
    }
}
