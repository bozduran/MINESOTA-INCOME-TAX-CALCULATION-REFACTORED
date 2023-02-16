package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class LogWriter implements FileWriter {



    public static Map<String, Short> receiptKindMap;
    static {
        receiptKindMap = new HashMap<>();
        receiptKindMap.put("Entertainment", (short) 0);
        receiptKindMap.put("Basic", (short)1);
        receiptKindMap.put("Travel", (short)2);
        receiptKindMap.put("Health", (short)3);
        receiptKindMap.put("Other", (short)4);
    }

    public abstract String fileType();

    public abstract String createTag(String tagToFormat,String stringToSurround);


    public void generateFile(int taxRegistrationNumber) throws IOException {
        TaxpayerManager manager = new TaxpayerManager();

        PrintWriter outputStream = new PrintWriter(
                new java.io.FileWriter("AFMs/"+taxRegistrationNumber + fileType()));//filetype <-fileType()

        outputStream.println(createTagString( "Name", manager.getTaxpayerName(taxRegistrationNumber) ));
        outputStream.println(createIntegerNumber("AFM", taxRegistrationNumber ) );
        outputStream.println(createTagString("Income", manager.getTaxpayerIncome(taxRegistrationNumber) ));
        outputStream.println(
                createDoubleNumber("BasicTax" , manager.getTaxpayerBasicTax(taxRegistrationNumber) ));
        if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
            outputStream.println( createDoubleNumber("TaxIncrease"
                    , manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) ));
        } else {
            outputStream.println( createDoubleNumber("TaxDecrease",
                     manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) ));
        }
        outputStream
                .println( createDoubleNumber("TotalTax" ,manager.getTaxpayerTotalTax(taxRegistrationNumber) ));
        outputStream.println( createIntegerNumber(
                "Receipts " , manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) ));
        /*
        outputStream.println( createFloatNumber(
                "Entertainment " , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT)));
        outputStream.println( createFloatNumber(
                "Basic" , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC) ));
        outputStream.println( createFloatNumber(
                "Travel" , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL) ));
        outputStream.println( createFloatNumber(
                "Health" , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH) ) );
        outputStream.println( createFloatNumber(
                "Other" , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER) ));
        */
        for (Map.Entry<String,Short> entry : receiptKindMap.entrySet()){
            outputStream.println( createFloatNumber(
                    entry.getKey() , manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, entry.getValue()) ));
        }
        outputStream.close();

    }


    public String createIntegerNumber(String tag,int number) {
        return createTag(tag, String.valueOf(number));
    }
    public String createFloatNumber(String tag,float number) {
        return createTag(tag, String.valueOf(number));
    }

    public String createDoubleNumber(String tag,double number) { return  createTag(tag,String.valueOf(number));}
    public String createTagString(String tag,String stringFotTag){
        return createTag(tag,stringFotTag);
    }

}