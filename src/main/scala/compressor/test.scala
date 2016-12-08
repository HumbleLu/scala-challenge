package compressor

import compressor.CntCompressor

object test {
  def main(Args: Array[String]){
    val log_file = "/Users/chienlu/test.txt" 
    val compress = new CntCompressor(log_file)
    compress.writeFile()
    
    val decompressed_file = "/Users/chienlu/test.txt.cmps" 
    val decompress = new CntDecompressor(decompressed_file)
    decompress.writeFile()
  }
}