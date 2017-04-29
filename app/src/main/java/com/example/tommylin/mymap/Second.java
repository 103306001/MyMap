package com.example.tommylin.mymap;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import com.example.tommylin.mymap.MapsActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by TommyLin on 2017/2/12.
 */

public class Second extends AppCompatActivity{

    private ImageView imgPhoto, imgBHeart, imgBHeart2, imgBHeart3, imgBHeart4 ,imgBHeart5;
    private Button btnSend, btnPrev;
    private TextView textView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        // 取得介面元件
        textView=(TextView)findViewById(R.id.textView) ;
        btnSend=(Button)findViewById(R.id.button2);
        btnPrev=(Button)findViewById(R.id.button3);
        imgPhoto = (ImageView)findViewById(R.id.imgPhoto);
        imgBHeart = (ImageView)findViewById(R.id.imgBHeart);
        imgBHeart2 = (ImageView)findViewById(R.id.imgBHeart2);
        imgBHeart3 = (ImageView)findViewById(R.id.imgBHeart3);
        imgBHeart4 = (ImageView)findViewById(R.id.imgBHeart4);
        imgBHeart5 = (ImageView)findViewById(R.id.imgBHeart5);



        // 設定 button 元件 Click 事件的 listener
        btnPrev.setOnClickListener(btnPrevListener);
        btnSend.setOnClickListener(btnSendListener);


        // 設定 TextView 的資料來源
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final String name = bundle.getString("NAME");
        textView.setText(name);


        //讀取資料
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("contacts");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(int i=2;i<6;i++){

                    String id = String.valueOf(i);

                    String Lat2r = dataSnapshot.child(id).child("user").getValue(String.class);

                    if (Lat2r.equals(name)){
                        Log.d("TAG", "Value is: " + name);

                        // 設定 Imgphoto(大頭貼) 的資料來源
                        // use *Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

                        String url = dataSnapshot.child(id).child("img").getValue(String.class);
                        int heart = dataSnapshot.child("1").child(name).getValue(Integer.class);
                        Picasso.with(getApplicationContext()).load(url).into(imgPhoto);

                        Log.d("TAG", "Value is: " + heart);

                        // 設定 ImgBHeart(擦肩次數) 的資料來源

                        String re = "contacts/1/"+name;
                        Log.d("TAG", "Value is: " + re);
                        DatabaseReference myRef = database.getReference(re);

                        int a = 1;
                        int times;

                        if(a<2) {
                            times = heart + 1;
                            a++;
                            myRef.setValue(times);

                            int aver;
                            aver = (times / 5) + 1;

                            switch (aver) {
                                case 1:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    break;
                                case 2:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    imgBHeart2.setImageResource(R.drawable.rheart);
                                    break;
                                case 3:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    imgBHeart2.setImageResource(R.drawable.rheart);
                                    imgBHeart3.setImageResource(R.drawable.rheart);
                                    break;
                                case 4:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    imgBHeart2.setImageResource(R.drawable.rheart);
                                    imgBHeart3.setImageResource(R.drawable.rheart);
                                    imgBHeart4.setImageResource(R.drawable.rheart);

                                    break;
                                case 5:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    imgBHeart2.setImageResource(R.drawable.rheart);
                                    imgBHeart3.setImageResource(R.drawable.rheart);
                                    imgBHeart4.setImageResource(R.drawable.rheart);
                                    imgBHeart5.setImageResource(R.drawable.rheart);
                                    break;
                                default:
                                    imgBHeart.setImageResource(R.drawable.rheart);
                                    imgBHeart2.setImageResource(R.drawable.rheart);
                                    imgBHeart3.setImageResource(R.drawable.rheart);
                                    imgBHeart4.setImageResource(R.drawable.rheart);
                                    imgBHeart5.setImageResource(R.drawable.rheart);
                                    break;
                            }
                        }
                    }
                  }
                }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                // Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // 定義 btnPrev 按鈕的onClick()方法
    private Button.OnClickListener btnPrevListener=new Button.OnClickListener(){
        public void onClick(View v){

            finish();

        }
    };


    // 定義 btnNext 按鈕的onClick() 方法
    private Button.OnClickListener btnSendListener=new Button.OnClickListener(){
        public void onClick(View v){

            new AlertDialog.Builder(Second.this)
                    .setTitle("確認視窗")
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage("確定要送出交友邀請嗎？")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i)
                        {

                        }
                    })
                    .show();

        }
    };

}
