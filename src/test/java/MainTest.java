import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.LangValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static utils.LangValue.*;

public class MainTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(MainTest.class);
    private static final Pattern contentPattern = Pattern.compile("/content/nutanix/");

    @Test(description = "Check languages links", enabled = true)
    public void languagesLinksTest() {
        SoftAssert softAssert = new SoftAssert();
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String currentLanguage = languageChooser.getSelectedLanguageText();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        Map<String, String> languagesLinksMap = languageChooser.getLanguagesMap();
        for (Map.Entry<String, String> languageLink : languagesLinksMap.entrySet()) {
            String examLink = languageLink.getValue();
            Matcher contextMatcher = contentPattern.matcher(examLink);
            softAssert.assertFalse(contextMatcher.find(), String.format("Link has context structure %s", examLink));
        }

        for (LangValue langValue : LangValue.values()) {
            if (langValue.name.equals(currentLanguage) || langValue.equals(INDIAN)) {
                continue;
            }
            if (langValue.equals(CHINESE)) {
                String link = languagesLinksMap.get(langValue.name);
                LOGGER.info(String.format("Link value : %s", link));
                linkAPIChecks(softAssert, link);
                String patt = "." + langValue.code;
                Pattern pattern = Pattern.compile(patt);
                Matcher linkStructMatcher = pattern.matcher(link);
                softAssert.assertTrue(linkStructMatcher.find(),
                        String.format("Link for Chines %s is not contain short description %s", link, patt));
                continue;
            }
            String langLink = languagesLinksMap.get(langValue.name);
            LOGGER.info(String.format("Link value : %s", langLink));
            linkAPIChecks(softAssert, langLink);
            String mc = langValue.code;
            String patWord = ".com/" + mc;
            Pattern pattern = Pattern.compile(patWord);
            Matcher linkStructMatcher = pattern.matcher(langLink);
            softAssert.assertTrue(linkStructMatcher.find(),
                    String.format("Link %s is not contain short description %s", langLink, mc));
        }

        softAssert.assertAll();
    }

    @Test(description = "Check navigation header utility links for default language", enabled = true)
    public void navigationHeaderUtilityLinksDefaultLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String currentLanguage = languageChooser.getSelectedLanguageText();
        String currentLanguageMeaning;
        List<String> shortDescriptionsList = new ArrayList<>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            if (langValue.name.equals(currentLanguage) || langValue.equals(INDIAN) || langValue.equals(CHINESE)) {
                currentLanguageMeaning = langValue.code;
                LOGGER.info(String.format("Language meaning is %s", currentLanguageMeaning));
                continue;
            }
            shortDescriptionsList.add(langValue.code);
        }
        NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
        LoginLink loginLink = new LoginLink(getDriver());
        List<String> utilityHeaderLinks = new ArrayList<>(navigationUtilityHeader.getUtilityHeaderLinks());
        utilityHeaderLinks.add(loginLink.getLoginUrl());

        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = utilityHeaderLinks
                .stream()
                .filter(o -> o.contains(localisationFiltMean))
                .toList();
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescMainMainMenuLink : shortDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescMainMainMenuLink));
            }
        }

        List<String> globalLinksList = utilityHeaderLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> x.contains(globalFiltMean))
                .toList();
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescMainMainMenuLink : shortDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Localisation link %s is not contain short description %s", globalLink, shortDescMainMainMenuLink));
            }
        }

        List<String> externalLinksList = utilityHeaderLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> !x.contains(globalFiltMean))
                .toList();
        for (String externalLink : externalLinksList) {
            linkAPIChecks(softAssert, externalLink);
            linkContentChecks(softAssert, externalLink);
        }
        softAssert.assertAll();
    }

    @Test(description = "Check navigation main menu links for default language", enabled = true)
    public void navigationMaimMenuLinksDefaultLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String currentLanguage = languageChooser.getSelectedLanguageText();

        List<String> shortMainMenuDescriptionsList = new ArrayList<>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            if (langValue.name.equals(currentLanguage)) {
                String currentLanguageCode = langValue.code;
                LOGGER.info(String.format("Default language code is [%s]", currentLanguageCode));
                continue;
            }
            shortMainMenuDescriptionsList.add(langValue.code);
        }

        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        List<String> mainMenuLinks = mainNavigationHeader.getNavigationTabLinks();
        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = mainMenuLinks
                .stream()
                .filter(o -> o.contains(localisationFiltMean))
                .toList();
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescMainMainMenuLink : shortMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescMainMainMenuLink));
            }
        }
        List<String> globalLinksList = mainMenuLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> x.contains(globalFiltMean))
                .toList();
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescMainMainMenuLink : shortMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescMainMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescMainMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Global link %s is not contain short description %s", globalLink, shortDescMainMainMenuLink));
            }
        }
        List<String> externalLinksList = mainMenuLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> !x.contains(globalFiltMean))
                .toList();
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
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String currentLanguage = languageChooser.getSelectedLanguageText();
        String currentLanguageMeaning;

        List<String> shortSubMainMenuDescriptionsList = new ArrayList<>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue langValue : LangValue.values()) {
            if (langValue.name.equals(currentLanguage) || langValue.equals(INDIAN) || langValue.equals(CHINESE)) {
                currentLanguageMeaning = langValue.code;
                LOGGER.info(String.format("Default language meaning is %s", currentLanguageMeaning));
                continue;
            }
            shortSubMainMenuDescriptionsList.add(langValue.code);
        }

        MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
        List<String> mainMenuSubLinks = mainNavigationHeader.getNavigationTabNames()
                .stream()
                .map(mainNavigationHeader::getNavigationTabSubLinks)
                .flatMap(Collection::stream)
                .toList();
        String localisationFiltMean = "nutanix.com";
        String globalFiltMean = "nutanix.";
        List<String> localisationLinksList = mainMenuSubLinks
                .stream()
                .filter(o -> o.contains(localisationFiltMean))
                .toList();
        for (String localisationLink : localisationLinksList) {
            linkAPIChecks(softAssert, localisationLink);
            linkContentChecks(softAssert, localisationLink);
            for (String shortDescSubMainMenuLink : shortSubMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescSubMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescSubMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(localisationLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Localisation link %s is not contain short description %s", localisationLink, shortDescSubMainMenuLink));
            }
        }

        List<String> globalLinksList = mainMenuSubLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> x.contains(globalFiltMean))
                .toList();
        for (String globalLink : globalLinksList) {
            linkAPIChecks(softAssert, globalLink);
            linkContentChecks(softAssert, globalLink);
            for (String shortDescSubMainMenuLink : shortSubMainMenuDescriptionsList) {
                LOGGER.info(String.format("Checking short description: %S", shortDescSubMainMenuLink));
                String patWord = String.format(".com/%s/", shortDescSubMainMenuLink);
                Pattern pattern = Pattern.compile(patWord);
                Matcher linkStructMatcher = pattern.matcher(globalLink);
                softAssert.assertFalse(linkStructMatcher.find(),
                        String.format("Global link %s is not contain short description %s", globalLink, shortDescSubMainMenuLink));
            }
        }

        List<String> externalLinksList = mainMenuSubLinks
                .stream()
                .filter(o -> !o.contains(localisationFiltMean))
                .filter(x -> !x.contains(globalFiltMean))
                .toList();
        for (String externalLink : externalLinksList) {
            linkAPIChecks(softAssert, externalLink);
            linkContentChecks(softAssert, externalLink);
        }
        softAssert.assertAll();
    }

    @Test(description = "Check navigation header utility links with selected languages", enabled = true)
    public void navigationHeaderUtilityLinksSpecLanguageTest() {
        SoftAssert softAssert = new SoftAssert();
        getDriver().get(URL);
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        String currentLanguage = languageChooser.getSelectedLanguageText();
        List<String> shortHeaderUtilityDescriptionsList = new ArrayList<>();
        for (LangValue language : LangValue.values()) {
            if (language.name.equals(currentLanguage) | language.name.equals(INDIAN.name))
                continue;
            String currentLangShortDesc = language.code;
            LOGGER.info(String.format("Next language for set: %s", language.name));
            languageChooser.selectLanguage(language.name);
            String ma = languageChooser.getSelectedLanguageText();

            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.name), String.format("Language %S doesn't set", ma));
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.name.equals(currentLanguage) || languageMeaning.equals(INDIAN) || languageMeaning.equals((ENGLISH)))
                    continue;
                shortHeaderUtilityDescriptionsList.add(languageMeaning.code);
            }
            currentLanguage = ma;
            NavigationUtilityHeader navigationUtilityHeader = new NavigationUtilityHeader(getDriver());
            LoginLink loginLink = new LoginLink(getDriver());
            List<String> utilityHeaderLinks = new ArrayList<>(navigationUtilityHeader.getUtilityHeaderLinks());
            utilityHeaderLinks.add(loginLink.getLoginUrl());
            String localisationFiltMean = String.format(".com/%s/", currentLangShortDesc);
            String globalFiltMean = "nutanix.";
            List<String> localisationLinksList = utilityHeaderLinks
                    .stream()
                    .filter(o -> o.contains(localisationFiltMean))
                    .toList();
            for (String localsationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localsationLink);
                linkContentChecks(softAssert, localsationLink);
            }

            List<String> globalLinksList = utilityHeaderLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> x.contains(globalFiltMean))
                    .toList();
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);
                for (String shortDesc : shortHeaderUtilityDescriptionsList) {
                    String patWord = ".com/" + shortDesc;
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(),
                            String.format("Link %s contains short description %s", globalLink, shortDesc));
                }
            }
            shortHeaderUtilityDescriptionsList.clear();

            List<String> externalLinksList = utilityHeaderLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> !x.contains(globalFiltMean))
                    .toList();
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
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        getDriver().get(URL);
        String currentLanguage = languageChooser.getSelectedLanguageText();

        List<String> shortMainMenuDescriptionsList = new ArrayList<>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue language : LangValue.values()) {
            if (language.name.equals(currentLanguage) || language.name.equals(INDIAN.name))
                continue;
            LOGGER.info(String.format("Next language for set: %s", language.name));
            languageChooser.selectLanguage(language.name);
            String ma = languageChooser.getSelectedLanguageText();
            String shortDescrSelectedLanguage = language.code;
            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.name), String.format("Language %S doesn't set", ma));
            currentLanguage = ma;
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.name.equals(currentLanguage) || languageMeaning.equals(INDIAN) || languageMeaning.equals((ENGLISH)))
                    continue;
                shortMainMenuDescriptionsList.add(languageMeaning.code);
            }

            MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
            List<String> mainMenuLinks = mainNavigationHeader.getNavigationTabNames();

            String localisationFiltMean = String.format(".com/%s/", shortDescrSelectedLanguage);
            String globalFiltMean = "nutanix.";

            List<String> localisationLinksList = mainMenuLinks
                    .stream()
                    .filter(o -> o.contains(localisationFiltMean))
                    .toList();
            for (String localisationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localisationLink);
                linkContentChecks(softAssert, localisationLink);
            }
            List<String> globalLinksList = mainMenuLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> x.contains(globalFiltMean))
                    .toList();
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);
                for (String shortDesc : shortMainMenuDescriptionsList) {
                    LOGGER.info(String.format("Checking short description: %S", shortDesc));
                    String patWord = ".com/" + shortDesc;
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(),
                            String.format("Link %s is not contain short description %s", mainMenuLinks, shortDesc));
                }
            }
            shortMainMenuDescriptionsList.clear();
            List<String> externalLinksList = mainMenuLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> !x.contains(globalFiltMean))
                    .toList();
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
        LanguageChooser languageChooser = new LanguageChooser(getDriver());
        getDriver().get(URL);
        String currentLanguage = languageChooser.getSelectedLanguageText();

        List<String> shortMainMenuDescriptionsList = new ArrayList<String>();
        LOGGER.info(String.format("Current language is %s", currentLanguage));
        for (LangValue language : LangValue.values()) {
            if (language.name.equals(currentLanguage) || language.name.equals(INDIAN.name))
                continue;
            LOGGER.info(String.format("Next language for set: %s", language.name));
            languageChooser.selectLanguage(language.name);
            String ma = languageChooser.getSelectedLanguageText();
            String shortDescrSelectedLanguage = language.code;
            LOGGER.info(String.format("Updated language value: %s", ma));
            softAssert.assertTrue(ma.contains(language.name), String.format("Language %S doesn't set", ma));
            currentLanguage = ma;
            for (LangValue languageMeaning : LangValue.values()) {
                if (languageMeaning.name.equals(currentLanguage) || languageMeaning.equals(INDIAN) || languageMeaning.equals(ENGLISH))
                    continue;
                shortMainMenuDescriptionsList.add(languageMeaning.code);
            }
            MainNavigationHeader mainNavigationHeader = new MainNavigationHeader(getDriver());
            List<String> navigationTabNames = mainNavigationHeader.getNavigationTabNames();
            List<String> mainMenuSubLinks = navigationTabNames
                    .stream()
                    .map(mainNavigationHeader::getNavigationTabSubLinks)
                    .flatMap(Collection::stream)
                    .toList();

            String localisationFiltMean = String.format(".com/%s/", shortDescrSelectedLanguage);
            String globalFiltMean = "nutanix.";

            List<String> localisationLinksList = mainMenuSubLinks
                    .stream()
                    .filter(o -> o.contains(localisationFiltMean))
                    .toList();
            for (String localisationLink : localisationLinksList) {
                linkAPIChecks(softAssert, localisationLink);
                linkContentChecks(softAssert, localisationLink);
            }

            List<String> globalLinksList = mainMenuSubLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> x.contains(globalFiltMean))
                    .toList();
            for (String globalLink : globalLinksList) {
                linkAPIChecks(softAssert, globalLink);
                linkContentChecks(softAssert, globalLink);

                for (String shortDesc : shortMainMenuDescriptionsList) {
                    LOGGER.info(String.format("Checking short description: %s", shortDesc));
                    String patWord = String.format(".com/%s/", shortDesc);
                    Pattern pattern = Pattern.compile(patWord);
                    Matcher linkStructMatcher = pattern.matcher(globalLink);
                    softAssert.assertFalse(linkStructMatcher.find(),
                            String.format("Link %s is not contain short description %s", globalLink, shortDesc));
                }

            }
            List<String> externalLinksList = mainMenuSubLinks
                    .stream()
                    .filter(o -> !o.contains(localisationFiltMean))
                    .filter(x -> !x.contains(globalFiltMean))
                    .toList();
            if (externalLinksList.isEmpty()) {
                LOGGER.info("External links links is empty");
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

