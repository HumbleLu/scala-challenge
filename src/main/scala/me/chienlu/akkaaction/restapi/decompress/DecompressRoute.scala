package me.chienlu.akkaaction.restapi.decompress

import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

/**
 * Decompress Route
 * Created by Chien Lu.
 */
object DecompressRoute {

  import akka.http.scaladsl.server.Directives._
  import me.chienlu.akkaaction.util.JsonSupport._

  def apply(props: DecompressContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    pathPrefix("decompress") {
      pathEnd {
        post {
          entity(as[Decompress]) { decompress =>
            onSuccess(props.decompressService.convert(decompress)) { result =>
              complete(StatusCodes.OK, result)
              complete("filename: " + decompress.inputFile + ".dcmps")
            }
          }
        }
      }
    }
}
