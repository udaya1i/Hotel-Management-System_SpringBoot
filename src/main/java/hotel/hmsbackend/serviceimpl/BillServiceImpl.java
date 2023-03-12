package hotel.hmsbackend.serviceimpl;

import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.BillDao;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.Bill;
import hotel.hmsbackend.service.BillService;
import hotel.hmsbackend.utils.HMSUtilits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;
@Service
@Slf4j
public class BillServiceImpl implements BillService {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BillDao billDao;
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("generateReport");
        try{
            String fileName;
            if (validateRequestMap(requestMap)){
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")){
                    fileName = (String) requestMap.get("uuid");
                }else {
                    fileName = HMSUtilits.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }
            }
            return HMSUtilits.getResponseEntity("Data Not Found", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return  requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetail") &&
                requestMap.containsKey("total");
    }
    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String)requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("total")));
            bill.setProductDetail((String) requestMap.get("setProductDetail"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
         }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
