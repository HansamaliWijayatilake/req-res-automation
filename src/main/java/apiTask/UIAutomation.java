package apiTask;

import Pages.FirstPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UIAutomation {

    public static final String BASE_URL = "http://the-internet.herokuapp.com/";
    WebDriver driver;

    @BeforeClass
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void task01(){
        driver.get(BASE_URL+"tables");
        FirstPage firstPage = new FirstPage(driver);

        String row2 = firstPage.accessTableRows(2);
        String row3 = firstPage.accessTableRows(3);
        String row4 = firstPage.accessTableRows(4);
        String row5 = firstPage.accessTableRows(5);


        System.out.println(row2);
        System.out.println(row3);
        System.out.println(row4);
        System.out.println(row5);

        Map<String,String> thirdRowVals = new HashMap<>();

        thirdRowVals.put("Last Name", "Doe");
        thirdRowVals.put("First Name", "Jason");
        thirdRowVals.put("Email", "jdoe@hotmail.com");
        thirdRowVals.put("Due", "$100.00");
        thirdRowVals.put("Website", "http://www.jdoe.com");
        thirdRowVals.put("Action", "edit");
        thirdRowVals.put("Action", "delete");

        String [] arr = row4.split(" ");
        for(String a: arr){
             if(thirdRowVals.values().contains(a)){
                Assert.assertTrue(thirdRowVals.values().contains(a.trim()));

           }
        }

        driver.close();

    }


    @Test
    public void task02(){

        driver.get(BASE_URL+"dropdown");
        WebElement dropdown = driver.findElement(By.id("dropdown"));
        dropdown.click();
        String vals =  dropdown.getText();

        Assert.assertTrue(vals.contains("Option 1"));
        Assert.assertTrue(vals.contains("Option 2"));


         Select selection = new Select(dropdown);
         selection.selectByValue("1");

         String selected = selection.getFirstSelectedOption().getText();
         Assert.assertTrue(selected.equals("Option 1"));

    }

    @Test
    public void task003(){

        driver.get(BASE_URL+"hovers");
        WebElement hover01 = driver.findElement(By.xpath("//div[@id='content']/div/div[@class='figure'][1]"));
        String name01 = hover01.getText();

        WebElement hover02 = driver.findElement(By.xpath("//div[@id='content']/div/div[@class='figure'][2]"));
        String name02 = hover02.getText();

        WebElement hover03 = driver.findElement(By.xpath("//div[@id='content']/div/div[@class='figure'][3]"));
        String name03 = hover03.getText();

        System.out.println("Name 01: " + name01);
        System.out.println("Name 02: " + name02);
        System.out.println("Name 03: " + name03);

        WebElement hover02Click = driver.findElement(By.xpath("//div[@id='content']/div/div[@class='figure'][2]/div/a"));
        hover02Click.click();

    }

    @Test
    public void task04(){
        driver.get(BASE_URL+"windows");
        WebElement link = driver.findElement(By.xpath("//div[@class='example']/a"));
        link.click();

        List<String> browserTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs .get(1));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='example']")).isDisplayed());
    }
}
