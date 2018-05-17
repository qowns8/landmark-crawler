class CrawlerTest extends BaseTest {

  test("크롤링 테스트") {
    val urls = getUriList
    urls.map( url => {
      go to (url)
      val a = pageSource
      println("\n\n\n\n\n"+pageSource)
    })
  }

}
