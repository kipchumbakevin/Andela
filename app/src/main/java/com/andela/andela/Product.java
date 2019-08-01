package com.andela.andela;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by KIPCHU on 01/08/2019.
 */

@Entity public class Product {
    @Id long id;
    String imageUri;
    private String image;

    public Product(String imageUri) {
        this.imageUri = image;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImage() {
        return imageUri;
    }
    public void setImage(String image){
        this.imageUri = image;
    }
}
