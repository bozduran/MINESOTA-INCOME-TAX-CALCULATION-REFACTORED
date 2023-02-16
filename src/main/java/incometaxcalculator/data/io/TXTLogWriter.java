package incometaxcalculator.data.io;

public class TXTLogWriter extends LogWriter {

  @Override
  public String createTag(String tagToFormat,String stringToSurround){
    return tagToFormat+": "+ stringToSurround;
  }

  @Override
  public String fileType() {
    return "_LOG.txt";
  }



}
