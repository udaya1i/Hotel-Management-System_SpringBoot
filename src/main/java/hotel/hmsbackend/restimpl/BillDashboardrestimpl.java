package hotel.hmsbackend.restimpl;

import hotel.hmsbackend.rest.BillDashboardRest;
import hotel.hmsbackend.service.BillDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BillDashboardrestimpl implements BillDashboardRest {
    @Autowired
    BillDashboard billDashboard;

    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        return billDashboard.getDetails();
    }
}
