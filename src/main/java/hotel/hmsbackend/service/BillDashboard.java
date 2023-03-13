package hotel.hmsbackend.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BillDashboard {
    ResponseEntity<Map<String, Object>> getDetails();
}
