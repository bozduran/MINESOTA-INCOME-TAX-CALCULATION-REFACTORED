package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class InfoWriter implements FileWriter{

    protected static String nameTag = "Name";
    protected static String afmTag = "AFM";
    protected static String statusTag = "Status";
    protected static String incomeTag = "Income";
    protected static String receiptsTag ="Receipts";
    protected static String receiptIDTag="Receipt ID";
    protected static String dateTag="Date";
    protected static String kindTag="Kind";
    protected static String amountTag="Amount";
    protected static String companyTag="Company";
    protected static String countryTag="Country";
    protected static String cityTag="City";
    protected static String streetTag="Street";
    protected static String numberTag ="Number";


    public void generateFile(int taxRegistrationNumber) throws IOException {
        TaxpayerManager manager = new TaxpayerManager();
        PrintWriter outputStream = new PrintWriter(
                new java.io.FileWriter("AFMs/"+taxRegistrationNumber + fileType()));

        outputStream.println( createTagString( nameTag,manager.getTaxpayerName(taxRegistrationNumber) )  );
        outputStream.println( createIntegerNumber( afmTag,taxRegistrationNumber) );
        outputStream.println( createTagString( statusTag,manager.getTaxpayerStatus(taxRegistrationNumber) ) );
        outputStream.println( createTagString(incomeTag, manager.getTaxpayerIncome(taxRegistrationNumber)) );
        outputStream.println();
        outputStream.println( createReceiptsHeader() );
        outputStream.println();

        generateTaxpayerReceipts(taxRegistrationNumber,outputStream,manager);
        outputStream.close();

    }

    public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream,
                                         TaxpayerManager manager){
        Map<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
        Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            HashMap.Entry<Integer, Receipt> entry = iterator.next();
            Receipt receipt = entry.getValue();
            outputStream.println( createIntegerNumber( receiptIDTag,receipt.getId( ) ) );
            outputStream.println( createTagString(dateTag,receipt.getIssueDate()));
            outputStream.println( createTagString( kindTag,receipt.getKind() ) );
            outputStream.println( createFloatNumber( amountTag,receipt.getAmount() ));
            outputStream.println( createTagString( companyTag,receipt.getCompany().getName()) );
            outputStream.println( createTagString(countryTag,receipt.getCompany().getAddress().getCountry())  );
            outputStream.println( createTagString(cityTag,receipt.getCompany().getAddress().getCity()) );
            outputStream.println( createTagString(streetTag,receipt.getCompany().getAddress().getStreet()) );
            outputStream.println( createIntegerNumber(numberTag,receipt.getCompany().getAddress().getNumber()) );
            outputStream.println();


        }
    }

    public abstract String fileType();

    public abstract String createTag(String tagToFormat,String stringToSuround);

    public abstract String createReceiptsHeader();

    public String createTagString(String tag,String stringFotTag){
        return createTag(tag,stringFotTag);
    }

    public String createIntegerNumber(String tag,int number) {
        return createTag(tag, String.valueOf(number));
    }

    public String createFloatNumber(String tag,float number) {
        return createTag(tag, String.valueOf(number));
    }

}
