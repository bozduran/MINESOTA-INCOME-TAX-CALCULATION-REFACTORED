package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;

import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public abstract class FileReader {

  protected abstract int checkForReceipt(BufferedReader inputStream)
      throws NumberFormatException, IOException;

  /**
   * 
   * @throws NumberFormatException 
   * @throws IOException xcvcxcvcxvxc
   * @throws WrongTaxpayerStatusException
   * @throws WrongFileFormatException
   * @throws WrongReceiptKindException
   * @throws WrongReceiptDateException
   */
  public void readFile(String fileName)
      throws NumberFormatException, IOException, WrongTaxpayerStatusException,
      WrongFileFormatException, WrongReceiptKindException, WrongReceiptDateException {

    BufferedReader inputStream = new BufferedReader(new java.io.FileReader(fileName));
    String fullname = getValueOfField(inputStream.readLine());
    int taxRegistrationNumber = Integer.parseInt(getValueOfField(inputStream.readLine()));
    String status = getValueOfField(inputStream.readLine());
    float income = Float.parseFloat(getValueOfField(inputStream.readLine()));

    createTaxpayer(fullname,
            taxRegistrationNumber,
            income,
            status);
    while (readReceipt(inputStream, taxRegistrationNumber)) ;
  }

  protected boolean readReceipt(BufferedReader inputStream, int taxRegistrationNumber)
      throws WrongFileFormatException, IOException, WrongReceiptKindException,
      WrongReceiptDateException {

    int receiptId;
    if ((receiptId = checkForReceipt(inputStream)) < 0) {
      return false;
    }
    String issueDate = getValueOfField(inputStream.readLine());
    String kind = getValueOfField(inputStream.readLine());
    float amount = Float.parseFloat(getValueOfField(inputStream.readLine()));

    Company company = new Company( getValueOfField(inputStream.readLine() ) ,
            getValueOfField(inputStream.readLine() ) ,
            getValueOfField(inputStream.readLine() ) ,
            getValueOfField(inputStream.readLine()) ,
            Integer.parseInt(getValueOfField(inputStream.readLine())) );

    createReceipt(receiptId,
            issueDate,
            amount,
            kind,
            company,
        taxRegistrationNumber);
    return true;
  }

  protected void createTaxpayer(String fullname, int taxRegistrationNumber, float income,
      String status) throws WrongTaxpayerStatusException {

    TaxpayerManager manager = new TaxpayerManager();
    manager.createTaxpayer(fullname, taxRegistrationNumber, status, income);
  }

  protected void createReceipt(int receiptId, String issueDate, float amount, String kind,
      Company company,
      int taxRegistrationNumber) throws WrongReceiptKindException, WrongReceiptDateException {

    TaxpayerManager manager = new TaxpayerManager();
    manager.createReceipt(receiptId, issueDate, amount, kind, company , taxRegistrationNumber);
  }

  protected boolean isEmpty(String line) {
      return line == null;
  }

  protected String getValueOfField(String fieldsLine) throws WrongFileFormatException {
    if (isEmpty(fieldsLine)) {
      throw new WrongFileFormatException();
    }
    try {
      if (this.getClass().equals(XMLFileReader.class)){
        String[] valueWithTail = fieldsLine.split(" ", 2);
        String[] valueReversed = new StringBuilder(valueWithTail[1]).reverse().toString().trim()
                .split(" ", 2);
        return new StringBuilder(valueReversed[1]).reverse().toString();
      } else if (this.getClass().equals(TXTFileReader.class)) {
        String[] values = fieldsLine.split(" ", 2);
        values[1] = values[1].trim();
        return values[1];
      }

    } catch (NullPointerException e) {
      throw new WrongFileFormatException();
    }
    return null;
  }
}