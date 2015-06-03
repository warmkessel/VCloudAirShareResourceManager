package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Language {
  ENGLISH("en", "English", "en-US", 1), AFRIKAANS("af", "Afrikaans"), ALBANIAN("am", "Albanian"), AMHARIC(
      "af", "Amharic"), ARABIC("ar", "Arabic"), ARMENIAN("hy", "Armenian"), ASSAMESE("as",
      "Assamese"), AZERI("az", "Azeri"), BASQUE("eu", "Basque"), BELARUSIAN("be", "Belarusian"), BENGALI(
      "bn", "Bengali"), BOSNIAN("bs", "Bosnian"), BULGARIAN("bg", "Bulgarian"), BURMESE("my",
      "Burmese"), CATALAN("ca", "Catalan"), CHINESE("zh", "Chinese"), CROATIAN("hr", "Croatian"), CZECH(
      "cs", "Czech"), DANISH("da", "Danish"), DIVEHI("dv", "Divehi"), DUTCH("nl", "Dutch"), ESTONIAN(
      "et", "Estonian"), FAROESE("fo", "Faroese"), FARSI("fa", "Farsi"), FINNISH("fi", "Finnish"), FRENCH(
      "fr", "French"), GAELIC("gd", "Gaelic"), GALICIAN("gl", "Galician"), GEORGIAN("ka",
      "Georgian"), GERMAN("de", "German"), GREEK("el", "Greek"), GUARANI("gu", "Guarani"), GUJARATI(
      "af", "Gujarati"), HEBREW("he", "Hebrew"), HINDI("hi", "Hindi"), HUNGARIAN("hu", "Hungarian"), ICELANDIC(
      "is", "Icelandic"), INDONESIAN("id", "Indonesian"), ITALIAN("it", "Italian"), JAPANESE("ja",
      "Japanese", 1), KANNADO("kn", "Kannada"), KASHMIRI("ks", "Kashmiri"), KAZAKH("kk", "Kazakh"), KHMER(
      "km", "Khmer"), KOREAN("ko", "Korean"), LAO("Lao", "Lao"), LATVIAN("lv", "Latvian"), LITHUANIAN(
      "lt", "Lithuanian"), MALAY("ms", "Malay"), MALAYALAM("Malayalam", "Malayalam"), MALTESE("mt",
      "Maltese"), MAORI("mi", "Maori"), MARATHI("mr", "Marathi"), MONGOLIAN("mn", "Mongolian"), MEPAL(
      "ne", "Nepali"), NORWEGIAN("nn", "Norwegian"), ORIYA("or", "Oriya"), POLISH("pl", "Polish"), PORUGUESE(
      "pt", "Portuguese"), PUNJABI("Punjabi", "Punjabi"), ROMANIAN("ro", "Romanian"), RUSSIAN("ru",
      "Russian"), SANSKRIT("sa", "Sanskrit"), SERBIAN("tn", "Serbian"), SETSUANA("af", "Setsuana"), SINDHI(
      "sd", "Sindhi"), SINHALA("si", "Sinhala"), SLOVAK("sk", "Slovak"), SLOVENIAN("sl",
      "Slovenian"), SOMALI("af", "Somali"), SORBIAN("sb", "Sorbian"), SPANISH("es", "Spanish"), SWAHILI(
      "sw", "Swahili"), SWEDISH("sv", "Swedish"), TAJIK("tg", "Tajik"), TAMIL("ta", "Tamil"), TATAR(
      "tt", "Tatar"), TELUGU("te", "Telugu"), THAI("th", "Thai"), TIBERTAN("bo", "Tibetan"), TSONGA(
      "ts", "Tsonga"), TURKISH("tr", "Turkish"), TURKEMEN("tk", "Turkmen"), UKRAINIAN("uk",
      "Ukrainian"), URDU("ur", "Urdu"), UZBEK("uz", "Uzbek"), VIETNAMESE("vi", "Vietnamese"), WELSH(
      "cy", "Welsh"), XHOSA("xh", "Xhosa"), YIDDISH("yi", "Yiddish"), ZULU("zu", "Zulu");

  public static Language fromId(String s) {
    if (s != null) {
      for (Language language : values()) {
        if (s.equals(language.getId()))
          return language;
      }
    }
    return getDefault();
  }

  public static Language fromString(String text) {
    if (text != null) {
      for (Language b : Language.values()) {
        if (text.equalsIgnoreCase(b.getId())) {
          return b;
        }
        for (int x = 0; x < b.getAltArray().length; x++) {
          if (text.equalsIgnoreCase(b.getAltArray()[x])) {
            return b;
          }
        }
      }
    }
    return getDefault();
  }

  public static Language getDefault() {
    return ENGLISH;
  }

  public static List<String> idValues() {
    List<String> ids = new ArrayList<String>();

    for (Language explicit : values()) {
      ids.add(explicit.getId());
    }
    return ids;
  }

  private String value, id, alt;
  private int tier;

  private String[] alts;

  Language(String id, String value) {
    this(id, value, "", 0);
  }

  Language(String id, String value, int tier) {
    this(id, value, "", tier);
  }

  Language(String id, String value, String alt) {
    this(id, value, alt, 0);
  }

  Language(String id, String value, String alt, int tier) {
    this.id = id;
    this.value = value;
    this.alt = alt;
    this.tier = tier;
  }

  public String getAlt() {
    if (null != this.alt && 0 < this.alt.length()) {
      return this.alt;
    } else {
      return getId();
    }
  }

  public String[] getAltArray() {
    if (null == alts) {
      alts = alt.split(",");
    }
    return alts;
  }

  public String getId() {
    return this.id;
  }

  public int getTier() {
    return this.tier;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
