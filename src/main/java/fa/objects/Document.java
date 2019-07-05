package fa.objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Document {

    private String pattern;
    private String target;
    private String action;
    private String groupby;
    private String dateprefix;
    private String contains;

    public String getDateprefix() {
        return dateprefix;
    }

    @XmlAttribute(name = "dateprefix")
    public void setDateprefix(String dateprefix) {
        this.dateprefix = dateprefix;
    }

    public String getPattern() {
        return pattern;
    }

    @XmlAttribute
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTarget() {
        return target;
    }

    @XmlAttribute
    public void setTarget(String target) {
        this.target = target;
    }

    public String getAction() {
        return action;
    }

    @XmlAttribute
    public void setAction(String action) {
        this.action = action;
    }

    public String getGroupby() {
        return groupby;
    }

    @XmlAttribute
    public void setGroupby(String groupby) {
        this.groupby = groupby;
    }

    public String getContains() {
        return contains;
    }

    @XmlAttribute(name = "contains")
    public void setContains(String contains) {
        this.contains = contains;
    }


    @Override
    public String toString() {
        return new StringBuilder("FilingAssistant [")
            .append("pattern=").append(pattern)
            .append(", target=").append(target)
            .append(", action=").append(action)
            .append(", groupby=").append(groupby)
            .append(", dateprefix=").append(dateprefix)
            .append(", contains=").append(contains)
            .append("]").toString();
    }
}
