import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByCardDelivery() {
//        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//span [@data-test-id = 'city']// input").setValue("Красноярск");
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $x("//span[@data-test-id = 'date']//input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//span[@data-test-id = 'date']//input").sendKeys(currentDate);
        $x("//span[@data-test-id = 'name']//input").setValue("Иванова Елена");
        $x("//span[@data-test-id = 'phone']//input").setValue("+79998887766");
        $x("//label[@data-test-id = 'agreement']").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//div [@class = 'notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
}
