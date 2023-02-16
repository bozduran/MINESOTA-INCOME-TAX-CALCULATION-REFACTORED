package incometaxcalculator.data.management;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {
  private final int[] incomeVariables ;
  private final double[] taxCalculateVariables;
  public MarriedFilingJointlyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    incomeVariables =
            new int[] {36080, 90000, 143350, 254240};
    taxCalculateVariables =
            new double[] {0.0535 , 1930.28 ,0.0705 ,36080 ,
                    5731.64 , 0.0785 , 90000,
                    9492.82 ,0.0785 ,143350 ,
                    18197.69 , 0.0985 , 254240} ;
  }


  public double calculateBasicTax() {
    return super.calculateBasicTax(incomeVariables , taxCalculateVariables);
  }
}