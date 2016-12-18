package compressor

import java.io._

class CntDecompressor(input_file: String){

  def decompressed = charCntDecompressor(input_file)
  def decompressedFile = input_file + ".dcmps"
  
  def charCntDecompressor(input_file: String): String = {
    //read string
    val input_string = scala.io.Source.fromFile(input_file).mkString.stripLineEnd
        
    //check format
    val m = input_string matches "([a-z0-9A-z][0-9]-)+([a-z0-9A-z][0-9])"
    if (m == false){
      throw new Exception("Format not match! Empty file:" + decompressedFile + "generated.")
    }
    
    def restore(s: String): String = {
      val charCurrent = s.charAt(0)
      val cnt = s.takeRight(s.length - 1).toString.toInt
      return charCurrent.toString * cnt
    }
    
    //output string
    var output = ""
    for(s<- input_string.split("-")){
      output += restore(s)
    }
    return output
  }
  
  def writeFile(){
    val file = new File(decompressedFile)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(decompressed)
    bw.close()
  }
}