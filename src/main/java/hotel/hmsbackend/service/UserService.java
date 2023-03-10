package hotel.hmsbackend.service;

import hotel.hmsbackend.wrapper.HMSWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<HMSWrapper>> getAllUser();
    ResponseEntity<String> update(Map<String, String> requestMap);
    ResponseEntity<String> checkToken();
    ResponseEntity<String> changepassword(Map<String, String> requestMap);
    ResponseEntity<String> forgetPassword(Map<String, String> requestMap);
}
