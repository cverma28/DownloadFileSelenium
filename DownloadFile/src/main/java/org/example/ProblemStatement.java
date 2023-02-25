package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ProblemStatement {
    @Test
    public void downloadChromeTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://filesamples.com/formats/bmp");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,250)", "");

        driver.findElement(By.xpath(".//*[@href='/samples/image/bmp/sample_640×426.bmp']")).click();
        js.executeScript("window.scrollBy(0,-250)", "");
        Thread.sleep(10000);
        driver.close();
    }
}