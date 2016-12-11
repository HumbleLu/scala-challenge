import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import spray.json.DefaultJsonProtocol
import compressor.CntCompressor
import compressor.CntDecompressor

import scala.concurrent.{ExecutionContextExecutor, ExecutionContext, Future}
import scala.util.{Success => ScalaSuccess}
import scala.util.{Failure => ScalaFailure}

import java.io._
import java.nio.file.{Paths, Files}
import java.io.IOException

/**
Author: Chien Lu
*/

//define compress and decomrpess request
case class CompressReq(inputFile: String)
case class DecompressReq(inputFile: String)

trait Protocols extends DefaultJsonProtocol {
  implicit val compressFormat = jsonFormat1(CompressReq.apply)
  implicit val decompressFormat = jsonFormat1(DecompressReq.apply)
}

trait CompressService extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

  def convert(compressReq: CompressReq)(implicit ec: ExecutionContext): Future[CompressReq] = Future {
    val compressed = new CntCompressor(compressReq.inputFile)
    compressed.writeFile()
    compressReq
  }

  def deconvert(decompressReq: DecompressReq)(implicit ec: ExecutionContext): Future[DecompressReq] = Future {
    val decompressed = new CntDecompressor(decompressReq.inputFile)
    decompressed.writeFile()
    decompressReq
  }

  val routes = {
    logRequestResult("akka-http-microservice") {
      pathPrefix("compress") {
        post{
          entity(as[CompressReq]) { compressReq =>
            onComplete(convert(compressReq)) { 
              case ScalaSuccess(value) => complete(StatusCodes.OK + "\nfilename: " + new CntCompressor(compressReq.inputFile).compressedFile)
              case ScalaFailure(ex)    => complete((StatusCodes.InternalServerError + "\n" + ex.getMessage))
            }
          }
        }
      } ~
      pathPrefix("decompress") {
        post{
          entity(as[DecompressReq]) { decompressReq =>
            onComplete(deconvert(decompressReq)) { 
              case ScalaSuccess(value) => complete(StatusCodes.OK + "\nfilename: " + new CntDecompressor(decompressReq.inputFile).decompressedFile)
              case ScalaFailure(ex)    => complete((StatusCodes.InternalServerError + "\n" + ex.getMessage))
            }
          }
        }
      }
    }
  }
}

object AkkaHttpMicroservice extends App with CompressService{
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
