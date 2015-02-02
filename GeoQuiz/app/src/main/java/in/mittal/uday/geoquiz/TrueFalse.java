package in.mittal.uday.geoquiz;

/**
 * Created by uday on 30/12/14.
 */
public class TrueFalse {
    /**
     * mQuestion holds the resource ID
     * of the string question
     */
    private int mQuestion;

    /**
     * mTrueQuestion holds whether the question is
     * True or False
     */
    private boolean mTrueQuestion;

    public TrueFalse(int question, boolean trueQuestion){
        mQuestion=question;
        mTrueQuestion=trueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
