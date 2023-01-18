package utils;

public enum LangValue {
    ENGLISH("United States (English)", ""),
    DEUTSCH("Deutschland (Deutsch)", "de"),
    SPAIN("España (Español)", "es"),
    JAPAN("日本 (日本語)","jp"),
    KOREAN("대한민국 (한국어)","kr"),
    CHINES("中国 (简体中文)","cn"),
    LATIN_SPAIN("Latinoamérica (Español)","mx"),
    ITALIAN("Italia (Italiano)","it"),
    BRITISH_ENG("United Kingdom (English)","uk"),
    AUSTRALIAN("Australia (English)","au"),
    BRASIL("Brasil (Português)","br"),
    CHINES_TRAD("台灣 (繁體中文)","tw"),
    INDIAN("India","in"),
    ASIA_PACIFIC_ENGLISH("Asia Pacific (English)","sg");
    public final String languageValue;
    public final String langMeaning;
    LangValue(String languageValue, String langMeaning) {
        this.languageValue = languageValue;
        this.langMeaning = langMeaning;
    }
}
