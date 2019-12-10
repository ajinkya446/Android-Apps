package com.example.bmcserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bmcserver.Common.Common;
import com.example.bmcserver.Interface.ItemClickListener;
import com.example.bmcserver.Model.Category;
import com.example.bmcserver.Model.Food;
import com.example.bmcserver.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class food_list extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase db;
    DatabaseReference dbref;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId="";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    FloatingActionButton fab;

    //Adding add_new_item Details.
    EditText desc,price,fname,discount;
    Button select_food,upload_food;

    Uri saveUri;

    Food newFood;
    RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //init firebase;
        db=FirebaseDatabase.getInstance();
        dbref=db.getReference("Items");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        recyclerView= (RecyclerView)findViewById(R.id.recycler_items);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rootLayout=(RelativeLayout)findViewById(R.id.root_layout);

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddFoodDialog();
            }
        });

        if(getIntent()!=null){
            categoryId=getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId!= null){
            loadFood(categoryId);
        }
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(food_list.this);
        alertDialog.setTitle("Add New Food Item");
        alertDialog.setMessage("Please Fill Information First");
        LayoutInflater inflater= this.getLayoutInflater();
        View add_menu_layout= inflater.inflate(R.layout.add_new_food,null);

        fname=(EditText)add_menu_layout.findViewById(R.id.food_name);
        price=(EditText)add_menu_layout.findViewById(R.id.price);
        discount=(EditText)add_menu_layout.findViewById(R.id.discount);
        desc=(EditText)add_menu_layout.findViewById(R.id.Description);
        select_food=(Button)add_menu_layout.findViewById(R.id.add_Food);
        upload_food=(Button)add_menu_layout.findViewById(R.id.upload_food);

        //ButtonAction HAndler.
        select_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();      //in this user select iamge from galary.
            }
        });

        upload_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        //setting for button of alertbox.
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //adding new category to the firebase
                if(newFood!=null)
                {
                    dbref.push().setValue(newFood);
                    Snackbar.make(rootLayout,"New Category" + " " +newFood.getName()+ " " + "Was Added",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void chooseImage() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"), Common.PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if(saveUri!=null)
        {
            final ProgressDialog mDialog= new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();


            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder= storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(food_list.this, "Uploaded...!", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //setting value for new Category.
                                    newFood = new Food();
                                    newFood.setName(fname.getText().toString());
                                    newFood.setDescription(desc.getText().toString());
                                    newFood.setPrice(price.getText().toString());
                                    newFood.setDiscount(discount.getText().toString());
                                    newFood.setMenuId(categoryId);
                                    newFood.setImage(uri.toString());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(food_list.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded"+progress+"%");
                }
            });
        }

    }

    private void loadFood(String categoryId) {
        adapter= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                dbref.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int i) {
                foodViewHolder.food_name.setText(food.getName());
                Picasso.with(getBaseContext()).load(food.getImage())
                    .into(foodViewHolder.food_image);

                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Common.PICK_IMAGE_REQUEST && resultCode== RESULT_OK
                && data!=null && data.getData() != null)
        {
            saveUri= data.getData();
            select_food.setText("Image Selected");
        }
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateFoodItem(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals(Common.DELETE))
        {
            deleteFoodItem(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteFoodItem(String key) {
        dbref.child(key).removeValue();
        Toast.makeText(this, "Item Deleted...!", Toast.LENGTH_SHORT).show();
    }

    private void showUpdateFoodItem(final String key, final Food food) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(food_list.this);
        alertDialog.setTitle("Edit Food Item");
        alertDialog.setMessage("Please Fill Information First");
        LayoutInflater inflater= this.getLayoutInflater();
        View add_menu_layout= inflater.inflate(R.layout.add_new_food,null);

        fname=(EditText)add_menu_layout.findViewById(R.id.food_name);
        price=(EditText)add_menu_layout.findViewById(R.id.price);
        discount=(EditText)add_menu_layout.findViewById(R.id.discount);
        desc=(EditText)add_menu_layout.findViewById(R.id.Description);
        select_food=(Button)add_menu_layout.findViewById(R.id.add_Food);
        upload_food=(Button)add_menu_layout.findViewById(R.id.upload_food);

        //setting default value to edittext.
        fname.setText(food.getName());
        price.setText(food.getPrice());
        discount.setText(food.getDiscount());
        desc.setText(food.getDescription());


        //ButtonAction HAndler.
        select_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();      //in this user select iamge from galary.
            }
        });

        upload_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(food);
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        //setting for button of alertbox.
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                //adding new category to the firebase

                    food.setName(fname.getText().toString());
                    food.setPrice(price.getText().toString());
                    food.setDiscount(discount.getText().toString());
                    food.setDescription(desc.getText().toString());

                    dbref.child(key).setValue(food);
                    Snackbar.make(rootLayout," Food " +" " +food.getName()+ " " + "Was Edited",Snackbar.LENGTH_SHORT).show();

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void changeImage(final Food food) {
        if(saveUri!=null)
        {
            final ProgressDialog mDialog= new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();


            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder= storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(food_list.this, "Uploaded...!", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //setting value for new Category.
                                    food.setImage(uri.toString());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(food_list.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded"+progress+"%");
                }
            });
        }

    }
}
