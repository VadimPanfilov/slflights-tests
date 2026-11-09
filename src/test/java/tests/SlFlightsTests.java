package tests;

import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SearchPage;
import pages.ResultsPage;
import pages.RegistrationPage;

public class SlFlightsTests {

    @Test
    public void successfulLoginAndLogout() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.loginAs("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.shouldBeVisible();
        searchPage.shouldShowWelcome("Иванов Иван Иванович");

        searchPage.logout();
        new LoginPage().shouldBeVisible();
    }

    @Test
    public void emptyPasswordShowsError() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.loginAs("standard_user", "");
        loginPage.shouldShowError("Пароль не может быть пустым");
    }

    @Test
    public void loginWithWrongPassword() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.loginAs("standard_user", "wrong_password");
        loginPage.shouldHaveErrorMessage("Неверное имя пользователя или пароль.");
    }

    @Test
    public void lockedOutUserCannotLogin() {
        LoginPage loginPage = new LoginPage().open();
        loginPage.loginAs("locked_out_user", "lock_pass2");
        loginPage.shouldHaveErrorMessage("Пользователь заблокирован");
    }

    @Test
    public void searchFlightAndSortByPrice() {
        new LoginPage().open().loginAs("standard_user", "stand_pass1");
        SearchPage searchPage = new SearchPage();
        searchPage.searchFlight("Москва", "Лондон");

        ResultsPage resultsPage = new ResultsPage();
        resultsPage.shouldShowResults();
        resultsPage.sortBy("price", "asc");
        resultsPage.shouldBeSortedByPriceAscending();
    }

    @Test
    public void checkRegistrationFormPrefilledData() {
        new LoginPage().open().loginAs("standard_user", "stand_pass1");
        new SearchPage().searchFlight("Москва", "Лондон");

        ResultsPage resultsPage = new ResultsPage();
        resultsPage.clickFirstFlightRegisterButton();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.shouldBePrefilled(
                "Иванов Иван Иванович",
                "1234 567890",
                "ivanov@example.com"
        );
    }

    @Test
    public void successfulFlightRegistration() {
        new LoginPage().open().loginAs("standard_user", "stand_pass1");
        new SearchPage().searchFlight("Москва", "Лондон");

        ResultsPage resultsPage = new ResultsPage();
        resultsPage.clickFirstFlightRegisterButton();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.fillPassengerData(
                "Петров Петр Петрович",
                "9876 543210",
                "petrov_test@example.com",
                "+7 (999) 123-45-67"
        );
        registrationPage.submit();

        registrationPage.shouldShowSuccess();
    }

    @Test
    public void returnToFlightsFromRegistrationForm() {
        new LoginPage().open().loginAs("standard_user", "stand_pass1");
        new SearchPage().searchFlight("Москва", "Лондон");

        ResultsPage resultsPage = new ResultsPage();
        resultsPage.clickFirstFlightRegisterButton();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.goBackToFlights();

        new ResultsPage().shouldShowResults();
    }
}