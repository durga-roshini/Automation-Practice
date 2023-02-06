package testPackage;
import com.google.zxing.*;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Scan_QR_code{
    RemoteWebDriver wdriver;
    @Test
    public void QR_Scan_check(Method m) throws Exception {
        try {
            System.out.println("Chrome");
            WebDriverManager.chromedriver().setup();
            String workingDir = System.getProperty("user.dir");
            String downloadFilepath = workingDir + "\\DownloadedFiles\\";

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setCapability("download.default_directory",downloadFilepath);
            wdriver = new ChromeDriver();
            wdriver.get("https://en.wikipedia.org/wiki/QR_code");
            wdriver.manage().window().maximize();
            System.out.println("URL Entered");

            String  qrCodeUrl= wdriver.findElement(By.xpath("(//img[@class='thumbimage'])[1]")).getAttribute("src");
            System.out.println("QR URL: "+qrCodeUrl);

            URL urlOfImage= new URL(qrCodeUrl);
            BufferedImage bufferedImage= ImageIO.read(urlOfImage);

            LuminanceSource luminanceSource= new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binaryBitmap= new BinaryBitmap(new HybridBinarizer(luminanceSource));

            Result result = new MultiFormatReader().decode(binaryBitmap);
            String txtInQRCode = result.getText();
            System.out.println("Converted String: "+txtInQRCode);

            //to open the converted string in New tab
            JavascriptExecutor jse = (JavascriptExecutor) wdriver;
            jse.executeScript("window.open()");//Opens New Tab
            ArrayList<String> tabs = new ArrayList<String>(wdriver.getWindowHandles());
            wdriver.switchTo().window(tabs.get(1));// Navigating to New Tab
            wdriver.get(txtInQRCode);
            System.out.println("Converted String opened in new tab.");

            //Closing Browser
            wdriver.quit();
        } catch (Exception e) {
            System.out.println(e);
            wdriver.quit();
        }
    }

}
