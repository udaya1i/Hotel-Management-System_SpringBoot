package hotel.hmsbackend.restimpl;

import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.rest.CategoryRest;
import hotel.hmsbackend.serviceimpl.CategoryServiceImpl;
import hotel.hmsbackend.utils.HMSUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {
    @Autowired
    CategoryServiceImpl categoryService;
    @Override
    public ResponseEntity<?> addNewCategory(Map<String, String> requestMap) {
        try{
            return categoryService.addNewCategory(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
