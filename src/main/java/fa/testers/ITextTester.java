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
        String file = "C:\\Users\\Roberto\\Downloads\\AMB0011 - IR-687566.PDF";
        //File file = new File("C:\\My Stuff\\My General Share\\To Do\\SARS\\2016-03-01 till 2017-02-28\\To Process\\LEVY\\TS_B_ANNC_02_3_0_062016.pdf");
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
