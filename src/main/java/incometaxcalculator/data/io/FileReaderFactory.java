package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileEndingException;

public class FileReaderFactory {
    public FileReader fileReaderCreate(String fileType) throws WrongFileEndingException {

        if (fileType.equals("txt")) {
             return  new TXTFileReader();
        } else if (fileType.equals("xml")) {
             return new XMLFileReader();
        } else {
            throw new WrongFileEndingException();
        }
    }
}
