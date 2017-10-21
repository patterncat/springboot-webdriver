package cn.patterncat.webdriver.exception;

/**
 * Created by patterncat on 2017-10-18.
 */
public class NoDriverAvailableException extends RuntimeException{

    public NoDriverAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
