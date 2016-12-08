package me.chienlu.akkaaction.restapi.compress

import java.time.LocalDate

import java.io._

import java.nio.file.{Paths, Files}

import me.chienlu.akkaaction.util.exception.{MeConflictMessage, MeNotFoundMessage}

import compressor.CntCompressor

import scala.concurrent.{ExecutionContext, Future}

/**
 * CompressService
 * Created by Chien Lu.
 */
class CompressService() {
  def convert(compress: Compress)(implicit ec: ExecutionContext): Future[Compress] = Future {
    //Check if the file exists
    if (Files.exists(Paths.get(compress.inputFile)) == false)
      throw MeConflictMessage(s"${compress.inputFile} not exsit")

    val compressed = new CntCompressor(compress.inputFile)
    compressed.writeFile()

    compress
  }
}