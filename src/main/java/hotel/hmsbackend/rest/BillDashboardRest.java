package hotel.hmsbackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

public interface BillDashboardRest {
    @RequestMapping(path = "/dashboard")
    ResponseEntity<Map<String,Object>> getDetails();
}
