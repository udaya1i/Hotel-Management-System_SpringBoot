package hotel.hmsbackend.pojo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc ")
@NamedQuery(name = "Bill.getBillByUserName", query = "select b from Bill b where b.createdBy =:username order by  b.id desc ")
@Entity
@DynamicInsert
@DynamicUpdate
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String contactNumber;
    private String name;
    private String email;
    private String paymentMethod;
    private Integer total;
    @Column(columnDefinition = "json")
    private String productDetail;
    private String createdBy;
    public Bill() {
    }
    public Bill(Integer id, String uuid, String contactNumber, String name, String email, String paymentMethod, Integer total, String productDetail, String createdBy) {
        this.id = id;
        this.uuid = uuid;
        this.contactNumber = contactNumber;
        this.name = name;
        this.email = email;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.productDetail = productDetail;
        this.createdBy = createdBy;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
