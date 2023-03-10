package hotel.hmsbackend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CategoyService {
    ResponseEntity<String> addNewCategory(Map<String, String> requestMap);

}
