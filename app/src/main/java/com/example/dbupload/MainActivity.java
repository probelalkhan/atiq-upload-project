package com.example.dbupload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UploadRequestBody.UploadCallback {

    public static final String DB_NAME = "MyDatabase.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDummyDatabase();
        uploadDatabase(DB_NAME);
    }

    public void uploadDatabase(String databaseName) {
        File database = new File(getDatabasePath(DB_NAME).getAbsolutePath());
        MultipartBody.Part part =
                MultipartBody.Part.createFormData(
                        "db[]",
                        database.getName(),
                        new UploadRequestBody(database, "image", this)
                );

        Call<UploadResponse> call = RetrofitClient.getRetrofitInstance().uploadImage(
                part,
                RequestBody.create(MediaType.parse("multipart/form-data"), "json")
        );

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                Toast.makeText(MainActivity.this, "Database Uploaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void createDummyDatabase() {
        SQLiteDatabase db = openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS employees (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    department varchar(200) NOT NULL,\n" +
                        "    joiningdate datetime NOT NULL,\n" +
                        "    salary double NOT NULL\n" +
                        ");"
        );
        String insertSQL = "INSERT INTO employees \n" +
                "(name, department, joiningdate, salary)\n" +
                "VALUES \n" +
                "(?, ?, ?, ?);";
        db.execSQL(insertSQL, new String[]{"name", "dept", "2020-04-12", "20000"});
        db.execSQL(insertSQL, new String[]{"name1", "dept", "2020-04-12", "20000"});
        db.execSQL(insertSQL, new String[]{"name2", "dept", "2020-04-12", "20000"});
        db.execSQL(insertSQL, new String[]{"name3", "dept", "2020-04-12", "20000"});
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }
}