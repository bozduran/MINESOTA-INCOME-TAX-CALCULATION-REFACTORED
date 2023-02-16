package incometaxcalculator.data.management;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  private final int[] incomeVariables ;
  private final double[] taxCalculateVariables;

  public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    incomeVariables =
            new int[] {30390, 90000, 122110, 203390};
    taxCalculateVariables =
            new double[] {0.0535 , 1625.87 ,0.0705 ,30390 ,
                    5828.38 , 0.0785 , 90000,
                    8092.13 ,0.0785 ,122110 ,
                    14472.61 , 0.0985 , 203390} ;

  }


  public double calculateBasicTax() {
    return calculateBasicTax(incomeVariables , taxCalculateVariables);
  }
}
