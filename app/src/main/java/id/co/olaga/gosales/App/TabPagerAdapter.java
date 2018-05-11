package id.co.olaga.gosales.App;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import id.co.olaga.gosales.tabswipe.berkas;
import id.co.olaga.gosales.tabswipe.foto;
import id.co.olaga.gosales.tabswipe.outlet;
import id.co.olaga.gosales.tabswipe.owner;
import id.co.olaga.gosales.tabswipe.payment;

/**
 * Created by TDEV on 16/03/18.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 5;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return outlet.newInstance();
            case 1:
                return owner.newInstance();
            case 2:
                return payment.newInstance();
            case 3:
                return berkas.newInstance();
            case 4:
                return foto.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return outlet.TITLE;

            case 1:
                return owner.TITLE;

            case 2:
                return payment.TITLE;

            case 3:
                return berkas.TITLE;

            case 4:
                return foto.TITLE;
        }
        return super.getPageTitle(position);
    }

}
