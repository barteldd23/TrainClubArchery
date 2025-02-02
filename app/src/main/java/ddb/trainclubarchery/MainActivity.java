package ddb.trainclubarchery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnNewGame;
    Button btnScores;
    public static final String TAG = "MainActivity";

    TCADataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initButtons();

        //dataSource = new TCADataSource(this, 0); // 0 => no pre-loaded names
        dataSource = new TCADataSource(this, 1); // 1 => pre-loaded names

        dataSource.open(true); // true means refresh
        //dataSource.open(); // this will open the current database not refreshing.


    }

    private void initButtons()
    {
        btnNewGame = findViewById(R.id.btnNewGame);
        btnScores = findViewById(R.id.btnScores);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewGameActivity.class );
                Log.d(TAG, "onClick: New Game");
                startActivity(intent);
            }
        });

    }
}