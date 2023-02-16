package incometaxcalculator.data.management;

import java.util.HashMap;
import java.util.Map;

import incometaxcalculator.exceptions.WrongReceiptKindException;

public abstract class Taxpayer {

  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  private final float[] amountPerReceiptsKind = new float[5];
  private int totalReceiptsGathered = 0;
  private Map<Integer, Receipt> receiptHashMap;

  public static Map<String, Integer> receiptKindMap;
  static {
    receiptKindMap = new HashMap<>();
    receiptKindMap.put("Entertainment",0);
    receiptKindMap.put("Basic", 1);
    receiptKindMap.put("Travel", 2);
    receiptKindMap.put("Health", 3);
    receiptKindMap.put("Other", 4);

  }
  public abstract double calculateBasicTax();

  protected Taxpayer(String fullname, int taxRegistrationNumber, float income) {
    this.receiptHashMap = new HashMap<Integer, Receipt>(0);
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
  }

  public double calculateBasicTax(int[] incomeVariables,double[] taxCalculateVariables){

      if (income < incomeVariables[0]) {
          return taxCalculateVariables[0] * income;
      } else if (income < incomeVariables[1]) {
          return taxCalculateVariables[1] + taxCalculateVariables[2] * (income - taxCalculateVariables[3]);
      } else if (income < incomeVariables[2]) {
          return taxCalculateVariables[4] + taxCalculateVariables[5] * (income - taxCalculateVariables[6]);
      } else if (income < incomeVariables[3]) {
          return taxCalculateVariables[7]+ taxCalculateVariables[8] * (income - taxCalculateVariables[9]);
      } else {
          return taxCalculateVariables[10] + taxCalculateVariables[11] * (income - taxCalculateVariables[12]);
      }
  }
  public boolean checkIfReceiptKindValid(int receiptKindCode){
    if (receiptKindCode >= 0 && receiptKindCode <= amountPerReceiptsKind.length){
      return true;
    }
    return false;
  }

  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
    int receiptKindCode = receiptKindMap.get(receipt.getKind());
    if (checkIfReceiptKindValid(receiptKindCode)){
      amountPerReceiptsKind[receiptKindCode]  += receipt.getAmount();
    }else{
      throw new WrongReceiptKindException();
    }
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }

  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
    Receipt receipt = receiptHashMap.get(receiptId);

    int kindCode = receiptKindMap.get(receipt.getKind());
    if (kindCode >= 0 && kindCode <= 4){
      amountPerReceiptsKind[kindCode] -= receipt.getAmount();
    }else{
      throw new WrongReceiptKindException();
    }

    totalReceiptsGathered--;
    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public Map<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
    float totalAmountOfReceipts = getTotalAmountOfReceipts();
    if (totalAmountOfReceipts < 0.2 * income) {
      return calculateBasicTax() * 0.08;
    } else if (totalAmountOfReceipts < 0.4 * income) {
      return calculateBasicTax() * 0.04;
    } else if (totalAmountOfReceipts < 0.6 * income) {
      return -calculateBasicTax() * 0.15;
    } else {
      return -calculateBasicTax() * 0.3;
    }
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }

}