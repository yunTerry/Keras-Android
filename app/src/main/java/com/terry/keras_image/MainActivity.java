package com.terry.keras_image;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int percode = 1, phocode = 2;
    private ImageView imgView;
    private RecyclerView recyclerView;
    private Button upload;
    private LinearLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void pickphoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, percode);
        } else {
            MultiImageSelector.create().showCamera(false).single().start(this, phocode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == percode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MultiImageSelector.create().single().start(this, phocode);
            } else {
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == phocode) {
            if (resultCode == RESULT_OK) {
                String imgpath = data.getStringArrayListExtra(
                        MultiImageSelectorActivity.EXTRA_RESULT).get(0);
                Picasso.with(this).load("file://" + imgpath).into(imgView);
                File mfile = new File(imgpath);
                RequestBody phBody = RequestBody.create(MediaType.parse("multipart/form-data"), mfile);
                MultipartBody.Part requestImgPart =
                        MultipartBody.Part.createFormData("image", mfile.getName(), phBody);
                loading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Rest.getRestApi().uploadFile(requestImgPart).enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        loading.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(new CommonAdapter<Result.PredictionsBean>(MainActivity.this,
                                R.layout.img_item, response.body().predictions) {
                            @Override
                            protected void convert(ViewHolder holder, Result.PredictionsBean pred, int position) {
                                holder.setText(R.id.percent, String.format("%.2f", pred.probability * 100) + "%");
                                holder.setProgress(R.id.progress, (int) Math.round(pred.probability * 10000));
                                holder.setText(R.id.object, pred.label);

                            }

                        });
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "请求失败",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void initView() {
        imgView = findViewById(R.id.img_view);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        upload = findViewById(R.id.upload);
        upload.setOnClickListener(this);
        loading = findViewById(R.id.loading);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.upload:
                pickphoto();
                break;
        }
    }
}
