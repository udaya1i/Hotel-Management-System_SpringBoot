package hotel.hmsbackend.serviceimpl;
import com.google.common.base.Strings;
import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.CategoryDao;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.Category;
import hotel.hmsbackend.service.CategoyService;
import hotel.hmsbackend.utils.HMSUtilits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
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
    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try{
            if (!Strings.isNullOrEmpty(filterValue)&&filterValue.equalsIgnoreCase("true")){
                    log.info("testing .......");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
                if (jwtFilter.isAdmin()){
                    if (validateCategoryMap(requestMap,true)){
                       Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                        if (!optional.isEmpty()){
                                categoryDao.save(getCategoryFromMap(requestMap, true));
                                return HMSUtilits.getResponseEntity("Category Updated successfully!!", HttpStatus.OK);
                        }else {
                            return HMSUtilits.getResponseEntity("Id does not exist!!!", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }
                }else {
                    return HMSUtilits.getResponseEntity(HMSConstant.unauthorize_access, HttpStatus.UNAUTHORIZED);
                }
                return HMSUtilits.getResponseEntity(HMSConstant.invalid_data, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

