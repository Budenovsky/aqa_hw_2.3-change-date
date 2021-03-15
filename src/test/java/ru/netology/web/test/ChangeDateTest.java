package ru.netology.web.test;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class ChangeDateTest {
    DataGenerator dataGenerator = new DataGenerator();

    @Test
    void shouldSendAndRedateInMoment() {
        open("http://localhost:9999/");
//        $("[data-test-id='city'] input").setValue(faker.address().city());
        $("[data-test-id='city'] input").setValue(dataGenerator.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dataGenerator.getDate(3));
        $("[data-test-id='name'] input").setValue(dataGenerator.getName());
        $("[data-test-id='phone'] input").setValue(dataGenerator.getPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована")).shouldBe(visible);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(dataGenerator.getDate(6));
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $(".notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(6)));
    }
}
