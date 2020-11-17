package com.amnapp.defsdemo;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingsPreferences{
    public static SettingsPreferences settingsPreferences = new SettingsPreferences();

    private int readerTextSizePosition;
    private boolean isDarkTheme;
    private int webAddressPosition;

    private static final boolean DARK_THEME = true;
    private static final boolean WHITE_THEME =false;
    public static final String PREFERENCE_KEY = "settingPref";
    public static final String READER_TEXT_SIZE_KEY = "rts";
    public static final String DARK_THEME_KEY = "dt";
    public static final String WEB_ADDRESS_KEY = "wa";
    //public static String[] readerTextArray = {"최대","크게","보통","작게","최소"};
    public static int[] readerTextSizeArray={40,35,25,20,15};
    public static String[] webAddressArray = {"https://endic.naver.com/search.nhn?searchOption=all&query=","https://zh.dict.naver.com/#/search?query=","https://ja.dict.naver.com/#/search?query=","https://ko.dict.naver.com/#/search?query=","https://translate.google.com/?hl=ko&sl=auto&tl=ko&text="};


    public static SettingsPreferences getInstance() {
        return settingsPreferences;
    }

    public static void setInstance(SettingsPreferences pref) {
        settingsPreferences = pref;
    }

    public int getReaderTextSizePosition() {
        return readerTextSizePosition;
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    public int getWebAddressPosition() {
        return webAddressPosition;
    }

    public void setReaderTextSizePosition(int readerTextSizePosition) {
        this.readerTextSizePosition = readerTextSizePosition;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.isDarkTheme = darkTheme;
    }

    public void setWebAddressPosition(int webAddressPosition) {
        this.webAddressPosition = webAddressPosition;
    }

    public SettingsPreferences() {

    }

    public SettingsPreferences(int readerTextSizePosition, boolean isDarkTheme, int webAddressPosition) {
        this.readerTextSizePosition = readerTextSizePosition;
        this.isDarkTheme = isDarkTheme;
        this.webAddressPosition = webAddressPosition;
    }
}
