package incometaxcalculator.data.io;

import java.io.File;
import java.io.IOException;

public class FileFactory {

    public void fileUpdate(int taxRegistrationNumber) throws IOException {

        if (new File("AFMs/"+taxRegistrationNumber + "_INFO.xml").exists()
                && new File(taxRegistrationNumber + "_INFO.txt").exists()) {
            new XMLInfoWriter().generateFile(taxRegistrationNumber);
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        } else if(new File("AFMs/"+taxRegistrationNumber + "_INFO.xml").exists() ) {
            new XMLInfoWriter().generateFile(taxRegistrationNumber);
        } else {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        }
    }


}
