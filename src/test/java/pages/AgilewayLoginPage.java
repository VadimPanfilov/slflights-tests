package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class AgilewayLoginPage {
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement signInButton = $("input[value='Sign in']");

    public AgilewayLoginPage open() {
        Selenide.open("https://travel.agileway.net");
        return this;
    }

    public void loginAs(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        signInButton.click();
    }

    public void shouldShowWelcome(String username) {
        $("body").shouldHave(text(username));
    }
}
