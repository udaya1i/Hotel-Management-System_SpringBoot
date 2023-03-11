package hotel.hmsbackend.service;

import hotel.hmsbackend.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<String> addNewProduct(Map<String, String> requestMap);
    ResponseEntity<List<ProductWrapper>> getAllProduct();
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);
    ResponseEntity<?> deleteProduct(Integer id);
    ResponseEntity<?> updateStatus(Map<String, String> requestMap);
    ResponseEntity<List<ProductWrapper>> getByCateogry(Integer id);
}
