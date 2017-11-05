package cn.patterncat.webdriver.controller;

import cn.patterncat.webdriver.component.DriverProcessor;
import cn.patterncat.webdriver.component.WebDriverTemplate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.RotatingDecorator;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ViewportPastingDecorator;
import ru.yandex.qatools.ashot.shooting.cutter.CutStrategy;
import ru.yandex.qatools.ashot.shooting.cutter.VariableCutStrategy;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by patterncat on 2017-11-05.
 */
@RestController
@RequestMapping("/screen")
public class ScreenController {

    @Autowired
    WebDriverTemplate webDriverTemplate;

    @GetMapping("/shot")
    public void takeScreenShot(@RequestParam String url, HttpServletResponse response) throws Exception {
        response.setContentType("image/jpg");
        response.setHeader("Content-Disposition","inline");

        byte[] data = webDriverTemplate.execute(15000, new DriverProcessor<byte[]>() {
            @Override
            public byte[] execute(WebDriver driver) {
                driver.get(url);
                byte[] data = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                return data;
            }
        });

        IOUtils.write(data,response.getOutputStream());

//        BufferedImage data = webDriverTemplate.execute(15000, new DriverProcessor<BufferedImage>() {
//            @Override
//            public BufferedImage execute(WebDriver driver) {
//                driver.manage().window().setSize(new Dimension(320,568));
//                driver.get(url);
//                ShootingStrategy pasting = ShootingStrategies.viewportPasting(5000);
//                Screenshot screenshot = new AShot()
//                        .shootingStrategy(pasting)
//                        .takeScreenshot(driver);
//
//                return screenshot.getImage();
//            }
//        });
//        ImageIO.write(data,"png",response.getOutputStream());
    }
}
