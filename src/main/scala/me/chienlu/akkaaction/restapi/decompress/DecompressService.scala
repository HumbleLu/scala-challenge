package me.chienlu.akkaaction.restapi.decompress

import java.time.LocalDate

import java.io._

import java.nio.file.{Paths, Files}

import me.chienlu.akkaaction.util.exception.{MeConflictMessage, MeNotFoundMessage}

import compressor.CntDecompressor

import scala.concurrent.{ExecutionContext, Future}

/**
 * DecompressService
 * Created by Chien Lu.
 */
class DecompressService() {
  def convert(decompress: Decompress)(implicit ec: ExecutionContext): Future[Decompress] = Future {
    //Check if the file exists
    if (Files.exists(Paths.get(decompress.inputFile)) == false)
      throw MeConflictMessage(s"${decompress.inputFile} not exsit")

    val decompressed = new CntDecompressor(decompress.inputFile)
    decompressed.writeFile()

    decompress
  }
}