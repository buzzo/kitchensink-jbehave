package br.com.buzzo.jbehave;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends WebDriverPage {

    public HomePage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        // TODO: url deve ser mais flexivel. buscar em um system.property para controlar no maven
        get("http://localhost:8080/jboss-as-kitchensink/");
        manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void type_name(final String name) {
        findElement(By.id("reg:name")).sendKeys(name);
    }

    public void type_email(final String email) {
        findElement(By.id("reg:email")).sendKeys(email);
    }

    public void type_phone(final String phoneNumber) {
        findElement(By.id("reg:phoneNumber")).sendKeys(phoneNumber);
    }

    public void register() {
        findElement(By.id("reg:register")).click();
    }

    public String getRegisterMessage() {
        final By id = By.xpath("//li[@class='valid']");
        new WebDriverWait(this, 10).until(ExpectedConditions.visibilityOfElementLocated(id));
        return findElement(id).getText().trim();
    }
}
