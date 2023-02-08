package testPackage;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generate_QR_Code {
    //static function that creates QR Code
    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws IOException, WriterException {
        //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
    //user-defined method that reads the QR code
    public static String readQRcode(String path, String charset, Map map) throws FileNotFoundException, IOException, NotFoundException
    {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
    //to create new directory with file
    public static Path createFileWithDir(String directory, String filename) {
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();
        return Paths.get(directory + File.separatorChar + filename);
    }
    public static void main(String args[]) throws WriterException, IOException, NotFoundException
    {
        //data that we want to store in the QR code
        ArrayList<String> URL = new ArrayList<>(List.of("https://durgafirst.com", "https://durgasecond.com","https://durgathird.com"));
        //path where we want to get QR Code
        String path = "C:\\Users\\durga roshini\\Documents\\Practice Project\\Practice Project\\QR Images";
        //Encoding charset to be used
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        //generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        //invoking the user-defined method that creates the QR code
        ArrayList<String> fileName = new ArrayList<String>();
        for(int i=0; i< URL.size();i++) {
            fileName.add( "Image " + (i + 1) + " " + RandomStringUtils.randomAlphabetic(3) + ".png");
            System.out.println(String.valueOf(createFileWithDir(path,  fileName.get(i))));
            generateQRcode(URL.get(i), String.valueOf(createFileWithDir(path,  fileName.get(i))), charset, hashMap, 200, 200);//increase or decrease height and width accodingly
        }

        //prints if the QR codes are generated
        System.out.println("QR Codes created successfully.");

        //Creating a File object for directory
        File directoryPath = new File("C:\\Users\\durga roshini\\Documents\\Practice Project\\Practice Project\\QR Images");
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        int j=0;
        while (j <= 2){
            for(File file : filesList){
                String s1 = path+"\\"+file.getName();
                String s2= path+"\\"+fileName.get(j);
                if (s1.equals(s2) && j< URL.size()){
                    System.out.println("Data stored in the QR Code is: \n"+ readQRcode(path+"\\"+file.getName(), charset, hashMap));
                    j=j+1;
                }
            }
        }
    }
}
