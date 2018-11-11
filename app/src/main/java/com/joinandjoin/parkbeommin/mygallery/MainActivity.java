package com.joinandjoin.parkbeommin.mygallery;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionListener permissionListener = new PermissionListener() {
            // 접근권한 허용되었을 때
            @Override
            public void onPermissionGranted() {
                RecyclerView recyclerView = findViewById(R.id.main_rv);
                final GalleryRecyclerViewAdapter adapter = new GalleryRecyclerViewAdapter(getApplicationContext(), new PhotoUtil().getAllPhotoPathList(getApplicationContext()));

                // recyclerview 그리드 형식의 3열로 셋팅
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                recyclerView.setAdapter(adapter);

                Button button = findViewById(R.id.main_btn);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String selectedPhoto = "";
                        for (Photo photo : adapter.getSelectedPhotos()) {
                            selectedPhoto += photo.getPath() + ", ";
                        }
                        Toast.makeText(getApplicationContext(), selectedPhoto, Toast.LENGTH_LONG).show();
                    }
                });
            }

            // 접근권한 허용되지 않았을 때
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


    }
}
