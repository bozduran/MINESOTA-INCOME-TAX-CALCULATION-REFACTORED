package incometaxcalculator.data.io;

public class XMLInfoWriter extends InfoWriter {

  @Override
  public String createTag(String tagToFormat,String stringToSuround){
    return "<"+tagToFormat.replace(" ","")+"> " + stringToSuround + " </"+tagToFormat+">";
  }

  @Override
  public String createReceiptsHeader() {
    return "<"+receiptsTag+">";
  }

  @Override
  public String fileType() {
    return "_INFO.xml";
  }

}
