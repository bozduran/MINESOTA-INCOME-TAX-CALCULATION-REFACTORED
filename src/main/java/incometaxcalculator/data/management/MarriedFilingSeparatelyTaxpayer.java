package incometaxcalculator.data.management;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

  private final int[] incomeVariables ;
  private final double[] taxCalculateVariables;
  public MarriedFilingSeparatelyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    incomeVariables =
            new int[] {18040, 71680, 90000, 127120};
    taxCalculateVariables =
            new double[] {0.0535 , 965.14 ,0.0705 ,18040 ,
                    4746.76 , 0.0785 , 71680,
                    6184.88 ,0.0785 ,90000 ,
                    9098.80 , 0.0985 , 127120} ;
  }

  public double calculateBasicTax() {
    return calculateBasicTax(incomeVariables , taxCalculateVariables);
  }

}