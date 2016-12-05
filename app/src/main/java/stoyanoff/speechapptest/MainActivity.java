package stoyanoff.speechapptest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private MediaPlayer mp;
    private final String HELLO = "hello";
    private final String CHAMP = "who is champ";
    private final String WATCHING_YOU = "i am watching you";
    private final String WATCHING_YOU2 = "i'm watching you";
    private final String READY = "ready for what";
    private final String HIS_NAME = "has arrived";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        //getActionBar().hide();
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));

                    if (result.get(0).toLowerCase().contains(HELLO)) {
                        mp = MediaPlayer.create(this, R.raw.hello);
                        mp.start();
                    } else if (result.get(0).toLowerCase().contains(HIS_NAME)) {
                        mp = MediaPlayer.create(this, R.raw.hisname);
                        mp.start();

                    } else if (result.get(0).toLowerCase().contains(WATCHING_YOU) || result.get(0).toLowerCase().contains(WATCHING_YOU2)) {
                        mp = MediaPlayer.create(this, R.raw.watchingyou);
                        mp.start();

                    } else if (result.get(0).toLowerCase().contains(CHAMP)) {
                        mp = MediaPlayer.create(this, R.raw.champ);
                        mp.start();

                    } else if (result.get(0).toLowerCase().contains(READY)) {
                        mp = MediaPlayer.create(this, R.raw.ready);
                        mp.start();

                    }

                }
            }
            break;
        }

    }
}
