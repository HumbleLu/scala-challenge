package compressor

import java.io._

class CntDecompressor(input_file: String){

  def decompressed = charCntDecompressor(input_file)
  def decompressedFile = input_file + ".dcmps"
  
  def charCntDecompressor(input_file: String): String = {
    //read string
    val input_string = scala.io.Source.fromFile(input_file).mkString.stripLineEnd
        
    //initial the count
    var charCurrent = input_string.charAt(0)
    var cnt = 0
    val n = input_string.length - 1

    //check format
    val m = input_string matches "([a-z0-9A-z][0-9])+"
    if (m == false){
      throw new Exception("Format not match! Empty file:" + decompressedFile + " generated.")
    }
    
    //output string
    var output = ""
    
    for(i <- 0 to n by 2){
      charCurrent = input_string.charAt(i)
      cnt = input_string.charAt(i + 1).toString.toInt
            
      output += charCurrent.toString * cnt
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