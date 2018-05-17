import java.net.URLEncoder
import java.time.Duration
import java.util.concurrent.TimeUnit

import org.openqa.selenium._
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

import scala.io.Source

object Crawler {

  System.setProperty("webdriver.chrome.driver", "chromedriver_mac")
  implicit val webDriver: WebDriver = new ChromeDriver()
  val waitDriver = new WebDriverWait(webDriver, 30)

  webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

  def readLocals = Source.fromFile("src/resources/local.txt").getLines().toList

  def getUriList = readLocals.map(line => s"https://m.search.naver.com/search.naver?query=${URLEncoder.encode(line , "UTF-8")}&sm=mtb_hty.top&where=m")

  def parsing(index: Long) = {
    val selector = s"#_sau_overseas_trip_answer > div.api_subject_bx > div.cs_attraction._lp_animation._attraction_list_root > div.attraction_list > div > div > div > ul > li:nth-child(${index})"
    val eachItem = By.cssSelector(selector)
    waitDriver.until(ExpectedConditions.presenceOfElementLocated(eachItem))
    val items = webDriver.findElement(eachItem).getText
    val itemList = items.split("\n")

    val imgSelector = s"${selector} > a > span > img"
    val eachImg = By.cssSelector(imgSelector)
    waitDriver.until(ExpectedConditions.presenceOfElementLocated(eachImg))
    val img = webDriver.findElement(eachImg).getAttribute("src")

    Map (
      "rank" -> itemList(0),
      "name" -> itemList(1),
      "tag" -> itemList(2),
      "img" -> img
    )
  }
}
