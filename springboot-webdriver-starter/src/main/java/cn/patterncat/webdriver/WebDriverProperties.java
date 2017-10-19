package cn.patterncat.webdriver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by patterncat on 2017-10-18.
 */
@ConfigurationProperties(prefix = "webdriver")
public class WebDriverProperties {

    private boolean enabled = true;

    private int poolMaxTotal = 8;

    private int poolMaxIdle = 8; //生产上建议跟max total一致,避免makeObject开销

    private int poolMinIdle = 4;

    private boolean preparePool = true;

    private int pageLoadTimeoutMs = 30*1000;

    private int scriptLoadTimeoutMs = 15*1000;

    private int implicitlyWaitMs = 15*1000;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(int poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    public int getPageLoadTimeoutMs() {
        return pageLoadTimeoutMs;
    }

    public void setPageLoadTimeoutMs(int pageLoadTimeoutMs) {
        this.pageLoadTimeoutMs = pageLoadTimeoutMs;
    }

    public int getScriptLoadTimeoutMs() {
        return scriptLoadTimeoutMs;
    }

    public void setScriptLoadTimeoutMs(int scriptLoadTimeoutMs) {
        this.scriptLoadTimeoutMs = scriptLoadTimeoutMs;
    }

    public int getImplicitlyWaitMs() {
        return implicitlyWaitMs;
    }

    public void setImplicitlyWaitMs(int implicitlyWaitMs) {
        this.implicitlyWaitMs = implicitlyWaitMs;
    }

    public boolean isPreparePool() {
        return preparePool;
    }

    public void setPreparePool(boolean preparePool) {
        this.preparePool = preparePool;
    }
}
