package hotel.hmsbackend.dao;

import hotel.hmsbackend.pojo.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill, Integer> {

}
