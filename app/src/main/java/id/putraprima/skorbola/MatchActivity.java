package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

public class MatchActivity extends AppCompatActivity {
    private TextView homeText;
    private TextView awayText;
    private TextView homeTextScore;
    private TextView awayTextScore;
    private ImageView homeimg;
    private ImageView awayimg;
    private int homeScore;
    private int awayScore;
    private String win;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        homeText = findViewById(R.id.txt_home);
        awayText = findViewById(R.id.txt_away);
        homeTextScore = findViewById(R.id.score_home);
        awayTextScore = findViewById(R.id.score_away);
        homeimg=findViewById(R.id.home_logo);
        awayimg=findViewById(R.id.away_logo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String home = extras.getString(MainActivity.HOME_KEY);
            String away = extras.getString(MainActivity.AWAY_KEY);
            String homeURL=extras.getString(MainActivity.HOMEIMG_KEY);
            String awayURL=extras.getString(MainActivity.AWAYIMG_KEY);
            homeText.setText(home);
            awayText.setText(away);
            try {
                Bitmap homebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(homeURL));
                Bitmap awaybitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(awayURL));
                homeimg.setImageBitmap(homebitmap);
                awayimg.setImageBitmap(awaybitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
        //2.Tombol add score menambahkan satu angka dari angka 0, setiap kali di tekan
        public void handleHomeScore(View view) {
            homeScore ++;
            homeTextScore.setText(String.valueOf(homeScore));
        }

        public void handleAwayScore(View view) {
            awayScore ++;
            awayTextScore.setText(String.valueOf(awayScore));
        }

    public void handleCekHasil(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_RESULT, win);
        if (homeScore > awayScore){
            win = homeText.getText().toString();
            intent.putExtra(ResultActivity.EXTRA_RESULT,win);
        }
        else if (awayScore > homeScore){
            win = awayText.getText().toString();
            intent.putExtra(ResultActivity.EXTRA_RESULT,win);
        }
        else if (homeScore == awayScore){
            win = "Draw";
            intent.putExtra(ResultActivity.EXTRA_RESULT,win);
        }

        startActivity(intent);
    }
    //3.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang ke ResultActivity, jika seri di kirim text "Draw"

}
