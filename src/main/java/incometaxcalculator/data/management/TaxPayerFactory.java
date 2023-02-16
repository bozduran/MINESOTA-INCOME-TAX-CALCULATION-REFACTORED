package incometaxcalculator.data.management;

import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TaxPayerFactory {
    public Taxpayer generateTaxPayer(String fullname, int taxRegistrationNumber, String status,
                                   float income)throws WrongTaxpayerStatusException{

        return switch (status) {
            case "Married Filing Jointly" -> new MarriedFilingJointlyTaxpayer(fullname, taxRegistrationNumber, income);
            case "Married Filing Separately" ->
                    new MarriedFilingSeparatelyTaxpayer(fullname, taxRegistrationNumber, income);
            case "Single" -> new SingleTaxpayer(fullname, taxRegistrationNumber, income);
            case "Head of Household" -> new HeadOfHouseholdTaxpayer(fullname, taxRegistrationNumber, income);
            default -> throw new WrongTaxpayerStatusException();
        };


    }
}
