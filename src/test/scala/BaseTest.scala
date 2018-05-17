import java.net.URLEncoder

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.FunSuite
import org.scalatest.selenium.WebBrowser

import scala.io.Source

trait BaseTest extends FunSuite with WebBrowser {
  System.setProperty("webdriver.chrome.driver", "chromedriver_mac")
  implicit val webDriver: WebDriver = new ChromeDriver()

  def readLocals = Source.fromFile("src/resources/local.txt").getLines().toList

  def getUriList = readLocals.map(line => s"https://m.search.naver.com/search.naver?query=${URLEncoder.encode(line , "UTF-8")}&sm=mtb_hty.top&where=m")
}