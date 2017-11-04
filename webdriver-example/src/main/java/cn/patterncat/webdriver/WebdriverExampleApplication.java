package cn.patterncat.webdriver;

import cn.patterncat.webdriver.component.DriverProcessor;
import cn.patterncat.webdriver.component.WebDriverTemplate;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.patterncat.webdriver"})
public class WebdriverExampleApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(WebdriverExampleApplication.class, args);
	}

	@Autowired
	WebDriverTemplate webDriverTemplate;

	@Override
	public void run(String... strings) throws Exception {
		webDriverTemplate.execute(15000, new DriverProcessor<Void>() {
			@Override
			public Void execute(WebDriver driver) {
				driver.get("https://www.baidu.com/");
				try{
					WebDriverWait webDriverWait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(15000),500); //默认500ms轮询一次
					webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("demo_wait_class")));
				}catch (Exception e){
					//when timeout and element not found
//					LOGGER.error("web driver wait element timeout",e);
				}
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				try {
					FileUtils.copyFile(scrFile, new File("screenshot.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
}
