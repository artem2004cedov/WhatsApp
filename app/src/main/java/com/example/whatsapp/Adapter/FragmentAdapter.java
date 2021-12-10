package com.example.whatsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.Fragments.CallsFragment;
import com.example.whatsapp.Fragments.CathFragment;
import com.example.whatsapp.Fragments.StatusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // создаем новые фрагменты по позиции
        switch (position) {
            case 0:
                return new CathFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallsFragment();
            default:
                return new CathFragment();
        }
    }

    // количетво фрагментов
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // изначальное значение
        String title = null;
        if (position == 0) {
            // присваевания названия
            title = "чаты";
        }
        if (position == 1) {
            title = "статус";
        }
        if (position == 2) {
            title = "звонки";
        }
        return title;
    }
}
