package com.andela.andela;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    ImageView profile,moreI;
    TextView track, name, country, mail, phone, slack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile = findViewById(R.id.imageProfile);
        name = findViewById(R.id.textName);
        country = findViewById(R.id.textCountry);
        mail = findViewById(R.id.textMail);
        phone = findViewById(R.id.textPhone);
        slack = findViewById(R.id.textSlackName);
        track = findViewById(R.id.textTrack);
        moreI = findViewById(R.id.moreI);
        moreI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.info,null);
                TextView moreInfo = (TextView) mView.findViewById(R.id.moreInfo);
                mBuilder.setView(mView);
                AlertDialog dialog= mBuilder.create();
                dialog.show();

            }
        });
    }
}
