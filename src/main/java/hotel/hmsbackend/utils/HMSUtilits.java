package hotel.hmsbackend.utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class HMSUtilits {
    public HMSUtilits() {
    }
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"messgae\":\""+ responseMessage+"\" }", httpStatus);
    }
    public static  String getUUID(){
        Date date = new Date();
        long time = date.getTime();
        return "Bill -"+time;
    }
}
