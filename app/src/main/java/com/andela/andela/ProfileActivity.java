package com.andela.andela;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 433;
    private static final int GALLERY = 1;
    private static final String IMAGE_DIRECTORY = "image";
    ImageView profile,moreI,photo;
    TextView track, name, country, mail, phone, slack;
    Button photos;

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
        photo = findViewById(R.id.photo);
        photos = findViewById(R.id.viewPhotos);
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
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();

            }
        });
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,PhotosActivity.class);
                startActivity(i);
            }
        });
        title();
    }

    private void title() {
        setTitle(name.getText().toString());
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select from gallery", "Take photo"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               switch (i){
                   case 0:
                       choosePhotoFromGallery();
                       break;
                   case 1:
                       takePhotoFromCamera();
                       break;
               }
            }
        });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,GALLERY);
        
    }

    private void takePhotoFromCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==REQUEST_CODE && data!=null){
            Bundle extras = data.getExtras();
            Bitmap p = (Bitmap) extras.get("data");
            profile.setImageBitmap(p);
        }
        if (resultCode == GALLERY){
            if (data!=null){
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contentURI);
                    String path = saveImage(bitmap);
                    profile.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (resultCode==RESULT_CANCELED){
            return;
        }
    }

    private String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG,90,bytes);
        File wallPaperDirectory = new File(Environment.getExternalStorageDirectory()+ IMAGE_DIRECTORY);
        if (!wallPaperDirectory.exists()){
            wallPaperDirectory.mkdirs();
        }try {
            File f = new File(wallPaperDirectory, Calendar.getInstance().getTimeInMillis()+" .jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,new String[]{f.getPath()},
                    new String[]{"image/jpeg"},null);
            fo.close();
            return f.getAbsolutePath();

        }catch (IOException e1){
            e1.printStackTrace();
        }
        return null;
    }
}
