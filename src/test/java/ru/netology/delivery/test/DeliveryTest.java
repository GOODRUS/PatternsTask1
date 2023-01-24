package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class DeliveryTest {

    private static Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }
    DataGenerator data = new DataGenerator();

    @Test
    void shouldTestCardForm() {

        long daysToAdd = 3;
        long daysToAddNext = 5;

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue(data.generateCity("ru"));
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(data.generateDate(daysToAdd, "dd.LL.yyyy"));
        $("span[data-test-id='name'] input").setValue(data.generateName("ru"));
        $("span[data-test-id='phone'] input").setValue(data.generatePhone("ru"));
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Запланировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно запланирована на " + data.generateDate(daysToAdd, "dd.LL.yyyy")), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);

        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(data.generateDate(daysToAddNext, "dd.LL.yyyy"));
        $$("button").find(exactText("Запланировать")).click();
        $x("//div[contains(text(), 'Необходимо подтверждение')]").should(appear, Duration.ofSeconds(15));
        $$("button").find(exactText("Перепланировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно запланирована на " + data.generateDate(daysToAddNext, "dd.LL.yyyy")), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);
    }
}


