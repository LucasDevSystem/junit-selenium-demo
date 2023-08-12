package com.example.seleniumdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleTest {

    WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Indica onde está o driver do firefox
        // substituir pelo seu diretorio absoluto
        System.setProperty("webdriver.gecko.driver", "/Users/mac/Fonts/selenium-junit-demo/selenium-demo/geckodriver");

        driver = new FirefoxDriver();
    }

    @Test
    public void testAcessarGoogleDoodles() {
        driver.navigate().to("http://www.google.com");

        WebElement estouComSorteButton = driver.findElement(By.name("btnI"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", estouComSorteButton);

        // Esperar até que a página Google Doodles seja acessada
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Google Doodles"));

        String pageTitle = driver.getTitle();
        assertEquals("Google Doodles", pageTitle);
    }

    @Test
    public void testFormLogin() {
        driver.navigate().to("http://the-internet.herokuapp.com/login");
        // elementos do texto
        WebElement element = driver.findElement(By.className("subheader"));
        List<WebElement> elements = element.findElements(By.tagName("em"));

        // elementos do formulario
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement submitBtn = driver.findElement(By.className("radius"));

        // valida os textos dos elementos
        assertEquals(2, elements.size());
        assertEquals("tomsmith", elements.get(0).getText());
        assertEquals("SuperSecretPassword!", elements.get(1).getText());

        String username = elements.get(0).getText();
        String password = elements.get(1).getText();

        // preenche o formulario
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        // submete os dados; como se fosse dado um "enter"
        submitBtn.submit();
        // valida se entrou na pagina secure
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.urlContains("secure"));

        WebElement welcomeEl = driver.findElement(By.className("subheader"));

        assertEquals("Welcome to the Secure Area. When you are done click logout below.", welcomeEl.getText());
    }

    @AfterEach
    public void closing() throws Exception {
        // Espera 5 segundos e fecha a janela do browser
        Thread.sleep(5000);
        driver.quit();
    }
}
