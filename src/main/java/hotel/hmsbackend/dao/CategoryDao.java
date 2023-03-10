package hotel.hmsbackend.dao;

import hotel.hmsbackend.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Integer> {
List<Category> getAllCategory();
}
