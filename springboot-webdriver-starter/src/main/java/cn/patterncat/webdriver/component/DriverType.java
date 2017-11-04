package cn.patterncat.webdriver.component;

/**
 * Created by patterncat on 2017-11-04.
 */
public enum DriverType {
    CHROME("chromedriver"),FIREFOX("firefoxdriver"),PHANTOM_JS("phantomjs");

    String filename;

    DriverType(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
