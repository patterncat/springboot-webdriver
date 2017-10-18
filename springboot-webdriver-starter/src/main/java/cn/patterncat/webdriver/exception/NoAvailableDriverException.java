package cn.patterncat.webdriver.exception;

/**
 * Created by patterncat on 2017-10-18.
 */
public class NoAvailableDriverException extends RuntimeException{

    public NoAvailableDriverException(String message, Throwable cause) {
        super(message, cause);
    }
}
