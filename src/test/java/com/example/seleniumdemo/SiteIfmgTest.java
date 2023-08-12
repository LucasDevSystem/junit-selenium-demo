package com.example.seleniumdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SiteIfmgTest {
    WebDriver driver;
    final String endpoint = "https://www.ifmg.edu.br/sabara";

    @BeforeEach
    public void setUp() {
        // Indica onde está o driver do firefox
        System.setProperty("webdriver.gecko.driver", "/Users/mac/Fonts/selenium-junit-demo/selenium-demo/geckodriver");

        driver = new FirefoxDriver();
    }

    @Test
    public void test() {
        // navega para o site
        driver.navigate().to(endpoint);

        // obtem o elemento do campo de pesquisa
        WebElement element = driver.findElement(By.id("nolivesearchGadget"));

        // preenche o campo de pesquisa e envia
        element.sendKeys("curso");
        element.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.titleContains("Busca"));
        
        WebElement resultTitleEl = driver.findElement(By.className("highlightedSearchTerm"));
         
        assertTrue(resultTitleEl.getText().contains("curso"));
    }

    @Test
    public void testPortalServicosList() {
        driver.navigate().to(endpoint);

        WebElement sobreEl = driver.findElement(By.id("sobre"));
        WebElement servicosEl = sobreEl.findElement(By.tagName("ul"));
        List<WebElement> servicosListEl = servicosEl.findElements(By.tagName("li"));

        assertEquals("Meu IFMG", servicosListEl.get(0).getText(), "O servico Meu IFMG esta indisponivel");
        assertEquals("CPA", servicosListEl.get(1).getText(), "O servico CPA esta indisponivel");
        assertEquals("Webmail", servicosListEl.get(2).getText(), "O servico Webmail esta indisponivel");
        assertEquals("Contato", servicosListEl.get(3).getText(), "O servico Contato esta indisponivel");
        assertEquals("Ex-Alunos", servicosListEl.get(4).getText(), "O servico Ex-Alunos esta indisponivel");
        assertEquals("Acesso a Sistemas", servicosListEl.get(5).getText(),
                "O servico Acesso a Sistemas esta indisponivel");
    }

    @AfterEach
    public void closing() throws Exception {
        // Espera 5 segundos e fecha a janela do browser
        Thread.sleep(5000);
        driver.quit();
    }
}
