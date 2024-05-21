import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;
class GenerateData {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(io.restassured.http.ContentType.JSON)
            .setContentType(io.restassured.http.ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));
    private GenerateData() {
    }

        private static void sendRequest(RegistrationDto user) {
             final RequestSpecification requestSpec = new RequestSpecBuilder()
                    .setBaseUri("http://localhost")
                    .setPort(9999)
                    .setAccept(io.restassured.http.ContentType.JSON)
                    .setContentType(io.restassured.http.ContentType.JSON)
                    .log(LogDetail.ALL)
                    .build();

            given()
                    .spec(requestSpec)
                    .body(user)
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }



    public static String getRandomLogin() {
        String login = faker.internet().emailAddress();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }


    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            return new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        }

        public static RegistrationDto getRegisteredUser(String status) {
            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }


    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }


    }


