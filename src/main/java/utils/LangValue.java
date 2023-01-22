package utils;

public enum LangValue {
    ENGLISH("United States (English)", "us"),
    DEUTSCH("Deutschland (Deutsch)", "de"),
    FRENCH("France (Français)", "fr"),
    SPANISH("España (Español)", "es"),
    JAPAN("日本 (日本語)","jp"),
    KOREAN("대한민국 (한국어)","kr"),
    CHINESE("中国 (简体中文)","cn"),
    LATIN_SPAIN("Latinoamérica (Español)","mx"),
    ITALIAN("Italia (Italiano)","it"),
    BRITISH_ENG("United Kingdom (English)","uk"),
    AUSTRALIAN("Australia (English)","au"),
    BRAZILIAN("Brasil (Português)","br"),
    CHINESE_TRAD("台灣 (繁體中文)","tw"),
    INDIAN("India","in"),
    ASIA_PACIFIC_ENGLISH("Asia Pacific (English)","sg");
    public final String name;
    public final String code;
    LangValue(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
