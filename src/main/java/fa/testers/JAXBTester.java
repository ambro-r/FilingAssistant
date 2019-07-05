package fa.testers;

import fa.objects.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBTester {

    Logger LOG = LoggerFactory.getLogger(JAXBTester.class);

    public JAXBTester() { }

    public void loadXML() {
        try {
            File file = new File("job.xml");
            LOG.debug(file.toString());
            JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Job job = (Job) jaxbUnmarshaller.unmarshal(file);
            LOG.debug(job.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JAXBTester jaxbTester = new JAXBTester();
        jaxbTester.loadXML();
    }
}
