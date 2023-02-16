package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class LogFileFactory {
    public FileWriter createLogWriter(String fileType) throws WrongFileFormatException {
        if (fileType.equals("txt")){
            return new TXTLogWriter();
        } else if (fileType.equals("xml")) {
            return new XMLLogWriter();
        }else{
            throw new WrongFileFormatException();

        }

    }
}
