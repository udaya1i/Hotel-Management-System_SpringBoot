package hotel.hmsbackend.rest;
import hotel.hmsbackend.wrapper.HMSWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
@RequestMapping("/hms")
public interface UserRest {
        @PostMapping(path = "/signup")
        public ResponseEntity<String> signup(@RequestBody (required = true)Map<String, String> requestMap);
        @PostMapping(path = "/login")
        public ResponseEntity<String> signin(@RequestBody (required = true) Map<String, String> requestMap);
        @GetMapping(path = "/get")
        public ResponseEntity<List<HMSWrapper>> getAllUser();
        @PostMapping(path = "/update")
        public ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);
        @GetMapping(path = "/gettoken")
        public ResponseEntity<String> checkToken();
        @PostMapping(path = "/changepassword")
        ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);
        @PostMapping("/forgetpassword")
        ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> requestMap);
}

