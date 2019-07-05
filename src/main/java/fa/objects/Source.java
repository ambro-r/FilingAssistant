package fa.objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Source {

    private String directory;
    private boolean recursive;

    public String getDirectory() {
        return directory;
    }

    @XmlAttribute
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isRecursive() {
        return recursive;
    }

    @XmlAttribute
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    @Override
    public String toString() {
        return new StringBuilder("Source [")
                .append("directory=").append(directory)
                .append(", recursive=").append(recursive)
                .append("]").toString();
    }
}
