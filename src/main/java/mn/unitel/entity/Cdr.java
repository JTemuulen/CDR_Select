package mn.unitel.entity;


public class Cdr {

    String cdrDate;
    String cdrType;
//   6-sms; 0-call; 100-call divert(forward); 1-system call; incoming-sms;
    String recordType;
    String startDateTime;
    String recOpeningTime;
    String endDateTime;
    String recClosureTime;
    String duration;
    String dialledPartyAddr;
    String ringingDuration;
    String accountingRecordType;
    String serviceType;
    String imsi;
    String imei;
    String callingNumber;
    String translatedNumber;
    String connectedNum;
    String recordingEntity;
    String location;
    String cellId;
    String basicService;
    String answerTime;

    public String getCdrDate() {
        return cdrDate;
    }

    public void setCdrDate(String cdrDate) {
        this.cdrDate = cdrDate;
    }

    public String getCdrType() {
        return cdrType;
    }

    public void setCdrType(String cdrType) {
        this.cdrType = cdrType;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getRecOpeningTime() {
        return recOpeningTime;
    }

    public void setRecOpeningTime(String recOpeningTime) {
        this.recOpeningTime = recOpeningTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getRecClosureTime() {
        return recClosureTime;
    }

    public void setRecClosureTime(String recClosureTime) {
        this.recClosureTime = recClosureTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDialledPartyAddr() {
        return dialledPartyAddr;
    }

    public void setDialledPartyAddr(String dialledPartyAddr) {
        this.dialledPartyAddr = dialledPartyAddr;
    }

    public String getRingingDuration() {
        return ringingDuration;
    }

    public void setRingingDuration(String ringingDuration) {
        this.ringingDuration = ringingDuration;
    }

    public String getAccountingRecordType() {
        return accountingRecordType;
    }

    public void setAccountingRecordType(String accountingRecordType) {
        this.accountingRecordType = accountingRecordType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getCallingNumber() {
        return callingNumber;
    }

    public void setCallingNumber(String callingNumber) {
        this.callingNumber = callingNumber;
    }

    public String getTranslatedNumber() {
        return translatedNumber;
    }

    public void setTranslatedNumber(String translatedNumber) {
        this.translatedNumber = translatedNumber;
    }

    public String getConnectedNum() {
        return connectedNum;
    }

    public void setConnectedNum(String connectedNum) {
        this.connectedNum = connectedNum;
    }

    public String getRecordingEntity() {
        return recordingEntity;
    }

    public void setRecordingEntity(String recordingEntity) {
        this.recordingEntity = recordingEntity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getBasicService() {
        return basicService;
    }

    public void setBasicService(String basicService) {
        this.basicService = basicService;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    @Override
    public String toString() {
        return "Cdr{" +
                "cdrDate='" + cdrDate + '\'' +
                ", cdrType='" + cdrType + '\'' +
                ", recordType='" + recordType + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", recOpeningTime='" + recOpeningTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", recClosureTime='" + recClosureTime + '\'' +
                ", duration='" + duration + '\'' +
                ", dialledPartyAddr='" + dialledPartyAddr + '\'' +
                ", ringingDuration='" + ringingDuration + '\'' +
                ", accountingRecordType='" + accountingRecordType + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", imsi='" + imsi + '\'' +
                ", imei='" + imei + '\'' +
                ", callingNumber='" + callingNumber + '\'' +
                ", translatedNumber='" + translatedNumber + '\'' +
                ", connectedNum='" + connectedNum + '\'' +
                ", recordingEntity='" + recordingEntity + '\'' +
                ", location='" + location + '\'' +
                ", cellId='" + cellId + '\'' +
                ", basicService='" + basicService + '\'' +
                ", answerTime='" + answerTime + '\'' +
                '}';
    }
}
