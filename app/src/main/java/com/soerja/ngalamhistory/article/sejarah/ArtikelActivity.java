package com.soerja.ngalamhistory.article.sejarah;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soerja.ngalamhistory.R;
import com.squareup.picasso.Picasso;

public class ArtikelActivity extends AppCompatActivity {

    private TextView  tvDesc, jumlahLike;
    private ImageView ivCOL;
    private String Judul = null, id_artikel = null, userUID, judul_artikel, kontent, judul_gambar;
    private DatabaseReference artReference, likeReference, userRef;
    private FirebaseAuth mAuth;
    private ImageButton like;
    private Button maps;
    private boolean cekLike = false;
    int countLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sejarahart);

        Toolbar toolbarhisart = findViewById(R.id.toolbarhisart);
        setSupportActionBar(toolbarhisart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapstoolhisart = findViewById(R.id.coollapshisart);

        mAuth = FirebaseAuth.getInstance();
        artReference = FirebaseDatabase.getInstance().getReference().child("Artikel");
        likeReference = FirebaseDatabase.getInstance().getReference().child("Likes");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userUID = mAuth.getCurrentUser().getUid();

        likeReference.keepSynced(true);

        Intent intent = getIntent();
        id_artikel = intent.getExtras().getString("id_artikel");

        collapstoolhisart.setCollapsedTitleTextColor(ContextCompat.getColor(ArtikelActivity.this, R.color.colorWhite ));
        collapstoolhisart.setExpandedTitleColor(ContextCompat.getColor(ArtikelActivity.this, R.color.colorWhite));

        jumlahLike = findViewById(R.id.jumlahLike);
        tvDesc = findViewById(R.id.Artic_desc);
        ivCOL = findViewById(R.id.imgcollaps);

        //Deklarasi Button
        Button btnPD = findViewById(R.id.btPhotoDesc);
        Button btnCMNT = findViewById(R.id.comment);
        Button btnTN = findViewById(R.id.thennow);
        maps = findViewById(R.id.map);
        like = findViewById(R.id.likeButton);

        artReference.child(id_artikel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                judul_artikel = (String) dataSnapshot.child("judul_artikel").getValue();
                kontent = (String) dataSnapshot.child("kontent").getValue();
                judul_gambar = (String) dataSnapshot.child("judul_gambar").getValue();

                collapstoolhisart.setTitle(judul_artikel);
                Picasso.with(ArtikelActivity.this).load(judul_gambar).into(ivCOL);
                tvDesc.setText(kontent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userUID != null){
                    cekLike = true;
                    likeReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(cekLike){
                                if(dataSnapshot.child(id_artikel).hasChild(userUID)){
                                    likeReference.child(id_artikel).child(userUID).removeValue();
                                    cekLike = false;
                                }
                                else{
                                    likeReference.child(id_artikel).child(userUID).setValue(true);
                                    cekLike = false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(ArtikelActivity.this, "Anda Harus Login Untuk Menggunakan Fitur Ini !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Komment
        btnCMNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArtikelActivity.this, ComentActivity.class);
                intent.putExtra("id_artikel", id_artikel);
                startActivity(intent);
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keMap(judul_artikel);
            }
        });

        setLikeBtn(id_artikel);

    }

    private void keMap(String judul_artikel) {
        Uri UriKoordinat = Uri.parse("geo:-7.9786395,112.5617424?q="+judul_artikel+"");
        Intent intentMap = new Intent(Intent.ACTION_VIEW, UriKoordinat);
        intentMap.setPackage("com.google.android.apps.maps");
        startActivity(intentMap);
    }

    public void setLikeBtn(final String id_Artikel){
        likeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(id_Artikel).hasChild(mAuth.getCurrentUser().getUid())){

                    like.setImageResource(R.drawable.ic_action_thumbup);
                    countLikes = (int)dataSnapshot.child(id_Artikel).getChildrenCount();
                    jumlahLike.setText(Integer.toString(countLikes));
                }
                else{
                    like.setImageResource(R.drawable.ic_action_thumbupblack);
                    countLikes = (int)dataSnapshot.child(id_Artikel).getChildrenCount();
                    jumlahLike.setText(Integer.toString(countLikes));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
