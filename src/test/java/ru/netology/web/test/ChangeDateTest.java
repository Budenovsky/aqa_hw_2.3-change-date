package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataGenerator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class ChangeDateTest {
    private Faker faker;

    String getDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSendAndRedateInMoment() {
        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue(faker.address().city());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        String minDeliveryDate = getDate(3);
        $("[data-test-id='date'] input").setValue(minDeliveryDate);
        $("[data-test-id='name'] input").setValue(faker.name().fullName());
        $("[data-test-id='phone'] input").setValue(faker.phoneNumber().phoneNumber()); /
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована")).shouldBe(visible);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        String newDeliveryDate = getDate(6);
        $("[data-test-id='date'] input").setValue(newDeliveryDate);
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Необходимо подтверждение")).shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $(".notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + newDeliveryDate));
    }
}
