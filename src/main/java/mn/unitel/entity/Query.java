package mn.unitel.entity;

import jakarta.ws.rs.Path;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Query")
public class Query {
    String id;
    String smsDirection;
    String service;
    ReturnJson returnJson;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getSmsDirection() {
        return smsDirection;
    }

    public void setSmsDirection(String smsDirection) {
        this.smsDirection = smsDirection;
    }
    @XmlAttribute
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @XmlElement(name = "ReturnJson")
    public ReturnJson getReturnJson() {
        return returnJson;
    }

    public void setReturnJson(ReturnJson returnJson) {
        this.returnJson = returnJson;
    }
}
