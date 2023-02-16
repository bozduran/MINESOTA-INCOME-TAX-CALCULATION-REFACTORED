package incometaxcalculator.data.management;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import incometaxcalculator.data.io.*;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxpayerManager {
  private final TaxPayerFactory taxPayerFactory = new TaxPayerFactory();
  private final FileFactory fileFactory = new FileFactory();
  private final LogFileFactory logFileFactory = new LogFileFactory();
  FileReaderFactory fileReaderFactory = new FileReaderFactory();


  private static final HashMap<Integer, Taxpayer> taxpayerHashMap = new HashMap<Integer, Taxpayer>(0);
  private static final HashMap<Integer, Integer> receiptOwnerTRN = new HashMap<Integer, Integer>(0);

  public void createTaxpayer(String fullname, int taxRegistrationNumber, String status,
      float income) throws WrongTaxpayerStatusException {
    taxpayerHashMap.put(taxRegistrationNumber,taxPayerFactory.
            generateTaxPayer(fullname,taxRegistrationNumber,status,income));

  }

  public void createReceipt(int receiptId, String issueDate, float amount, String kind,
      Company company,
      int taxRegistrationNumber) throws WrongReceiptKindException, WrongReceiptDateException {

    Receipt receipt = new Receipt(receiptId, issueDate, amount, kind,
        company);
    taxpayerHashMap.get(taxRegistrationNumber).addReceipt(receipt);
    receiptOwnerTRN.put(receiptId, taxRegistrationNumber);
  }

  public void removeTaxpayer(int taxRegistrationNumber) {
    Taxpayer taxpayer = taxpayerHashMap.get(taxRegistrationNumber);
    taxpayerHashMap.remove(taxRegistrationNumber);
    Map<Integer, Receipt> receiptsHashMap = taxpayer.getReceiptHashMap();
    for (HashMap.Entry<Integer, Receipt> entry : receiptsHashMap.entrySet()) {
      receiptOwnerTRN.remove(entry.getValue().getId());
    }
  }

  public void addReceipt(int receiptId, String issueDate, float amount, String kind,
      Company company,
      int taxRegistrationNumber) throws IOException, WrongReceiptKindException,
      WrongReceiptDateException, ReceiptAlreadyExistsException {

    if (containsReceipt(receiptId)) {
      throw new ReceiptAlreadyExistsException();
    }
    createReceipt(receiptId, issueDate, amount, kind, company,
        taxRegistrationNumber);
    updateFiles(taxRegistrationNumber);
  }

  public void removeReceipt(int receiptId) throws IOException, WrongReceiptKindException {
    taxpayerHashMap.get(receiptOwnerTRN.get(receiptId)).removeReceipt(receiptId);
    updateFiles(receiptOwnerTRN.get(receiptId));
    receiptOwnerTRN.remove(receiptId);
  }

  private void updateFiles(int taxRegistrationNumber) throws IOException {
    fileFactory.fileUpdate(taxRegistrationNumber);
  }

  public void saveLogFile(int taxRegistrationNumber, String fileFormat)
      throws IOException, WrongFileFormatException {
    FileWriter writer = logFileFactory.createLogWriter(fileFormat);
    writer.generateFile(taxRegistrationNumber);
  }

  public boolean containsTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.containsKey(taxRegistrationNumber);
  }

  public boolean containsTaxpayer() {
    return !taxpayerHashMap.isEmpty();
  }

  public boolean containsReceipt(int id) {
    return receiptOwnerTRN.containsKey(id);

  }

  public Taxpayer getTaxpayer(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber);
  }

  public void loadTaxpayer(String fileName)
      throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
      WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    String[] ending = fileName.split("\\.");
    System.out.println(ending[1]);
    FileReader reader = fileReaderFactory.fileReaderCreate(ending[1]);
    reader.readFile(fileName);


  }

  public String getTaxpayerName(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getFullname();
  }

  public String getTaxpayerStatus(int taxRegistrationNumber) {
    if (taxpayerHashMap.get(taxRegistrationNumber) instanceof MarriedFilingJointlyTaxpayer) {
      return "Married Filing Jointly";
    } else if (taxpayerHashMap
        .get(taxRegistrationNumber) instanceof MarriedFilingSeparatelyTaxpayer) {
      return "Married Filing Separately";
    } else if (taxpayerHashMap.get(taxRegistrationNumber) instanceof SingleTaxpayer) {
      return "Single";
    } else {
      return "Head of Household";
    }
  }

  public String getTaxpayerIncome(int taxRegistrationNumber) {
    return "" + taxpayerHashMap.get(taxRegistrationNumber).getIncome();
  }

  public double getTaxpayerVariationTaxOnReceipts(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getVariationTaxOnReceipts();
  }

  public int getTaxpayerTotalReceiptsGathered(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalReceiptsGathered();
  }

  public float getTaxpayerAmountOfReceiptKind(int taxRegistrationNumber, short kind) {
    return taxpayerHashMap.get(taxRegistrationNumber).getAmountOfReceiptKind(kind);
  }

  public double getTaxpayerTotalTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getTotalTax();
  }

  public double getTaxpayerBasicTax(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getBasicTax();
  }

  public Map<Integer, Receipt> getReceiptHashMap(int taxRegistrationNumber) {
    return taxpayerHashMap.get(taxRegistrationNumber).getReceiptHashMap();
  }

}