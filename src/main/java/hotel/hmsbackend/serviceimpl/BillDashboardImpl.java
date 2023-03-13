package hotel.hmsbackend.serviceimpl;

import hotel.hmsbackend.dao.BillDao;
import hotel.hmsbackend.dao.CategoryDao;
import hotel.hmsbackend.dao.ProductDao;
import hotel.hmsbackend.rest.BillDashboardRest;
import hotel.hmsbackend.service.BillDashboard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BillDashboardImpl implements BillDashboard {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
