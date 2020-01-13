import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginFormTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty( "webdriver.chrome.driver", "/Users/tahs/Selenium/ChromeDriver/chromedriver79" );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait( 3, TimeUnit.SECONDS );

        driver.get( "http://testing.todvachev.com/test-scenarios/login-form/" );
    }

    @AfterClass
    public void quit() {
        driver.quit();
    }

    private String fillInForm(String username, String password) {
        WebElement text = driver.findElement( By.name( "userid" ) );
        text.sendKeys( username );
        WebElement webElement = driver.findElement( By.name( "passid" ) );
        webElement.sendKeys( password );
        WebElement textarea = driver.findElement( By.name( "repeatpassid" ) );
        textarea.sendKeys( password );
        WebElement checkbox = driver.findElement( By.name( "submit" ) );
        checkbox.click();
        String alert = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        return alert;
    }

    @Test
    public void LogIn() {
        String alert = fillInForm( "Username", "Password" );
        Assert.assertEquals( alert, "Succesful login!" );
    }

    @Test
    public void checkUserErrorMessage() {
        String alert = fillInForm( "User", "Password" );
        Assert.assertEquals( alert, "User Id should not be empty / length be between 5 to 12" );
    }

    @Test
    public void checkUserErrorMessageTooBig() {
        String alert = fillInForm( "asdasdfasdgasddgafdg", "Password" );
        Assert.assertEquals( alert, "User Id should not be empty / length be between 5 to 12" );

    }

    @Test
    public void checkUserErrorMessageEmpty() {
        String alert = fillInForm( "", "Password" );
        Assert.assertEquals( alert, "User Id should not be empty / length be between 5 to 12" );

    }

    @Test
    public void checkPasswordErrorMessage() {
        String alert = fillInForm( "Username", "Pass" );
        Assert.assertEquals( alert, "Password should not be empty / length be between 5 to 12" );
    }

    @Test
    public void checkPasswordErrorMessageTooBig() {
        String alert = fillInForm( "Usernmae", "asdasdfasdgasddgafdg" );
        Assert.assertEquals( alert, "Password should not be empty / length be between 5 to 12" );

    }

    @Test
    public void checkPasswordErrorMessageEmpty() {
        String alert = fillInForm( "Usernmae", "" );
        Assert.assertEquals( alert, "Password should not be empty / length be between 5 to 12" );

    }

    @BeforeMethod
    public void cleanInput(){
        WebElement text = driver.findElement( By.name( "userid" ) );
        text.clear();
        WebElement webElement = driver.findElement( By.name( "passid" ) );
        webElement.clear();
        WebElement textarea = driver.findElement( By.name( "repeatpassid" ) );
        textarea.clear();
    }

}

