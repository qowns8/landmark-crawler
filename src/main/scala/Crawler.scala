import java.net.URLEncoder
import java.time.Duration
import java.util.concurrent.TimeUnit

import org.openqa.selenium._
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}

import scala.io.Source

object Crawler {

  // 리눅스 크롬 - 셀레늄
  System.setProperty("webdriver.chrome.driver", "chromedriver_linux")
  implicit val webDriver: WebDriver = new ChromeDriver()
  // 스레드 timeout 설정
  val waitDriver = new WebDriverWait(webDriver, 30)
  webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)

  // 관광지 위치들
  def readLocals = Source.fromFile("src/resources/local.txt").getLines().toList

  /**
    * 관광지 이름을 url로 인코딩한 리스트
    * @return List[String]
    * @example {{{
    *           val urls = Crawler.getUriList
    *           urls.map(eachUrl => {
    *             // something work
    *           })
    * }}}
    */
  def getUriList = readLocals.map(line => s"https://m.search.naver.com/search.naver?query=${URLEncoder.encode(line , "UTF-8")}&sm=mtb_hty.top&where=m")

  /**
    * html에서 이미지, 랭킹, 태그, 이름을 Map[String, String]으로 가저오는 메소드
    * @param index 번호
    * @return Map[String, String]
    * @example {{{
    *     var count = 0
    *     urls.map(eachUrl => {
    *       // something do
    *       parsing(count)
    *     })
    * }}}
    */
  def parsing(index: Long) = {
    // 랭크, 태그, 이름
    val selector = s"#_sau_overseas_trip_answer > div.api_subject_bx > div.cs_attraction._lp_animation._attraction_list_root > div.attraction_list > div > div > div > ul > li:nth-child(${index})"
    val eachItem = By.cssSelector(selector)
    waitDriver.until(ExpectedConditions.presenceOfElementLocated(eachItem))
    val items = webDriver.findElement(eachItem).getText.split("\n")

    // 이미지
    val imgSelector = s"${selector} > a > span > img"
    val eachImg = By.cssSelector(imgSelector)
    waitDriver.until(ExpectedConditions.presenceOfElementLocated(eachImg))
    val img = webDriver.findElement(eachImg).getAttribute("src")

    Map (
      "rank" -> items(0),
      "name" -> items(1),
      "tag" -> items(2),
      "img" -> img
    )
  }
}
