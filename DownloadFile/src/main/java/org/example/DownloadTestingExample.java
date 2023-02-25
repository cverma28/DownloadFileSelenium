package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DownloadTestingExample {
    final String expectedFileName = "sample_640×426.bmp";
    final String downloadLocation = "/Users/chandanverma/IdeaProjects/DownloadFile/Download";

    @BeforeTest
    public void cleanFolder() throws IOException {
        File directory = new File(downloadLocation);
        FileUtils.cleanDirectory(directory);
    }

    @Test
    public void downloadTest() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadLocation);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://filesamples.com/formats/bmp");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,250)", "");

        driver.findElement(By.xpath(".//*[@href='/samples/image/bmp/sample_640×426.bmp']")).click();
        js.executeScript("window.scrollBy(0,-250)", "");

        Thread.sleep(10000);
        Assert.assertTrue(isFileAvailable(), "Downloaded document is not found");
        driver.close();
    }


    @Test
    public void downloadFirefoxTest() throws InterruptedException {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", downloadLocation);
        options.addPreference("browser.download.useDownloadDir", true);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;text/plain;application/text;text/xml;application/xml");
        options.addPreference("pdfjs.disabled", true);  // disable the built-in PDF viewer

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.get("https://filesamples.com/formats/bmp");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,250)", "");

        driver.findElement(By.xpath(".//*[@href='/samples/image/bmp/sample_640×426.bmp']")).click();
        js.executeScript("window.scrollBy(0,-250)", "");

        Thread.sleep(10000);
        Assert.assertTrue(isFileAvailable(), "Downloaded document is not found");
        driver.close();
    }
    public boolean isFileAvailable(){
        File folder = new File(downloadLocation);
        File[] listOfFiles = folder.listFiles();
        boolean isFileAvailable = false;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println("File " + fileName);
                if (fileName.matches(expectedFileName)) {
                    isFileAvailable = true;
                }
            }
        }
        return isFileAvailable;
    }
}