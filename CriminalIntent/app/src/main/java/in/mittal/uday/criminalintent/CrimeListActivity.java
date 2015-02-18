package in.mittal.uday.criminalintent;

import android.app.Fragment;

/**
 * Created by uday on 12/02/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
