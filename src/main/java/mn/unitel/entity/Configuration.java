package mn.unitel.entity;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "Configuration")
public class Configuration {

    public Configuration() {}

    List<Query> queries;

    @XmlElementWrapper(name = "Queries")
    @XmlElement(name = "Query")
    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
}
