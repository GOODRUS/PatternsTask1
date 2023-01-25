package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);


        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue(validUser.getCity());
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(firstMeetingDate);
        $("span[data-test-id='name'] input").setValue(validUser.getName());
        $("span[data-test-id='phone'] input").setValue(validUser.getPhone());
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Запланировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);

        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $x("//div[contains(text(), 'Необходимо подтверждение')]").should(appear, Duration.ofSeconds(15));
        $$("button").find(exactText("Перепланировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);
    }
}


