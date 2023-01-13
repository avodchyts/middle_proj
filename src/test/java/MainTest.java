import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.NavigationHeaderUtilityPage;
import pages.NavigationMainPage;
import utils.LangValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class MainTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(MainTest.class);
    private static final Pattern contentPattern = Pattern.compile("/content/nutanix/");
    private static String langVal;
    private static String patternValue = ".com/" + langVal;
    private static final Pattern linkStructPattern = Pattern.compile(patternValue);
    @Test(description = "Check languages links", enabled = true)
    public void languagesLinksTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        NavigationHeaderUtilityPage navigationHeaderUtilityPage = new NavigationHeaderUtilityPage(getDriver());
        Map<String, String> languagesLinksMap = navigationHeaderUtilityPage.getLanguagesMap();
        for (Map.Entry<String, String> languageLink : languagesLinksMap.entrySet()) {
            String examLink = languageLink.getValue();
            Matcher contextMatcher = contentPattern.matcher(examLink);
            softAssert.assertFalse(contextMatcher.find(), String.format("Link has context structure %s", examLink));
        }

        for (LangValue langValue : LangValue.values()) {
            String nv = langValue.languageValue;
            if (langValue.languageValue.equals(currentLanguage) || langValue.equals(LangValue.INDIAN)) {
                continue;
            }
            if (langValue.equals(LangValue.CHINES)) {
                String link = languagesLinksMap.get(langValue.languageValue);
                LOGGER.info(String.format("Link value : %s", link));
                linkAPIChecks(softAssert, link);
                String patt = "." + langValue.langMeaning;
                Pattern pattern = Pattern.compile(patt);
                Matcher linkStructMatcher = pattern.matcher(link);
                softAssert.assertTrue(linkStructMatcher.find(), String.format("Link for Chines %s is not contain short description %s", link, patt));
                continue;
            }
            String langLink = languagesLinksMap.get(langValue.languageValue);
            LOGGER.info(String.format("Link value : %s", langLink));
            linkAPIChecks(softAssert, langLink);
            String mc = langValue.langMeaning;
            String patWord = ".com/" + mc;
            Pattern pattern = Pattern.compile(patWord);
            Matcher linkStructMatcher = pattern.matcher(langLink);
            softAssert.assertTrue(linkStructMatcher.find(), String.format("Link %s is not contain short description %s", langLink, mc));
        }

        softAssert.assertAll();
    }

    @Test(description = "Check navigation header utility links for default language", enabled = true)
    public void navigationHeaderUtilityLinksDefaultLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();
        String currentLanguageMeaning;
        List<String> shortDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            String nv = langValue.languageValue;
            if (langValue.languageValue.equals(currentLanguage) || langValue.languageValue.equals(LangValue.INDIAN) || langValue.languageValue.equals(LangValue.CHINES)) {
                currentLanguageMeaning = langValue.langMeaning;
                LOGGER.info(String.format("Language meaning is %s", currentLanguageMeaning));
                continue;
            }
            shortDescriptionsList.add(langValue.langMeaning);
        }
        NavigationHeaderUtilityPage navigationHeaderUtilityPage = new NavigationHeaderUtilityPage(getDriver());
        List<String> utilityHeaderLinks = navigationHeaderUtilityPage.getUtilityLinks();
        utilityHeaderLinks.add(navigationHeaderUtilityPage.getLoginLink());

        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = utilityHeaderLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescMainMainMenuLink : shortDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescMainMainMenuLink));
            }
        }

        List<String> globalLinksList = utilityHeaderLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescMainMainMenuLink : shortDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Localisation link %s is not contain short description %s", globalLink, shortDescMainMainMenuLink));
            }
        }

        List<String> externalLinksList = utilityHeaderLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String externalLink : externalLinksList) {
            linkAPIChecks(softAssert, externalLink);
            linkContentChecks(softAssert, externalLink);
        }
        softAssert.assertAll();
    }

    @Test(description = "Check navigation main menu links for default language", enabled = true)
    public void navigationMaimMenuLinksDefaultLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();
        String currentLanguageMeaning;

        List<String> shortMainMenuDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            String nv = langValue.languageValue;
            if (langValue.languageValue.equals(currentLanguage)) {
                currentLanguageMeaning = langValue.langMeaning;
                LOGGER.info(String.format("Default language meaning is %s", currentLanguageMeaning));
                continue;
            }
            shortMainMenuDescriptionsList.add(langValue.langMeaning);
        }

        NavigationMainPage navigationMainPage = new NavigationMainPage(getDriver());
        List<String> mainMenuLinks = navigationMainPage.getMainNavigationLinks();
        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = mainMenuLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescMainMainMenuLink : shortMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescMainMainMenuLink));
            }
        }
        List<String> globalLinksList = mainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescMainMainMenuLink : shortMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Global link %s is not contain short description %s", globalLink, shortDescMainMainMenuLink));
            }
        }
        List<String> externalLinksList = mainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String externalLink : externalLinksList) {
            linkAPIChecks(softAssert, externalLink);
            linkContentChecks(softAssert, externalLink);
        }
        softAssert.assertAll();
    }

    @Test(enabled = true)
    @Description("Check navigation main menu sub links for default language")
    public void navigationMaimMenuSubLinksDefaultLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();
        String currentLanguageMeaning;

        List<String> shortSubMainMenuDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            String nv = langValue.languageValue;
            if (langValue.languageValue.equals(currentLanguage) || langValue.languageValue.equals(LangValue.INDIAN) || langValue.languageValue.equals(LangValue.CHINES)) {
                currentLanguageMeaning = langValue.langMeaning;
                LOGGER.info(String.format("Default language meaning is %s", currentLanguageMeaning));
                continue;
            }
            shortSubMainMenuDescriptionsList.add(langValue.langMeaning);
        }

        NavigationMainPage navigationMainPage = new NavigationMainPage(getDriver());
        List<String> subMainMenuLinks = navigationMainPage.getMainNavigationSubLinks();
        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = subMainMenuLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescSubMainMenuLink : shortSubMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescSubMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescSubMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescSubMainMenuLink));
            }
        }

        List<String> globalLinksList = subMainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescSubMainMenuLink : shortSubMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescSubMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescSubMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(), String.format("Global link %s is not contain short description %s", globalLink, shortDescSubMainMenuLink));
            }
        }

        List<String> externalLinksList = subMainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
        for (String externalLink : externalLinksList) {
            linkAPIChecks(softAssert, externalLink);
            linkContentChecks(softAssert, externalLink);
        }
        softAssert.assertAll();
    }

    @Test(description = "Check navigation header utility links with selected languages", enabled = true)
    public void navigationHeaderUtilityLinksSpecLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();
        List<String> shortHeaderUtilityDescriptionsList = new ArrayList<String>();
        for (LangValue language : LangValue.values()) {
            if (language.languageValue.equals(currentLanguage) | language.languageValue.equals(LangValue.INDIAN.languageValue))
                continue;
            String currentLangShortDesc = language.langMeaning;
            LOGGER.info(String.format("Next language for set: %s", language.languageValue));
            homePage.setLanguage(language.languageValue);
            String ma = homePage.getLanguageText();

            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.languageValue), String.format("Language %S doesn't set", ma));
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.languageValue.equals(currentLanguage) || languageMeaning.languageValue.equals(LangValue.INDIAN.languageValue) || languageMeaning.languageValue.equals((LangValue.ENGLISH.languageValue)))
                    continue;
                shortHeaderUtilityDescriptionsList.add(languageMeaning.langMeaning);
            }
            currentLanguage = ma;
            NavigationHeaderUtilityPage navigationHeaderUtilityPage = new NavigationHeaderUtilityPage(getDriver());
            List<String> utilityHeaderLinks = navigationHeaderUtilityPage.getUtilityLinks();
            utilityHeaderLinks.add(navigationHeaderUtilityPage.getLoginLink());
            String localisationFiltMean = String.format(".com/%s/", currentLangShortDesc);
            String globalFiltMean = "nutanix.";
            List<String> localisationLinksList = utilityHeaderLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
            for (String localsationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localsationLink);
                linkContentChecks(softAssert, localsationLink);
            }

            List<String> globalLinksList = utilityHeaderLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);
                for (String shortDesc : shortHeaderUtilityDescriptionsList) {
                    String patWord = ".com/" + shortDesc;
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(), String.format("Link %s contains short description %s", globalLink, shortDesc));
                }
            }
            shortHeaderUtilityDescriptionsList.clear();

            List<String> externalLinksList = utilityHeaderLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
            for (String externalLink : externalLinksList) {
                linkAPIChecks(softAssert, externalLink);
                linkContentChecks(softAssert, externalLink);
            }
        }
        softAssert.assertAll();
    }

    @Test(description = "Check main navigation links with selected languages", enabled = true)
    public void navigationMainLinksSpecLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();

        List<String> shortMainMenuDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue language : LangValue.values()) {
            if (language.languageValue.equals(currentLanguage) || language.languageValue.equals(LangValue.INDIAN.languageValue))
                continue;
            LOGGER.info(String.format("Next language for set: %s", language.languageValue));
            homePage.setLanguage(language.languageValue);
            String ma = homePage.getLanguageText();
            String shortDescrSelectedLanguage = language.langMeaning;
            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.languageValue), String.format("Language %S doesn't set", ma));
            currentLanguage = ma;
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.languageValue.equals(currentLanguage) || languageMeaning.languageValue.equals(LangValue.INDIAN.languageValue) || languageMeaning.languageValue.equals((LangValue.ENGLISH.languageValue)))
                    continue;
                shortMainMenuDescriptionsList.add(languageMeaning.langMeaning);
            }

            NavigationMainPage navigationMainPage = new NavigationMainPage(getDriver());
            List<String> mainMenuLinks = navigationMainPage.getMainNavigationLinks();

            String localisationFiltMean = String.format(".com/%s/", shortDescrSelectedLanguage);
            String globalFiltMean = "nutanix.";

            List<String> localisationLinksList = mainMenuLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
            for (String localisationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localisationLink);
                linkContentChecks(softAssert, localisationLink);
            }
            List<String> globalLinksList = mainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);
                for (String shortDesc : shortMainMenuDescriptionsList) {
                    LOGGER.info(String.format("Checking short description: %S", shortDesc));
                    String patWord = ".com/" + shortDesc;
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(), String.format("Link %s is not contain short description %s", mainMenuLinks, shortDesc));
                }
            }
            shortMainMenuDescriptionsList.clear();
            List<String> externalLinksList = mainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
            for (String externalLink : externalLinksList) {
                linkAPIChecks(softAssert, externalLink);
                linkContentChecks(softAssert, externalLink);
            }
        }
        softAssert.assertAll();
    }

    @Test(description = "Check sub main navigation links with selected languages", enabled = true)
    public void navigationMainSubLinksSpecLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        homePage.openHomePage(URL);
        String currentLanguage = homePage.getLanguageText();

        List<String> shortMainMenuDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue language : LangValue.values()) {
            if (language.languageValue.equals(currentLanguage) || language.languageValue.equals(LangValue.INDIAN.languageValue))
                continue;
            LOGGER.info(String.format("Next language for set: %s", language.languageValue));
            homePage.setLanguage(language.languageValue);
            String ma = homePage.getLanguageText();
            String shortDescrSelectedLanguage = language.langMeaning;
            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.languageValue), String.format("Language %S doesn't set", ma));
            currentLanguage = ma;
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.languageValue.equals(currentLanguage) || languageMeaning.languageValue.equals(LangValue.INDIAN.languageValue) || languageMeaning.languageValue.equals((LangValue.ENGLISH.languageValue)))
                    continue;
                shortMainMenuDescriptionsList.add(languageMeaning.langMeaning);
            }
            NavigationMainPage navigationMainPage = new NavigationMainPage(getDriver());
            List<String> subMainMenuLinks = navigationMainPage.getMainNavigationSubLinks();

            String localisationFiltMean = String.format(".com/%s/", shortDescrSelectedLanguage);
            String globalFiltMean = "nutanix.";

            List<String> localisationLinksList = subMainMenuLinks.stream().filter(o -> o.contains(localisationFiltMean)).collect(Collectors.toList());
            for (String localisationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localisationLink);
                linkContentChecks(softAssert, localisationLink);
            }

            List<String> globalLinksList = subMainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> x.contains(globalFiltMean)).collect(Collectors.toList());
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);

                for (String shortDesc : shortMainMenuDescriptionsList) {
                    LOGGER.info(String.format("Checking short description: %s", shortDesc));
                    String patWord = String.format(".com/%s/", shortDesc);
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(), String.format("Link %s is not contain short description %s", globalLink, shortDesc));
                }

            }
            List<String> externalLinksList = subMainMenuLinks.stream().filter(o -> !o.contains(localisationFiltMean)).filter(x -> !x.contains(globalFiltMean)).collect(Collectors.toList());
            if (externalLinksList.isEmpty()) {
                LOGGER.info(String.format("External links links is empty, size: %s", externalLinksList.size()));
            } else {
                for (String externalLink : externalLinksList) {
                    linkAPIChecks(softAssert, externalLink);
                    linkContentChecks(softAssert, externalLink);
                }
            }
            shortMainMenuDescriptionsList.clear();
        }
        softAssert.assertAll();
    }


    private void linkAPIChecks(SoftAssert softAssert, String link) {
        LOGGER.info(String.format("API checking link: %s", link));
        Pattern patternApi = Pattern.compile("4\\d{2}");
        ValidatableResponse validatableResponse = given().contentType("application/json").when().get(link).then();
        String nz = validatableResponse.extract().statusLine();
        Matcher matcherApi = patternApi.matcher(validatableResponse.extract().statusLine());
        softAssert.assertFalse(matcherApi.find(), String.format("Status code line: %s. Link is not valid: %s", nz, link));
    }

    private void linkContentChecks(SoftAssert softAssert, String link) {
        LOGGER.info(String.format("Content checking link: %s", link));
        Matcher contextMatcher = contentPattern.matcher(link);
        softAssert.assertFalse(contextMatcher.find(), String.format("Link has context structure %s", link));
    }
}

