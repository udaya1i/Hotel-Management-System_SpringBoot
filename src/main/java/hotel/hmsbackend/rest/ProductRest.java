package hotel.hmsbackend.rest;

import hotel.hmsbackend.pojo.Product;
import hotel.hmsbackend.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.geom.RectangularShape;
import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();
}
