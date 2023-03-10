package hotel.hmsbackend.restimpl;
import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.rest.UserRest;
import hotel.hmsbackend.service.UserService;
import hotel.hmsbackend.utils.HMSUtilits;
import hotel.hmsbackend.wrapper.HMSWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    private UserService userService;
    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        try{
            return userService.signUp(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> signin(Map<String, String> requestMap) {
        try{
            userService.login(requestMap);

        }catch (Exception ex){
        ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<List<HMSWrapper>> getAllUser() {
        try
        {
            return userService.getAllUser();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<HMSWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try
        {
        return userService.update(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changepassword(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
        try {
            return userService.forgetPassword(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
