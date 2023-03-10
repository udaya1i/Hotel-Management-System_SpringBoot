package hotel.hmsbackend.serviceimpl;
import hotel.hmsbackend.constent.HMSConstant;
import hotel.hmsbackend.dao.UserDao;
import hotel.hmsbackend.jwt.CustomerUserDetailsSerivce;
import hotel.hmsbackend.jwt.JWTUtils;
import hotel.hmsbackend.jwt.JwtFilter;
import hotel.hmsbackend.pojo.User;
import hotel.hmsbackend.service.UserService;
import hotel.hmsbackend.utils.EmailUtils;
import hotel.hmsbackend.utils.HMSUtilits;
import hotel.hmsbackend.wrapper.HMSWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.*;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsSerivce customerUserDetailsSerivce;
    @Autowired
    JWTUtils jwtUtils;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside Signup{}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.FindByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return HMSUtilits.getResponseEntity("Register Successfully", HttpStatus.OK);
                } else {
                    return HMSUtilits.getResponseEntity("User is Already Registered with Same Email Address", HttpStatus.BAD_REQUEST);
                }
            } else {
                return HMSUtilits.getResponseEntity(HMSConstant.invalid_data, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private boolean validateSignUpMap(Map<String, String> requestMap){
       if(requestMap.containsKey("name")&& requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")&& requestMap.containsKey("password"))
       {
           return true;
        }
       return false;
    }
    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside Login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
//            System.out.println("about to go inside if condition"); //testing

            if (auth.isAuthenticated()){
//                System.out.println("inside if condition"); //testing
                if (customerUserDetailsSerivce.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+jwtUtils.generateToken(customerUserDetailsSerivce.getUserDetail().getEmail(),
                            customerUserDetailsSerivce.getUserDetail().getRole())+"\"}", HttpStatus.OK);
                }
                else {
                    System.out.println(("inside else condition")); //testing
                    return new ResponseEntity<String>("{\"message\":\""+ "wait until user accept you request"+"\"}", HttpStatus.BAD_REQUEST);
                }
            }
//            System.out.println("out of inside"); //testing
        }catch (Exception ex){
//            System.out.println("inside catch exception");// testing

            log.error("{}", ex);
        }
//        System.out.println("out.............");// testing

        return new ResponseEntity<String>("{\"message\":\""+ "Something Went Wrong"+"\"}", HttpStatus.BAD_REQUEST);
    }
    @Override
    public ResponseEntity<List<HMSWrapper>> getAllUser() {
        try
        {
           if (jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
           }
           else {
               return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
               Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
               if(!optional.isEmpty()){
                        userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                        sentMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                        return HMSUtilits.getResponseEntity("UserStatus updated Successfully", HttpStatus.OK);
               }else {
                return HMSUtilits.getResponseEntity("User Id doesn't exist", HttpStatus.OK);
               }
            }else {
                return HMSUtilits.getResponseEntity(HMSConstant.unauthorize_access, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private void sentMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:-"+user+"\n  is approved by \n Admi:- \n"+jwtFilter.getCurrentUser(), allAdmin);
        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:-"+user+"\n  is disabled by \n Admi:- \n"+jwtFilter.getCurrentUser(), allAdmin);
        }
    }
    @Override
    public ResponseEntity<String> checkToken() {
    return HMSUtilits.getResponseEntity("true", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<String> changepassword(Map<String, String> requestMap) {
        try{
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                 if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                     userObj.setPassword(requestMap.get("newPassword"));
                     userDao.save(userObj);
                     return HMSUtilits.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                 }
                 return HMSUtilits.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestMap) {
        try{
            User user= userDao.findByEmail(requestMap.get("email"));
            if(user != null && !user.getEmail().isEmpty())
                emailUtils.forgetMail(user.getEmail(), "crenditals by HMS", user.getPassword());
                return HMSUtilits.getResponseEntity("Check Your Email", HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return HMSUtilits.getResponseEntity(HMSConstant.something_went_wrong, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
