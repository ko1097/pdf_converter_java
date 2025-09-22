package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;


@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
         String pdfFileName = "dummy_pdf.pdf";
         File zipFile = new File("output_images.zip");
         File pdfFile = new File(pdfFileName);
          try (PDDocument document = PDDocument.load(pdfFile);
               FileOutputStream fos = new FileOutputStream(zipFile);
                 ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                // PDFTextStripper pdfStripper = new PDFTextStripper();
          
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                // String text = pdfStripper.getText(document);
                for(int page =0; page<document.getNumberOfPages(); page++){
                    BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    String fileName = "page_" + (page + 1) + ".png";
                    ImageIO.write(image, "png", baos);
                    baos.flush();

                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOut.putNextEntry(zipEntry);
                    zipOut.write(baos.toByteArray());
                    zipOut.closeEntry();
                    baos.close();
                }
                // System.out.println("PDF Content:\n" + text);
            } catch (IOException e) {
                System.err.println("Error reading PDF: " + e.getMessage());
            }
        SpringApplication.run(App.class, args);

       
        }
 

}
