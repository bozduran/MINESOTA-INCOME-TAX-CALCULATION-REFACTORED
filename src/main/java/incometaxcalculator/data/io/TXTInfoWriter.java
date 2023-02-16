package incometaxcalculator.data.io;

public class TXTInfoWriter extends InfoWriter {

  @Override
  public String createTag(String tagToFormat,String stringToSuround){
    return tagToFormat+": "+stringToSuround;
  }

  @Override
  public String createReceiptsHeader() {
    return createTag("Receipts","");
  }

  @Override
  public String fileType() {
    return "_INFO.txt";
  }


}