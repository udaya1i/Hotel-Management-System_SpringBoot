package hotel.hmsbackend.dao;

import hotel.hmsbackend.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
