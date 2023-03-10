package hotel.hmsbackend.dao;

import hotel.hmsbackend.pojo.User;
import hotel.hmsbackend.wrapper.HMSWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {
    User FindByEmailId(@Param("email")String email);
    List<HMSWrapper> getAllUser();
    List<String>getAllAdmin();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status")String status, @Param("id") Integer id);
    User findByEmail(String email);

}
