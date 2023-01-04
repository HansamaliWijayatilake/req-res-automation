package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class FirstPage {

    WebDriver driver;
    @FindBy(id="table1")
    WebElement table01;

    List<WebElement> rows;
    List<WebElement> cols;


    public FirstPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }



    public String accessTableRows(int row) {
        if (table01.findElements(By.tagName("tr")).size() == 0) {
            return null;
        }

        String columnValue = null;
        String rowValue = null;
        rows = table01.findElements(By.tagName("tr"));
//        for (int i = 0; i < row; i++) {
//            cols = rows.get(row).findElements(By.tagName("td"));
//            if (col <= cols.size()) {
//                columnValue = cols.get(col - 1).getText();
//            }
//        }

        for (int i = 0; i < row; i++) {
            //cols = rows.get(row).findElements(By.tagName("td"));
            if (row <= rows.size()) {
                rowValue = rows.get(row - 1).getText();
            }
        }


        return rowValue;
    }
}
