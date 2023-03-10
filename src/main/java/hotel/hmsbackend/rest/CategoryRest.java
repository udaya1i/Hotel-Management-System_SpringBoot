package hotel.hmsbackend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {
    ResponseEntity<?> addNewCategory(@RequestBody(required = true)Map<String, String> requestMap);
}
