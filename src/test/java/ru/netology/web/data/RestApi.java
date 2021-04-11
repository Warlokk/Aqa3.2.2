package ru.netology.web.data;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.val;
import ru.netology.web.data.DataHelper.*;

import static io.restassured.RestAssured.*;

public class RestApi {

    public static void login(AuthInfo authInfo) {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(new AuthInfo(authInfo.getLogin(), authInfo.getPassword())))
                .when()
                .post("/auth")
                .then()
                .statusCode(200);
    }

    public static String verificationToken(String login, String code) {
        String token =
                given()
                        .baseUri("http://localhost:9999/api")
                        .contentType(ContentType.JSON)
                        .body(new Gson().toJson(new VerificationCode(login, code)))
                        .when()
                        .post("/auth/verification")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");
        return token;
    }

    public static void transfer(String token, String fromCard, String toCard, long amount, int statusCode) {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .headers("Authorization", "Bearer " + token)
                .body(new Gson().toJson(new TransferDetail(fromCard, toCard, amount)))
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode);
    }

    public static Card[] cardInfo(String token) {
        val cards =
                given()
                        .baseUri("http://localhost:9999/api")
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get("/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(Card[].class);
        return cards;
    }

}
