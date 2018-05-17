import java.io.{BufferedWriter, File, FileWriter}

import org.json4s._
import org.json4s.jackson.Serialization

object Main {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  def main(args: Array[String]): Unit = {
    val range = Range(1, 7).toList

    val locals = Crawler.readLocals
    var count = -1
    val results = Crawler.getUriList.map(url => {
      count = count + 1
      Crawler.webDriver.navigate.to(url)
      range.map(index => {
        Crawler.parsing(index) ++ Map("local" -> locals(count))
      })
    })
    println(results)

    val jsonStr = Serialization.write(results)

    val file = new File("landmark_result.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(jsonStr)
    bw.close()
    Crawler.webDriver.quit()
  }
}
