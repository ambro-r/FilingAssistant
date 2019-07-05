package fa.logic;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import fa.objects.Source;
import fa.objects.Job;
import fa.objects.Document;
import fa.objects.Location;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssistantManagement {

  Logger LOG = LoggerFactory.getLogger(AssistantManagement.class);

  public void workJob(Job job) {
    LOG.info("{} sources loaded.", job.getSources().size());
    LOG.info("{} locations loaded.", job.getLocations().size());
    LOG.info("{} keepers loaded.", job.getDocuments().size());
    for(Document document : job.getDocuments()) {
      scrubTarget(document, job.getLocations());
    }
    for(Source source : job.getSources()) {
      int filed = parseDirectory(source, job.getDocuments());
      LOG.info("{} files filed!", String.valueOf(filed));
    }
  }

  private boolean file(File source, File destination, String action) {
    boolean filed = Boolean.FALSE;
    try {
      LOG.trace("   ... performing action={} on file={}", action, source.getName());
      if ("move".equalsIgnoreCase(action)) {
        FileUtils.moveFile(source, destination);
        LOG.info("... {} moved to {}.", source.getName(), destination);
        filed = Boolean.TRUE;
      } else if ("copy".equalsIgnoreCase(action)) {
        if (destination.lastModified() < source.lastModified()) {
          FileUtils.copyFile(source, destination);
          LOG.info("... {} copied.", source.getName());
          filed = Boolean.TRUE;
        } else {
          LOG.warn("... {} destination file is newer or the same as source file.", source.getName());
        }
      } else {
        LOG.warn("action={} is unsupported.", action);
      }
    } catch (FileExistsException fex) {
      LOG.warn("Source={} already exists at destination={}", source.getName(), destination.getAbsolutePath());
    } catch (IOException ioex) {
      LOG.error("Error occurred :", ioex);
    }
    return filed;
  }

  private File getDestinationFile(File sourceFile, Document document) {
    LOG.trace("   ... generating destination file");
    String prefix = "";
    if (!StringUtils.isEmpty(document.getDateprefix())) {
      SimpleDateFormat sdf = new SimpleDateFormat(document.getDateprefix());
      prefix = sdf.format(sourceFile.lastModified()) + " ";
    }
    File destination = new File(document.getTarget() + getGroupBy(document.getGroupby()) + System.getProperty("file.separator") + prefix + sourceFile.getName());
    LOG.trace("   ... destination file={}", destination.getName());
    return destination;
  }

  private String getGroupBy(String groupBy) {
    if ("YEAR".equalsIgnoreCase(groupBy)) {
      Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
      String year = Integer.toString(calendar.get(Calendar.YEAR));
      LOG.trace("   ... grouping by year, current year={}", year);
      return System.getProperty("file.separator") + year;
    }
    return "";
  }

  private int parseDirectory(Source source, List<Document> documents) {
    int fileCounter = 0;
    LOG.info("Parsing directory={}", source.getDirectory());
    File directory = new File(source.getDirectory());
    if(directory.exists() && directory.isDirectory()) {
      for(Document document : documents) {
        FileFilter fileFilter = new RegexFileFilter(document.getPattern());
        File[] files = directory.listFiles(fileFilter);
        LOG.debug("... {} files found matching pattern={}.", files.length, document.getPattern());
        for (int i = 0; i < files.length; i++) {
          LOG.debug("... processing file={}", files[i].getName());
          File sourceFile = new File(source.getDirectory() + System.getProperty("file.separator") + files[i].getName());
          File destinationFile = null;
          if(files[i].getName().toLowerCase().endsWith(".pdf") && !StringUtils.isEmpty(document.getContains())) {
             if(checkPDF(sourceFile, document.getContains())) {
               destinationFile = getDestinationFile(sourceFile, document);
             }
          } else {
            destinationFile = getDestinationFile(sourceFile, document);
          }
          if(destinationFile != null) {
           if(file(sourceFile, destinationFile, document.getAction())) {
             fileCounter ++;
           }
          }
          LOG.trace("   ... processing done");
        }
      }
      File[] directories = directory.listFiles(File::isDirectory);
      LOG.debug("... {} sub directories found in current directory, recursive={}.", directories.length, source.isRecursive());
      if(source.isRecursive()) {
        for (int i = 0; i < directories.length; i++) {
          Source recursiveSource = new Source();
          recursiveSource.setRecursive(Boolean.TRUE);
          recursiveSource.setDirectory(directories[i].getAbsolutePath());
          fileCounter = fileCounter + parseDirectory(recursiveSource, documents);
        }
      }
    } else  {
      LOG.debug("... directory does not exist.");
    }
    return fileCounter;
  }

  private void scrubTarget(Document document, List<Location> location) {
    for(Location loc : location) {
      String identifier = "$" + loc.getIdentifier().toUpperCase() + "$";
      if(document.getTarget().contains(identifier)) {
        document.setTarget(document.getTarget().replace(identifier, loc.getDirectory()));
        LOG.debug("{} updated.", document.toString());
        break;
      }
    }
  }

  private boolean checkPDF(File sourceFile, String contains) {
    boolean success = Boolean.FALSE;
    LOG.trace("   ... is PDF with defined contains=\"{}\"", contains);
    try {
      PdfReader reader = new PdfReader(sourceFile.getAbsolutePath());
      LOG.trace("   ... {} pages to read in PDF", reader.getNumberOfPages());
      for (int i = 0; i < reader.getNumberOfPages(); i++) {
        String pdfText = PdfTextExtractor.getTextFromPage(reader, i+1);
        if (pdfText.contains(contains)) {
          LOG.trace("   ... match found in PDF");
          success = Boolean.TRUE;
          break;
        }
      }
      reader.close();
    } catch (Exception e) {
      LOG.error("Exception occurred on checking PDF: ", e);
    }
    return success;
  }


}
