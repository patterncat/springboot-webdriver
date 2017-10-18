package cn.patterncat.webdriver;

import cn.patterncat.webdriver.component.DriverProcessor;
import cn.patterncat.webdriver.component.WebDriverTemplate;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;

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
		webDriverTemplate.execute(10000, new DriverProcessor<Void>() {
			@Override
			public Void execute(PhantomJSDriver driver) {
				driver.get("https://www.baidu.com/");
				File scrFile = driver.getScreenshotAs(OutputType.FILE);
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
