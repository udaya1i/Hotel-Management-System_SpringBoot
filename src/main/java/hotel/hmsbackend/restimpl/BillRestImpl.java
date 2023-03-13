package hotel.hmsbackend.restimpl;

import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.pojo.Bill;
import hotel.hmsbackend.rest.BillRest;
import hotel.hmsbackend.service.BillService;
import hotel.hmsbackend.utils.HMSUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class BillRestImpl implements BillRest {
    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try {
            return billService.generateReport(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getAllBills() {
        try {
            return billService.getAllBills();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    @Override
    public ResponseEntity<byte[]> getPDF(Map<String, Object> reqeustMap) {
        try{
                billService.getBillPdf(reqeustMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deletebill(Integer id) {
        try{
            return billService.deletebill(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
