package refer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class Login {
	
	WebDriver driver = new FireFoxDriver();
	
	
  @Test
  public void f() {
	  
	  driver.findElement(By.id("username")).sendKeys("giri7@gmail.com");
	  driver.findElement(By.id("password")).sendKeys("sales777");
	  driver.findElement(By.id("Login")).click();
  }
  
  
  @BeforeMethod
  public void beforeMethod() {
	  driver.get("https://developer.salesforce.com/");
	  driver.findElement(By.id("login-button")).click();
	  
  }

  @AfterMethod
  public void afterMethod() {
	 
	  driver.close();
	  
  }

}
