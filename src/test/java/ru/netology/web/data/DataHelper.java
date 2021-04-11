package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.*;


@Data
public class DataHelper {

    @Value
    public static class AuthInfo {

        String login;
        String password;

    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getRandomAuthInfo() {
        val faker = new Faker();
        val name = faker.name().username();
        val password = "$2a$10$UvF1b6JIdHKpe0Wk8nILz.S4iTbBDSXrfDThlY2PKw5tUHGAHwo62"; //password is "123qwerty"
        QuerySQL.addUser(name, password);
        return new AuthInfo(name, "123qwerty");
    }

    @Value
    public static class VerificationCode {

        String login;
        String code;

    }

    @Value
    public static class TransferDetail {

        String from;
        String to;
        long amount;

    }

    @Value
    public static class Card {
        String id;
        String number;
        long balance;

    }

}