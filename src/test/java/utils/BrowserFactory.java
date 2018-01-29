package utils;

import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import constants.Browsers;
import constants.RemoteBrowsers;

public class BrowserFactory {

    private static final String BROWSER_PROP_KEY = "browser";
    private static final String REMOTE_BROWSER_PROP_KEY = "remoteBrowser";
    private static final String REMOTE_ADDRESS = "remoteAddress";

    /**
     * creates the browser driver specified in the system property "browser" if no property is set then a firefox browser driver is created. The allow
     * properties are firefox, safari and chrome e.g to run with safari, pass in the option -Dbrowser=safari at runtime
     * @return WebDriver
     */
    public static WebDriver getBrowser() {
        Browsers browser;
        RemoteBrowsers remoteBrowser;
        WebDriver driver = null;

        if (System.getProperty(BROWSER_PROP_KEY) == null) {
            browser = Browsers.FIREFOX;
        } else {
            browser = Browsers.browserForName(System.getProperty(BROWSER_PROP_KEY));
        }
        switch (browser) {
            case CHROME:
                driver = createChromeDriver();
                break;
            case SAFARI:
                driver = createSafariDriver();
                break;
            case FIREFOX:
                driver = createFirefoxDriver(getFirefoxProfile());
                break;
            case REMOTE:

                if (System.getProperty(REMOTE_BROWSER_PROP_KEY) == null) {
                    remoteBrowser = RemoteBrowsers.FIREFOX;
                } else {
                    remoteBrowser = RemoteBrowsers.browserForName(System.getProperty(REMOTE_BROWSER_PROP_KEY));
                }

                DesiredCapabilities capability = null;

                switch (remoteBrowser) {
                    case FIREFOX:
                        capability = DesiredCapabilities.firefox();
                        break;
                    case CHROME:
                        capability = DesiredCapabilities.chrome();
                        break;
                    case SAFARI:
                        capability = DesiredCapabilities.safari();
                        break;
                    case IE:
                        capability = DesiredCapabilities.internetExplorer();
                        break;
                }

                try {
                    driver = new RemoteWebDriver(new URL(System.getProperty(REMOTE_ADDRESS)), capability);
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                driver = createFirefoxDriver(getFirefoxProfile());
                break;
        }
        addAllBrowserSetup(driver);

        return driver;
    }

    private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }

    private static WebDriver createChromeDriver() {
        if(System.getProperty("os.name").startsWith("Windows")) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        }

        return new ChromeDriver();
    }

    private static WebDriver createFirefoxDriver(FirefoxProfile firefoxProfile) {
        if(System.getProperty("os.name").startsWith("Windows")) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        }

        FirefoxOptions ffoptions = new FirefoxOptions();
        ffoptions.setProfile(firefoxProfile);
        return new FirefoxDriver(ffoptions.toCapabilities());
    }

    private static FirefoxProfile getFirefoxProfile() {
        String path = getFilePath();
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setAcceptUntrustedCertificates(true);
        firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
        firefoxProfile.setPreference("intl.accept_languages", "de");
        firefoxProfile.setPreference("browser.download.folderList", 2);
        firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
        firefoxProfile.setPreference("browser.download.dir", path);
        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/csv,application/excel,application/vnd.ms-excel,application/vnd.msexcel,text/anytext,text/comma-separated-values,text/csv,text/plain,text/x-csv,application/x-csv,text/x-comma-separated-values,text/tab-separated-values");
        firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);

        return firefoxProfile;
    }

    private static void addAllBrowserSetup(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(0, 0));
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
        driver.manage().window().setSize(dim);
    }

    private static String getFilePath(){
        String filePath;
        if(System.getProperty("os.name").startsWith("Windows")) {
            filePath = "C:\\Users\\Erik\\Downloads\\";
        } else {
            filePath = "$HOME/Downloads/";
        }
        return filePath;
    }
}
