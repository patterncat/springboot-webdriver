## TODO
- phantomjs not support promise
- chrome and firefox not support full screen shot
>如果页面是适配移动端一屏可以展示完整,则可以设置driver的emulation

# version
## 0.0.7
- remove ForSeleniumServer.ENSURING_CLEAN_SESSION

## 0.0.6
- add ssl config for phantomjs
- add testOnBorrow,testOnReturn,testOnCreate,testWhileIdle config
- add validate driver logic

## 0.05
- chrome driver 2.33 and support chrome 60-62
- firefox driver v0.19.1
- phantomjs version 2.1.1

>内置了chrome\firefox的不同操作系统的driver,jar包增大到25M

## 0.0.4
- phantomjs version 2.1.1
- update phantomjsdriver to 1.4.3
- update selenium version to 3.4.0

change selenum dependency to provided
need to add selenium-api,remote-driver,support dependency manually
```
        <dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-api</artifactId>
			<version>3.4.0</version>
		</dependency>
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-remote-driver</artifactId>
			<version>3.4.0</version>
		</dependency>
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-support</artifactId>
			<version>3.4.0</version>
		</dependency>
```

## 0.0.3
change exception to NoDriverAvailableException
and uncomment log error in template catch

## 0.0.2
add prepare pool config

## 0.0.1
basic finish driver config
- phantomjs version 2.1.1
- phantomjsdriver version 1.2.1
- selenium version 2.44 or 2.45

# 相关组件
![](https://assertselenium.files.wordpress.com/2013/03/screen-shot-2013-03-24-at-11-54-01-pm.png)

## phantomjs
>phantomis就是一个基于webkit的无界面浏览器
>最新版2.1.1

## Ghost Driver
>Ghost Driver is a pure JavaScript implementation of the WebDriver Wire Protocol for PhantomJS. It's a Remote WebDriver that uses PhantomJS as back-end.

>ghost driver现在已经跟phantomjs合体，所以安装好最新版本的phantomjs就等于安装好了ghostdriver

>Because of latest stable GhostDriver being embedded in PhantomJS, you shouldn't need anything else to get started.

## Selenium
>Selenium是一个用于Web应用程序测试的工具。Selenium测试直接运行在浏览器中，就像真正的用户在操作一样。

### Selenium WebDriver
>Selenium 1.0 + WebDriver = Selenium 2.0
>Selenium 2，又名 WebDriver，它的主要新功能是集成了 Selenium 1.0 以及 WebDriver（WebDriver 曾经是 Selenium 的竞争对手）。也就是说 Selenium 2 是 Selenium 和 WebDriver 两个项目的合并，即 Selenium 2 兼容 Selenium，它既支持 Selenium API 也支持 WebDriver API。 WebDriver是一个用来进行复杂重复的web自动化测试的工具。意在提供一种比Selenium1.0更简单易学，有利于维护的API。它没有和任何测试框架进行绑定，所以他可以很好的在单元测试和main方法中调用。一旦创建好一个Selenium工程，你马上会发现WebDriver和其他类库一样：它是完全独立的，你可以直接使用而不需要考虑其他配置，这个Selenium RC是截然相反的。

>WebDriver is the name of the key interface against which tests should be written in Java, the implementing classes one should use are listed as below:

- ChromeDriver
- EventFiringWebDriver
- FirefoxDriver
- HtmlUnitDriver
- InternetExplorerDriver
- PhantomJSDriver
- RemoteWebDriver
- SafariDriver

## chrome driver
- [chromedriver-2.33](http://chromedriver.storage.googleapis.com/index.html?path=2.33/)
>Supports Chrome v60-62

## firefox driver
- [geckodriver](https://github.com/mozilla/geckodriver/releases)

## doc
- [phanbedder](https://github.com/anthavio/phanbedder)
- [webdriver](http://www.51testing.com/zhuanti/webdriver.htm)
- [How To Capture Full Page Screenshot Using Selenium WebDriver](http://www.softwaretestingmaterial.com/how-to-capture-full-page-screenshot-using-selenium-webdriver/)