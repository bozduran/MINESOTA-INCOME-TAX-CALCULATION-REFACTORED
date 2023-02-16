package incometaxcalculator.data.io;

public class XMLLogWriter extends LogWriter {

  @Override
  public String createTag(String tagToFormat,String stringToSurround){
    return "<"+tagToFormat.replace(" ","")+"> " + stringToSurround + " </"+tagToFormat+">";
  }

  @Override
  public String fileType() {
    return "_LOG.xml";
  }

}
