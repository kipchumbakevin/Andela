package com.andela.andela;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.Random;
import io.objectbox.Box;
public class UploadPhotos extends AppCompatActivity {
    private static final int REQUEST_CODE = 433;
    Button upload;
ImageView productImage;
    private File mPhotoFile;
    private Uri photoURI;
    private Product mProduct;
    private Box<Product>mProductBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        upload = findViewById(R.id.upload);
        productImage = findViewById(R.id.product_image);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCameraDialog();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(UploadPhotos.this,"Uploaded",Toast.LENGTH_SHORT).show();
                addPhoto();
            }
        });
        setTitle("Upload");
    }

    private void addPhoto() {
        String picturePath = photoURI.toString();
        mProduct = new Product(picturePath);
        mProductBox.put(this.mProduct);
        finish();
    }

    private void startCameraDialog() {
        ImageView camera,gallery;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(R.layout.view_camera);

        final AlertDialog pp = alertDialogBuilder.create();
        pp.show();
        View view = LayoutInflater.from(UploadPhotos.this).inflate(R.layout.view_camera,null);
        camera = pp.findViewById(R.id.dialog_camera);
        gallery = pp.findViewById(R.id.dialog_gallery);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhotoToFilePathAndRetrieve();
                pp.dismiss();
            }
        });
    }

    private void savePhotoToFilePathAndRetrieve() {
        mPhotoFile = getPhotoFile();
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(UploadPhotos.this,"com.andela.andela.fileprovider",mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        List<ResolveInfo>cameraActivities = UploadPhotos.this.getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity:cameraActivities){
            UploadPhotos.this.grantUriPermission(activity.activityInfo.packageName,uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(captureImage,REQUEST_CODE);
        }
    }

    public File getPhotoFile() {
        File filesDir = getFilesDir();
        return new File(filesDir, getPhotoFilename());
    }

    private String getPhotoFilename() {
        return "IMG_" + new Random().nextDouble() + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        else if (requestCode == REQUEST_CODE){
            Uri uri = FileProvider.getUriForFile(UploadPhotos.this,"com.andela.andela.fileprovider",mPhotoFile);
            photoURI = uri;
            revokeUriPermission(uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
    }

    private void updatePhotoView() {
        if (photoURI!=null){
            Glide.with(this).load(photoURI).into(productImage);
        }
    }
}
