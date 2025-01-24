package mn.unitel.service;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;

import io.quarkus.runtime.Startup;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mn.unitel.entity.Cdr;
import mn.unitel.entity.Field;
import mn.unitel.entity.Query;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Startup
public class DBService {
    @Inject
    @DataSource("imei-data")
    AgroalDataSource ds;

    @Inject
    ConfigurationService cs;

    @PostConstruct
    void start() {
        System.out.println("Starting DB service");
        testConnection();
    }

    public void testConnection() {
        try (var connection = ds.getConnection()) {
            System.out.println("Connected to IMEI database: " + connection.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cdr> selectCdr(String phoneNumber) {
        String selectQuery = "select * from cdrs where imsi = ?";

        List<Cdr> cdrs = new ArrayList<>();
        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, phoneNumber);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Cdr cdr = new Cdr();
                cdr.setCdrDate(rs.getString("cdr_date"));
                cdr.setCdrType(rs.getString("cdr_type"));
                cdr.setRecordType(rs.getString("record_type"));
                cdr.setStartDateTime(rs.getString("start_date_time"));
                cdr.setRecOpeningTime(rs.getString("rec_opening_time"));
                cdr.setEndDateTime(rs.getString("end_date_time"));
                cdr.setRecClosureTime(rs.getString("rec_closure_time"));
                cdr.setDuration(rs.getString("duration"));
                cdr.setDialledPartyAddr(rs.getString("dialled_party_addr"));
                cdr.setRingingDuration(rs.getString("ringing_duration"));
                cdr.setAccountingRecordType(rs.getString("accounting_record_type"));
                cdr.setServiceType(rs.getString("service_type"));
                cdr.setImsi(rs.getString("imsi"));
                cdr.setImei(rs.getString("imei"));
                cdr.setCallingNumber(String.valueOf(rs.getString("calling_number")));
                cdr.setTranslatedNumber(String.valueOf(rs.getString("translated_number")));
                cdr.setConnectedNum(String.valueOf(rs.getString("connected_num")));
                cdr.setRecordingEntity(String.valueOf(rs.getString("recording_entity")));
                cdr.setRecordingEntity(String.valueOf(rs.getString("recording_entity")));
                cdr.setLocation(String.valueOf(rs.getString("location")));
                cdr.setCellId(String.valueOf(rs.getString("cell_id")));
                cdr.setBasicService(String.valueOf(rs.getString("basic_service")));
                cdr.setAnswerTime(String.valueOf(rs.getString("answer_time")));
                cdrs.add(cdr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return cdrs;
        }
        return cdrs;
    }

    public void insertUser(String firstName, String lastName, String nationalId, String dob, String phoneNo) {
        String insertQuery = "insert into users values(?, ?, ?, ?, ?, ?)";
        long currentTime = System.currentTimeMillis();

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, nationalId);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, phoneNo);
            preparedStatement.setString(6, String.valueOf(currentTime));
            preparedStatement.executeQuery();

        }catch (SQLException e) {
        e.printStackTrace();
        }
    }

    //xml
    // 0-connected call; 1-attempted call; 6-outgoing sms; 7-incoming sms; 100-call divert(forward)
    public JsonArray selectCdrXml(String searchKey, String searchValue, int page, int pageSize) {
        Query query = cs.getQuery("select_cdr");

        String selectQuery = "select * from cdrs where ";
        int keyNumber = 0;
        if(searchKey.equals("phone_number"))    {selectQuery += "calling_number=? or dialled_party_addr=?"; keyNumber=2;}
        else                {selectQuery += (searchKey + "=?"); keyNumber=1;}

        List<String> recordTypes = new ArrayList<>();
        String service = query.getService();
        String smsDirection = query.getSmsDirection();
        if(service.contains("call"))    recordTypes.add("0");
        if(service.contains("sms"))    {
            if(smsDirection.contains("out"))    recordTypes.add("6");
            if(smsDirection.contains("in"))    recordTypes.add("7");
        }

        selectQuery += " and (";
        for(int i = 0; i < recordTypes.size(); i++) {
            selectQuery += ("record_type=?");
            if(i != recordTypes.size() - 1) selectQuery += " or ";
        }
        selectQuery += ")";

        String sort = query.getReturnJson().getSort();
        if(sort.contains("desc")) selectQuery += (" order by " + query.getReturnJson().getSortColumn() + " desc");
        if(sort.contains("asc")) selectQuery += (" order by " + query.getReturnJson().getSortColumn() + " asc");

        int offsetValue = (page-1)*pageSize;
        int fetchValue = pageSize;

        // OFFSET 10 ROWS FETCH NEXT 5 ROWS ONLY;
        selectQuery += ( " offset " + offsetValue + " rows fetch next " + fetchValue + " rows only");

        System.out.println(selectQuery);

        JsonArray cdrs = new JsonArray();

        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)){

            if(keyNumber == 2){
                String calledNumber = "+" + searchValue;
                preparedStatement.setString(1, calledNumber);
                String dialledNumber ="tel:" + searchValue;
                preparedStatement.setString(2, dialledNumber);
            }
            if(keyNumber == 1){
                preparedStatement.setString(1, searchValue);
            }
            for(int i = 0; i < recordTypes.size(); i++) {
                preparedStatement.setString(keyNumber + 1 + i, recordTypes.get(i));
            }

            ResultSet rs = preparedStatement.executeQuery();

            List<Field> fields = query.getReturnJson().getFields();
            int columnCount = fields.size();
            int rowNo = 1 + (page-1)*pageSize;
            while (rs.next()) {
                    JsonObject cdr = new JsonObject();
                    cdr.put("No", rowNo);
                    for(int i = 1; i <= columnCount; i++) {
                        Field field = fields.get(i - 1);
                        String label = field.getLabel();
                        String labelName = field.getValue();
                        String fieldDataType = field.getDataType();
                        String resultGet = "get" + fieldDataType.substring(0, 1).toUpperCase() + fieldDataType.substring(1).toLowerCase();
                        Method method = ResultSet.class.getMethod(resultGet, String.class);
                        Object fieldValue = method.invoke(rs, label);
                        fieldValue = numberTypeConversion(label, String.valueOf(fieldValue), field.getNumberType());
                        cdr.put(labelName, fieldValue);
                    }
                    cdrs.add(cdr);
                    rowNo++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return cdrs;
    }


    public int countRowsCdrXml(String searchKey, String searchValue) {
        Query query = cs.getQuery("select_cdr");
        int count=0;

        String countQuery = "select count(*) from cdrs where ";
        int keyNumber = 0;
        if(searchKey.equals("phone_number"))    {countQuery += "calling_number=? or dialled_party_addr=?"; keyNumber=2;}
        else                {countQuery += (searchKey + "=?"); keyNumber=1;}

        List<String> recordTypes = new ArrayList<>();
        String service = query.getService();
        String smsDirection = query.getSmsDirection();
        if(service.contains("call"))    recordTypes.add("0");
        if(service.contains("sms"))    {
            if(smsDirection.contains("out"))    recordTypes.add("6");
            if(smsDirection.contains("in"))    recordTypes.add("7");
        }

        countQuery += " and (";
        for(int i = 0; i < recordTypes.size(); i++) {
            countQuery += ("record_type=?");
            if(i != recordTypes.size() - 1) countQuery += " or ";
        }
        countQuery += ")";

        System.out.println(countQuery);

        try(Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(countQuery)){

            if(keyNumber == 2){
                String calledNumber = "+" + searchValue;
                preparedStatement.setString(1, calledNumber);
                String dialledNumber ="tel:" + searchValue;
                preparedStatement.setString(2, dialledNumber);
            }
            if(keyNumber == 1){
                preparedStatement.setString(1, searchValue);
            }
            for(int i = 0; i < recordTypes.size(); i++) {
                preparedStatement.setString(keyNumber + 1 + i, recordTypes.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e){
            e.printStackTrace();
            return count;
        }
        return count;
    }


    String numberTypeConversion(String label, String value, int numberType){
        String stripNumber;
                if(label.equals("dialled_party_addr") && value.length()>=3){stripNumber = value.substring(4);}
        else    if(label.equals("calling_number") && value.length()>=1)  {stripNumber = value.substring(1);}
        else    if(label.equals("connected_num") && value.length()>=4)  {stripNumber = value.substring(5);}
        else    stripNumber = value;

        if(numberType == 0 || stripNumber.isEmpty()) return stripNumber;
        if(numberType == 1) return "+" + stripNumber;
        if(numberType == 2) return "tel:" + stripNumber;
        return "tel:+" + stripNumber;
    }

}
