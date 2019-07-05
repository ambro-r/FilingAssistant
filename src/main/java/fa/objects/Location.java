package fa.objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Location {

    private String directory;
    private String identifier;

    public String getDirectory() {
        return directory;
    }

    @XmlAttribute
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getIdentifier() {
        return identifier;
    }

    @XmlAttribute
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return new StringBuilder("Location [")
                .append("identifier=").append(identifier)
                .append(", directory=").append(directory)
                .append("]").toString();
    }
}
