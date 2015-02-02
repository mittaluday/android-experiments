package in.mittal.uday.geoquiz;

import java.io.Serializable;
import java.security.interfaces.RSAKey;

/**
 * Created by uday on 29/01/15.
 *
 * Class stores user's response to each question
 */
public class UserResponse implements Serializable {
    public static enum ResponseType {
        CORRECT(1f), INCORRECT(-1f), CHEATED(-0.5f);

        private float scoreValue;

        ResponseType(float scoreValue){
            this.scoreValue = scoreValue;
        }

        public float getScoreValue(){
            return scoreValue;
        }
    }

    TrueFalse mQuestion;
    ResponseType mResponse;

    public TrueFalse getQuestion() {
        return mQuestion;
    }

    public void setQuestion(TrueFalse question) {
        mQuestion = question;
    }

    public ResponseType getResponse() {
        return mResponse;
    }

    public void setResponse(ResponseType response) {
        mResponse = response;
    }
}
