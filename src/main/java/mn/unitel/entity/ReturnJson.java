package mn.unitel.entity;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jboss.logging.annotations.Fields;

import java.util.List;

@XmlRootElement(name = "ReturnJson")
public class ReturnJson {
    String sort;
    String sortColumn;
    List<Field> fields;

    @XmlAttribute
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @XmlAttribute
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    @XmlElementWrapper(name = "Fields")
    @XmlElement(name = "Field")
    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
