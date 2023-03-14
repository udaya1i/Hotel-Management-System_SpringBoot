package hotel.hmsbackend.pojo;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
@NamedQuery(name = "Product.getAllProduct", query = "select new hotel.hmsbackend.wrapper.ProductWrapper(p.id, p.name,p.description, p.price,p.status, p.category.id,p.category.name ) from Product p  ")
@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")  // this p.status =:status is same as in productdao ({@Param("status")String status}) -----
@NamedQuery(name = "Product.getProductByCategory", query ="select new hotel.hmsbackend.wrapper.ProductWrapper(p.id, p.name) from Product p where p.category.id=:id and p.status='true' ")
@NamedQuery(name="product.getProductById", query = "select new hotel.hmsbackend.wrapper.ProductWrapper(p.id, p.name,p.description, p.price) from Product p where p.id=:id")
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {
    private static final Long serialCersionUid =1234L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "category_fk")
    private Category category;

    private String description;
    private Integer price;
    private  String status;

    public Product() {
    }

    public Product(Integer id, String name, Category category, String description, Integer price, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
