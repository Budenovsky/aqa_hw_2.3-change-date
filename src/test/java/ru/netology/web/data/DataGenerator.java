package ru.netology.web.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    Faker faker = new Faker(new Locale("ru"));

    public String getCity() {
        String city = faker.address().city();
        return city;
    }
    public String getDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getName() {
        String name = faker.name().fullName();
        return name;
    }

    public String getPhone() {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }
}

