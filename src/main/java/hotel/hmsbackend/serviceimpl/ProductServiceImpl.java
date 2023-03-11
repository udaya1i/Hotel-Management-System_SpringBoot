package hotel.hmsbackend.serviceimpl;

import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.ProductDao;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.Category;
import hotel.hmsbackend.pojo.Product;
import hotel.hmsbackend.service.ProductService;
import hotel.hmsbackend.utils.HMSUtilits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {

            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return HMSUtilits.getResponseEntity("Product Added SuccessFully!!!", HttpStatus.OK);
                }
                return HMSUtilits.getResponseEntity(HMSConstant.invalid_data, HttpStatus.BAD_REQUEST);
            } else {
                return HMSUtilits.getResponseEntity(HMSConstant.unauthorize_access, HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }
    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }
            product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
    return product;
    }

}
