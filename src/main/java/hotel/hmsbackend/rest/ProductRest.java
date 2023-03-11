package hotel.hmsbackend.rest;

import hotel.hmsbackend.pojo.Product;
import hotel.hmsbackend.wrapper.ProductWrapper;
import org.hibernate.hql.internal.ast.tree.ResolvableNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping("/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);
}
