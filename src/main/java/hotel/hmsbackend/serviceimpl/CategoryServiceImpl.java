package hotel.hmsbackend.serviceimpl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.CategoryDao;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.Category;
import hotel.hmsbackend.rest.CategoryRest;
import hotel.hmsbackend.service.CategoyService;
import hotel.hmsbackend.utils.HMSUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoyService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                        categoryDao.save(getCategoryFromMap(requestMap, false));
                        return HMSUtilits.getResponseEntity("Category Added Successfully", HttpStatus.OK);
                }
            } else {
                return HMSUtilits.getResponseEntity(HMSConstant.unauthorize_access, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }
    private Category getCategoryFromMap (Map<String,String>requestMap, Boolean isAdd){
        Category category = new Category();
        if (isAdd){
                 category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
}

