package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String pattern = "dd.LL.yyyy";
        String data = LocalDate.now().plusDays(shift).format((DateTimeFormatter.ofPattern(pattern)));
        return data;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String number = faker.phoneNumber().phoneNumber();
        return number;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));
            UserInfo user = new UserInfo(faker.address().city(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber());
            return user;
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }
}
