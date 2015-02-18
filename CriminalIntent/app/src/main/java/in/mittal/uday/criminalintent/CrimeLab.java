package in.mittal.uday.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by uday on 11/02/15.
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;
    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    //Private Constructor (Singleton)
    private CrimeLab(Context appContext){

        //AppContext is needed by the singleton to start
        //activities, access project resources,
        //find application's private storage etc
        mAppContext= appContext;

        mCrimes = new ArrayList<Crime>();

        //Populating the crime list with random data
        for(int i=0; i<100; i++){
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i%2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c){
        if(sCrimeLab==null){
            //Setting the context as application context because
            //c could be any context - service context, activity context etc
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes(){
        return mCrimes;
    }

    //Utility method
    public Crime getCrime(UUID id){
        for(Crime c: mCrimes){
            if(c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }
}
