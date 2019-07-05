package fa;

import fa.logic.AssistantManagement;
import fa.objects.Job;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilingAssistant {

  Logger LOG = LoggerFactory.getLogger(FilingAssistant.class);

  public FilingAssistant() {}

  public Job loadXML(String jobFile) {
    try {
      File file = new File(jobFile);
      LOG.debug(file.toString());
      JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      return (Job) jaxbUnmarshaller.unmarshal(file);
    } catch (JAXBException jaxbe) {
      LOG.error("JAXB Xml exception occurred: {}", jaxbe.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
    if(args.length == 0 || StringUtils.isEmpty(args[0])) {
      System.out.println("Please specify the job file in the program arguments.");
    } else {
      FilingAssistant filingAssistant = new FilingAssistant();
      Job job = filingAssistant.loadXML(args[0]);
      if (job != null) {
        AssistantManagement assistantManagement = new AssistantManagement();
        assistantManagement.workJob(job);
      }
    }
  }

}
