import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.sql.SQLOutput;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class ServiceTestModeTestingV1 {
    @BeforeAll
    static void setUp(){
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = GenerateData.Registration.getRegisteredUser("active");
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[id=root]").shouldBe(Condition.visible);
        $("[id=root]").shouldHave(Condition.exactText("  Личный кабинет"));
        $("[lang=en]").sendKeys(Keys.chord(Keys.ALT, Keys.LEFT));
    }

    @Test
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = GenerateData.Registration.getUser("active");
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = GenerateData.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $(".button").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка\n" +
                "Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        open("http://localhost:9999");
        var registeredUser = GenerateData.Registration.getRegisteredUser("active");
        var wrongLogin = GenerateData.getRandomLogin();
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        open("http://localhost:9999");
        var registeredUser = GenerateData.Registration.getRegisteredUser("active");
        var wrongPassword = GenerateData.getRandomPassword();
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").setValue(wrongPassword);
        $(".button").click();
        $("[data-test-id=error-notification]").shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(Condition.exactText("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль"));
    }




}
