package in.mittal.uday.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by uday on 11/02/15.
 * mId: Crime Identifier
 * mTitle: Crime Description
 * mDate: Date of Crime
 * mSolved: Whether the crime has been solved or not
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString(){
        return mTitle;
    }
}
