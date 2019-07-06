package fa.testers;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITextTester {

  Logger LOG = LoggerFactory.getLogger(ITextTester.class);

  public ITextTester() {}

  public void loadPDF() {
      try {
        String file = "C:\\Test_Document.PDF";
        PdfReader reader = new PdfReader(file);
        int n = reader.getNumberOfPages();
        String str=PdfTextExtractor.getTextFromPage(reader, 1); //Extracting the content from a particular page.
        System.out.println(str);
        reader.close();
      } catch (Exception e) {
        System.out.println(e);
      }

  }


  public static void main(String[] args) {
    ITextTester iTextTester = new ITextTester();
    iTextTester.loadPDF();
  }

}
