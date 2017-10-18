package cn.patterncat.webdriver;

import cn.patterncat.webdriver.component.PooledDriverFactory;
import cn.patterncat.webdriver.component.WebDriverPool;
import cn.patterncat.webdriver.component.WebDriverTemplate;
import net.anthavio.phanbedder.Phanbedder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by patterncat on 2017-10-18.
 */
@Configuration
@ConditionalOnClass({PhantomJSDriver.class})
@ConditionalOnProperty(
        prefix = "webdriver",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties(WebDriverProperties.class)
public class WebDriverAutoConfiguration {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";

    private final WebDriverProperties properties;

    public WebDriverAutoConfiguration(WebDriverProperties properties) {
        this.properties = properties;
    }

    @Bean
    public PooledDriverFactory pooledDriverFactory(){
        File phantomjs = Phanbedder.unpack();
        DesiredCapabilities dcaps = new DesiredCapabilities();
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjs.getAbsolutePath());
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX + "userAgent",USER_AGENT);

        PooledDriverFactory factory = new PooledDriverFactory(dcaps,properties);
        return factory;
    }

    @Bean
    public WebDriverPool webDriverPool(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(properties.getPoolMaxTotal());
        poolConfig.setMinIdle(properties.getPoolMinIdle()); //init pool size
        poolConfig.setMaxIdle(properties.getPoolMaxIdle());
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnCreate();
//        poolConfig.setTestOnReturn();
//        poolConfig.setTestWhileIdle();
//        poolConfig.setBlockWhenExhausted();
//        poolConfig.setMaxWaitMillis();
//        poolConfig.setLifo();
//        poolConfig.setMinEvictableIdleTimeMillis();
//        poolConfig.setNumTestsPerEvictionRun();
//        poolConfig.setSoftMinEvictableIdleTimeMillis();
//        poolConfig.setEvictionPolicyClassName();
//        poolConfig.setFairness();
        return new WebDriverPool(pooledDriverFactory(),poolConfig);
    }

    @Bean
    public WebDriverTemplate webDriverTemplate(){
        return new WebDriverTemplate(webDriverPool());
    }
}