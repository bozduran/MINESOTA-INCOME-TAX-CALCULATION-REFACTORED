package incometaxcalculator.data.management;

public class SingleTaxpayer extends Taxpayer {

  private final int[] incomeVariables ;
  private final double[] taxCalculateVariables;
  public SingleTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);
    incomeVariables =
            new int[] {24680, 81080, 90000, 152540};
    taxCalculateVariables =
            new double[] {0.0535 , 1320.38 ,0.0705 ,24680 ,
                    5296.58 , 0.0785 , 81080,
                    5996.80 ,0.0785 ,90000 ,
                    10906.19 , 0.0985 , 152540} ;
  }



  public double calculateBasicTax() {
    return super.calculateBasicTax(incomeVariables , taxCalculateVariables);
  }
}