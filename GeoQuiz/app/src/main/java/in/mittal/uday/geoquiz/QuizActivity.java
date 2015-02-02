package in.mittal.uday.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Launcher Activity
 */
public class QuizActivity extends Activity {

    //Constant Declarations
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER ="cheater";

    //Button Declarations
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;

    //Class Variable Declarations
    private boolean mIsCheater;
    private TextView mQuestionTextView;
    private List<UserResponse> mUserResponseList = new ArrayList<UserResponse>();
    private int mCurrentIndex = 0;

    //QuestionBank Initialization
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_oceans, true),
    };


    /**
     * Method checks whether the user cheated in a question or not
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data==null){
            return;
        }
        mIsCheater=data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,false);
    }

    /**
     * Method saves current question and cheating status of the user
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            restoreSavedInstance(savedInstanceState);
        }
        setContentView(R.layout.activity_quiz);

        wireViewObjects();
    }

    private void restoreSavedInstance(Bundle savedInstanceState){
        mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
    }


    private void wireViewObjects(){

        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CheatActivity
                Intent i = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,answerIsTrue);
                startActivityForResult(i,0);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mIsCheater=false;
              //  mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mCurrentIndex = mCurrentIndex + 1;
                if(mCurrentIndex > mQuestionBank.length){
                    //End Quiz
                }
                else {
                    updateQuestion();
                }
            }
        });
    }

    private void getScoreActivityIntent(){
        Intent i = new Intent(QuizActivity.this, ScoreActivity.class);
        i.putExtra(ScoreActivity.EXTRA_USER_RESPONSE, mUserResponseList);
    }

    /**
     * Method updates the current question
     */
    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    /**
     * Method checks for the submitted answer and raises a
     * toast accordingly
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResId = 0;
        UserResponse userResponse = new UserResponse();
        userResponse.setQuestion(mQuestionBank[mCurrentIndex]);
        if(mIsCheater){
            messageResId=R.string.judgement_toast;
            userResponse.setResponse(UserResponse.ResponseType.CHEATED);
        }
        else{
            if(userPressedTrue==answerIsTrue){
                messageResId = R.string.correct_toast;
                userResponse.setResponse(UserResponse.ResponseType.CORRECT);
            }
            else{
                messageResId = R.string.incorrect_toast;
                userResponse.setResponse(UserResponse.ResponseType.INCORRECT);
            }
        }
        mUserResponseList.add(userResponse);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method calculates the user score given the mUserResponseList
     * @return
     */
    public float calculateScore(){
        float userScore=0;
        for(UserResponse userResponse: mUserResponseList){
            if(userResponse.getResponse().equals(UserResponse.ResponseType.CHEATED)){
                userScore += UserResponse.ResponseType.CHEATED.getScoreValue();
            }
            if(userResponse.getResponse().equals(UserResponse.ResponseType.CORRECT)){
                userScore += UserResponse.ResponseType.CORRECT.getScoreValue();
            }
            if(userResponse.getResponse().equals(UserResponse.ResponseType.INCORRECT)){
                userScore += UserResponse.ResponseType.INCORRECT.getScoreValue();
            }
        }
        return userScore;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
