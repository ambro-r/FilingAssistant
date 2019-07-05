package fa.objects;

import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "job")
public class Job {

    private List<Location> locations;
    private List<Source> sources;
    private List<Document> documents;

    public List<Location> getLocations() {
        return locations;
    }

    @XmlElementWrapper(name="locations")
    @XmlElement(name = "location")
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    @XmlElementWrapper(name="documents")
    @XmlElement(name = "document")
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Source> getSources() {
        return sources;
    }

    @XmlElementWrapper(name="sources")
    @XmlElement(name = "source")
    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return new StringBuilder("Job [")
            .append("\n").append(sources.stream().map(Source::toString).collect(Collectors.joining(";")))
            .append("\n").append(locations.stream().map(Location::toString).collect(Collectors.joining(";")))
            .append("\n").append(documents.stream().map(Document::toString).collect(Collectors.joining(";")))
            .append("\n").append("]").toString();
    }
}
