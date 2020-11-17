package com.amnapp.defsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

    Spinner mReaderTextSizeSpinner;
    Switch mDarkThemeSwitch;
    Spinner mWebAddressSpinner;
    TextView mApplyButton;
    SettingsPreferences mSettingPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        loadPreferences();
        initUI(view);


        return view;
    }

    private void loadPreferences() {
        SharedPreferences pref = getActivity().getSharedPreferences(SettingsPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);//sharedpref에 저장된 설정 데이터를 가져온다
        int readerTextPosition = pref.getInt(SettingsPreferences.READER_TEXT_SIZE_KEY, 2);
        boolean isDarkTheme = pref.getBoolean(SettingsPreferences.DARK_THEME_KEY, false);
        int webAddressPosition = pref.getInt(SettingsPreferences.WEB_ADDRESS_KEY,0);
        mSettingPreferences = new SettingsPreferences(readerTextPosition,isDarkTheme,webAddressPosition);
    }

    private void initUI(View view) {
        mReaderTextSizeSpinner=view.findViewById(R.id.sReaderTextSizeSpinner);
        mDarkThemeSwitch=view.findViewById(R.id.sDarkThemeSwitch);
        mWebAddressSpinner=view.findViewById(R.id.sChooseAddressSpinner);
        mApplyButton=view.findViewById(R.id.sApplyButton);
        mReaderTextSizeSpinner.setSelection(mSettingPreferences.getReaderTextSizePosition());//초기 위치 설정
        mDarkThemeSwitch.setChecked(mSettingPreferences.isDarkTheme());//초기 위치 설정
        mWebAddressSpinner.setSelection(mSettingPreferences.getWebAddressPosition());//초기 위치 설정

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingPreferences.setReaderTextSizePosition(mReaderTextSizeSpinner.getSelectedItemPosition());//현재 스피너의 상태를 객체에 저장
                mSettingPreferences.setDarkTheme(mDarkThemeSwitch.isChecked());
                mSettingPreferences.setWebAddressPosition(mWebAddressSpinner.getSelectedItemPosition());
                SettingsPreferences.setInstance(mSettingPreferences);//상태를 저장한 객체를 넘겨준다
                SharedPreferences pref = getActivity().getSharedPreferences(SettingsPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);//sharedpref에 상태를 저장
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(SettingsPreferences.READER_TEXT_SIZE_KEY,mReaderTextSizeSpinner.getSelectedItemPosition());
                editor.putBoolean(SettingsPreferences.DARK_THEME_KEY,mDarkThemeSwitch.isChecked());
                editor.putInt(SettingsPreferences.WEB_ADDRESS_KEY,mWebAddressSpinner.getSelectedItemPosition());
                editor.apply();
                Toast.makeText(getContext(), "설정이 적용되었습니다", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });


    }
}