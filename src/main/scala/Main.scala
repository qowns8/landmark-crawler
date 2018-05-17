import java.io.{BufferedWriter, File, FileWriter}

import org.json4s._
import org.json4s.jackson.Serialization

object Main {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  def main(args: Array[String]): Unit = {
    val range = Range(1, 7).toList // 각 관광지당 상위 7개만 뽑음
    val locals = Crawler.readLocals // 관광지 이름들
    var count = -1 // 관광지 카운터
    val results = Crawler.getUriList.map(url => {
      count = count + 1
      Crawler.webDriver.navigate.to(url) // 페이지 이동
      range.map(index => { // 1에서 7까지
        Crawler.parsing(index) ++ Map("local" -> locals(count)) // 파싱데이터 + 지역이름
      })
    })
    println(results) // log

    val jsonStr = Serialization.write(results)

    //파일 입출력
    val file = new File("landmark_result.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(jsonStr)
    bw.close()
    Crawler.webDriver.quit()
  }
}
