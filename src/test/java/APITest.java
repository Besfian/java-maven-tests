import org.springframework.boot.test.context.SpringBootTest;


import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APITest {


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    @Description("API Test Example")
    public void testAPIFunctionality() {
        String url = "http://httpbin.org/get";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(200, response.getStatusCodeValue());
        // Другие проверки вашего API теста
    }
}
