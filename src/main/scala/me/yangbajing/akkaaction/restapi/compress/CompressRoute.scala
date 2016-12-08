package me.yangbajing.akkaaction.restapi.compress

import akka.http.scaladsl.model.StatusCodes
import akka.stream.Materializer

import scala.concurrent.ExecutionContextExecutor

/**
 * Compress Route
 * Created by Chien Lu.
 */
object CompressRoute {

  import akka.http.scaladsl.server.Directives._
  import me.yangbajing.akkaaction.util.JsonSupport._

  def apply(props: CompressContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    pathPrefix("compress") {
      pathEnd {
        post {
          entity(as[Compress]) { compress =>
            onSuccess(props.compressService.convert(compress)) { result =>
              complete(StatusCodes.OK, result)
              complete("filename: " + compress.inputFile + ".cmps")
            }
          }
        }
      }
    }
}
