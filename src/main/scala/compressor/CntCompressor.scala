package compressor

import java.io._

class CntCompressor (input_file: String){
  
  def compressed = charCntCompressor(input_file)
  def compressedFile = input_file + ".cmps"
  
  def charCntCompressor(input_file: String): String = {
    //
    val input_string = scala.io.Source.fromFile(input_file).mkString.stripLineEnd
    
    //output string
    var output = ""
    
    //initial the count
    var cnt = 1
    var charPrev = input_string.charAt(0)
    var charCurrent = input_string.charAt(0)
    val n = input_string.length - 1
    
    for(i <- 1 to n){
      charCurrent = input_string.charAt(i)
      if (charCurrent == charPrev){
        cnt += 1
      }
      else{
        output += charPrev
        output += cnt
        charPrev = charCurrent
        cnt = 1
      }
      // last character
      if(i == n){
        output += charCurrent
        output += cnt
      }
    }
    
    return output
  }
  
  def writeCompressedfile(){
    val file = new File(compressedFile)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(compressed)
    bw.close()
  }
}