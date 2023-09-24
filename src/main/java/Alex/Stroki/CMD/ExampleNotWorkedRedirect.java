package Alex.Stroki.CMD;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin(origins = {"http://localhost:8080", "*"}, allowedHeaders = "*")
@RestController
public class ExampleNotWorkedRedirect {

    public String testUrl = "https://stroki.ru/";
    //public String testUrl = "http://localhost:8080/redirect.html";

    @CrossOrigin(origins = {"http://localhost:8080", "*"})
    @GetMapping("/ResponseEntityVoid")
    ResponseEntity<Void> responseEntityVoid() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(testUrl))
                .build();
    }

    @CrossOrigin(origins = {"http://localhost:8080", "*"})
    @GetMapping("/RedirectView")
    public RedirectView redirectView() {
        return new RedirectView(testUrl);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "*"})
    @GetMapping("/ResponseEntity")
    public ResponseEntity<Object> responseEntity() throws URISyntaxException {
        URI uri = new URI(testUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @CrossOrigin(origins = {"http://localhost:8080", "*"})
    @GetMapping(value = "/HttpServletResponse")
    public void httpServletResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect(testUrl);
    }

}
