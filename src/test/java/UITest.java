import org.springframework.boot.test.context.SpringBootTest;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UITest {

    @Test
    @Description("UI Test Example")
    public void testUIFunctionality() {
        open("https://example.com");
        $("h1").shouldHave(Condition.text("Welcome"));
        // Другие шаги вашего UI теста
    }
}
