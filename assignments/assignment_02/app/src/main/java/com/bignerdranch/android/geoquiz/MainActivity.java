package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    public static final String EXTRA_MESSAGE = "com.example.geoquiz.MESSAGE";
    private static final int REQUEST_CODE_CHEAT = 1;

    private Question[] mQuestionBank = new Question[]
    {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);
                updateQuestion();
            }
        });
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.w(TAG,"Cheat Button Clicked");

                Intent i = new Intent(MainActivity.this, CheatActivity.class);
                String message = "Hello from MainActivity";
                i.putExtra(EXTRA_MESSAGE, message);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHEAT)
        {
            Log.d(TAG, "Request code: " + requestCode);
            if (null != data && data.getStringExtra("result") != null)
            {
                int messageRedId = R.string.judgment_toast;
                Toast.makeText(this, messageRedId, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue)
        {
            messageResId = R.string.correct_toast;
        }
        else
        {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show();
    }
}