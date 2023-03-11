package hotel.hmsbackend.dao;

import hotel.hmsbackend.pojo.Product;
import hotel.hmsbackend.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<ProductWrapper> getAllProduct();
}
