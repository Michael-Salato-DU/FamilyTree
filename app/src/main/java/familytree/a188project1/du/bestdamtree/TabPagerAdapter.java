//Tess Julien
package familytree.a188project1.du.bestdamtree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TreeListFragment famList = new TreeListFragment();
                return famList;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}