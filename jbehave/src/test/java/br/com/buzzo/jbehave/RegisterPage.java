package br.com.buzzo.jbehave;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends WebDriverPage {

    private static boolean SLOW = false;

    /**
     * Descomente para ver os testes executando mais lentamente.
     */
    static {
        RegisterPage.SLOW = true;
    }

    public RegisterPage(final WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public String getRegisterMessage() {
        slower();
        final By id = By.xpath("//ul[@class='messages']/li");
        new WebDriverWait(this, 10).until(ExpectedConditions.visibilityOfElementLocated(id));
        return findElement(id).getText().trim();
    }

    public void open() {
        get("http://localhost:8080/jboss-as-kitchensink/index.jsf");
        manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    public void register() {
        slower();
        findElement(By.id("reg:register")).click();
    }

    public void type_email(final String email) {
        slower();
        findElement(By.id("reg:email")).sendKeys(email);
    }

    public void type_name(final String name) {
        slower();
        findElement(By.id("reg:name")).sendKeys(name);
    }

    public void type_phone(final String phoneNumber) {
        slower();
        findElement(By.id("reg:phoneNumber")).sendKeys(phoneNumber);
    }

    /**
     * Utilizado para deixar a execução mais lenta e visivel. Não deve ser utilizado em produção.
     */
    private void slower() {
        if (RegisterPage.SLOW) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
