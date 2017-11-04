package cn.patterncat.webdriver;

import cn.patterncat.webdriver.component.*;
import net.anthavio.phanbedder.Phanbedder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import java.io.File;
import java.util.stream.IntStream;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverAutoConfiguration.class);

    private final WebDriverProperties properties;

    public WebDriverAutoConfiguration(WebDriverProperties properties) {
        this.properties = properties;
    }

    @Bean
    public PooledDriverFactory pooledDriverFactory(){
        DesiredCapabilities dcaps = DriverLoader.unpackDriverIfNeeded(properties.getDriverType());
        PooledDriverFactory factory = new PooledDriverFactory(dcaps,properties);
        return factory;
    }

    @Bean(destroyMethod = "close") //default is close() or shutdown()
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
    public WebDriverTemplate webDriverTemplate(WebDriverPool pool){
        return new WebDriverTemplate(pool);
    }

    @Bean
    public CommandLineRunner preparePoolRunner(WebDriverPool pool){
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                if(properties.isPreparePool()){
                    StopWatch stopWatch = new StopWatch("prepare pool");
                    stopWatch.start();
                    LOGGER.info("start to prepare pool size:{}",properties.getPoolMinIdle());
                    pool.preparePool();
                    stopWatch.stop();
                    LOGGER.info("finish prepare pool size:{},cost:{}",properties.getPoolMinIdle(),stopWatch.prettyPrint());
                }
            }
        };
    }
}
