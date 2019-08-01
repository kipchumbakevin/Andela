package com.andela.andela;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.andela.andela.object_box.ObjectBox;

import java.util.ArrayList;
import java.util.Queue;

import io.objectbox.Box;
import io.objectbox.query.Query;

public class PhotosActivity extends AppCompatActivity {
    public static ArrayList<Product>mProductArrayList = new ArrayList<>();
    RecyclerView mRecyclerView;
    int position = 78;
    private Box<Product>mProductBox;
    private Query<Product>mProductQuery;
    Button addPhotos;
    ProductListAdapter mProductListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        if (savedInstanceState!=null){
            position = savedInstanceState.getInt("position");
        }
        mRecyclerView = findViewById(R.id.productRecyclerView);
        mProductListAdapter = new ProductListAdapter(PhotosActivity.this,mProductArrayList);
        mRecyclerView.setAdapter(mProductListAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(PhotosActivity.this,2));
        addPhotos = findViewById(R.id.addPhotos);
        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PhotosActivity.this, UploadPhotos.class);
                startActivity(i);
            }
        });
        initObjectBox();
        title();
    }

    private void initObjectBox() {
        mProductBox = ObjectBox.get().boxFor(Product.class);
    }

    private void title() {
        setTitle("My Photos");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position);
    }
}
