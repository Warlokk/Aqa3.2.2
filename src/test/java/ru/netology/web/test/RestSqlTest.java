package ru.netology.web.test;


import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static ru.netology.web.data.QuerySQL.*;
import static ru.netology.web.data.RestApi.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;


public class RestSqlTest {



    @Test
    void shouldLoginAndTransferOwnCards() {
        val user = getAuthInfo();
        login(user);
        String token = verificationToken(user.getLogin(), getVerificationCodeFor(user.getLogin()));
        val cards = cardInfo(token);
        val fromCard = cards[0].getId();
        val toCard = cards[1].getId();
        val initialFromCardBalance = getCardBalance(fromCard);
        val initialToCardBalance = getCardBalance(toCard);
        long amount = 5000;
        int statusCode = 200;
        transfer(token, getCardNumber(fromCard), getCardNumber(toCard), amount, statusCode);
        assertEquals(initialFromCardBalance - amount, getCardBalance(fromCard));
        assertEquals(initialToCardBalance + amount, getCardBalance(toCard));
    }

    @Test
    void shouldLoginAndTransferToAnothersCards() {
        val user = getAuthInfo();
        login(user);
        String token = verificationToken(user.getLogin(), getVerificationCodeFor(user.getLogin()));
        val cards = cardInfo(token);
        val fromCard = cards[0].getId();
        val initialFromCardBalance = getCardBalance(fromCard);
        long amount = 5000;
        int statusCode = 200;
        transfer(token, getCardNumber(fromCard), "5559 0000 0000 0008", amount, statusCode);
        assertEquals(initialFromCardBalance - amount, getCardBalance(fromCard));
    }

    @Test
    void shouldNotTransferFromAnothersCard() {
        val user = getAuthInfo();
        login(user);
        String token = verificationToken(user.getLogin(), getVerificationCodeFor(user.getLogin()));
        val cards = cardInfo(token);
        val toCard = cards[0].getId();
        val initialToCardBalance = getCardBalance(toCard);
        long amount = 5000;
        int statusCode = 406;
        transfer(token, "5559 0000 0000 0008", getCardNumber(toCard), amount, statusCode);
        assertEquals(initialToCardBalance, getCardBalance(toCard));
    }

    @Test
    void shouldNotTransferOverdraft() {
        val user = getAuthInfo();
        login(user);
        String token = verificationToken(user.getLogin(), getVerificationCodeFor(user.getLogin()));
        val cards = cardInfo(token);
        val fromCard = cards[0].getId();
        val toCard = cards[1].getId();
        val initialFromCardBalance = getCardBalance(fromCard);
        val initialToCardBalance = getCardBalance(toCard);
        long amount = initialFromCardBalance + 1000;
        int statusCode = 406;
        transfer(token, getCardNumber(fromCard), getCardNumber(toCard), amount, statusCode);
        assertEquals(initialFromCardBalance, getCardBalance(fromCard));
        assertEquals(initialToCardBalance, getCardBalance(toCard));
    }


}
