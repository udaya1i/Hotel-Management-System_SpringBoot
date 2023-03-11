package hotel.hmsbackend.restimpl;

import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.rest.ProductRest;
import hotel.hmsbackend.service.ProductService;
import hotel.hmsbackend.utils.HMSUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {
    @Autowired
    ProductService productService;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            return productService.addNewProduct(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
