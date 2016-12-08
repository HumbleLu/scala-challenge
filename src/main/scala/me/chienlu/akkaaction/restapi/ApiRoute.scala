package me.chienlu.akkaaction.restapi

import akka.http.scaladsl.model.{HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler
import akka.stream.Materializer
import com.typesafe.scalalogging.StrictLogging
import me.chienlu.akkaaction.restapi.compress.CompressRoute
import me.chienlu.akkaaction.restapi.decompress.DecompressRoute
import me.chienlu.akkaaction.util.exception.MessageException
import org.json4s.JsonAST.{JInt, JObject, JString}

import scala.concurrent.ExecutionContextExecutor

/**
 * Api Route
 * Created by Chien Lu.
 */
object ApiRoute extends StrictLogging {
  val healthCheck =
    path("health_check") {
      get { ctx =>
        logger.debug(ctx.request.toString)
        ctx.complete(HttpEntity.Empty)
      }
    }

  import me.chienlu.akkaaction.util.JsonSupport._

  /**
   * 自定义异常处理
   */
  val customExceptionHandler = ExceptionHandler {
    case e: MessageException =>
      extractRequest { req =>
        val msg =
          s"""\nmethod: ${req.method}
             |uri: ${req.uri}
             |headers:
             |\t${req.headers.mkString("\n\t")}
             |$e""".stripMargin
        if (e.errcode > 500) logger.error(msg, e)
        else logger.warn(msg)

        complete(
          StatusCodes.getForKey(e.errcode) getOrElse StatusCodes.InternalServerError,
          JObject("errcode" -> JInt(e.errcode), "errmsg" -> JString(e.errmsg)))
      }
    case e: Exception =>
      extractRequest { req =>
        logger.error(req.toString, e)
        complete(
          StatusCodes.InternalServerError,
          JObject("errcode" -> JInt(500), "errmsg" -> JString(e.getLocalizedMessage)))
      }
  }

  def apply(props: ContextProps)(implicit ec: ExecutionContextExecutor, mat: Materializer) =
    handleExceptions(customExceptionHandler) {
      pathPrefix("api") {
        healthCheck ~
          CompressRoute(props) ~
          DecompressRoute(props)
      }
    }

}