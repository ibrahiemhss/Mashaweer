package com.mashaweer.ibrahim.mashaweer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mashaweer.ibrahim.mashaweer.Network.BaseApiService;
import com.mashaweer.ibrahim.mashaweer.model.ResponseApiModel;
import com.mashaweer.ibrahim.mashaweer.Network.UtilsApi;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

/**
 * Created by ibrahim on 22/12/17.
 */




public class RegisterUserActivity extends AppCompatActivity {
    private static final String TAG = RegisterUserActivity.class.getSimpleName();

    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    EditText etPhone;
    TextView addImage;
    Button btnRegister;
    Context mContext;
    BaseApiService mApiService;
    private AlertDialog.Builder builder;
    ProgressDialog mDialog;
    CircleImageView imgHolder;
    String part_image;
    File actualImageFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    protected static final int REQUEST_CODE_MANUAL = 5;
    private static final String[] INITIAL_PERMS = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_register_user );

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();

    }

    private void initComponents() {
        addImage=findViewById( R.id.addImage );
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPassword =  findViewById(R.id.etPassword);
        btnRegister =  findViewById(R.id.btnRegister);
        imgHolder = findViewById(R.id.imgHolder);
        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("جارى التسجيل ... ");
        etPhone=findViewById( R.id.etPhone );
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();

                if(part_image==null){
                    requestRegister();
                    imgHolder.setVisibility( View.VISIBLE );

                }else {
                     uploadPhoto();
                }
            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    openGallry();
                    if(part_image==null){
                        imgHolder.setVisibility( View.VISIBLE );
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);
                }else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
                {
                    openGallry();
                    if(part_image==null){
                        imgHolder.setVisibility( View.VISIBLE );
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

                }else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    openGallry();
                    if(part_image==null){
                        imgHolder.setVisibility( View.VISIBLE );
                    }
                    requestPermissions(INITIAL_PERMS, REQUEST_CODE_ASK_PERMISSIONS);

                }else {
                    openGallry();
                    if(part_image==null){
                        imgHolder.setVisibility( View.VISIBLE );
                    }

                }
            }
        });
    }

    private void uploadPhoto() {

      //  pd.show(mContext, null, "جارى التسجيل", true, false);
        builder = new AlertDialog.Builder( mContext);


        File imagefile= new File(part_image);

        try {
            imagefile = new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath( Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(actualImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //resizeAndCompressImageBeforeSend(mContext,part_image,imagefile.getName());

        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);

        MultipartBody.Part partImage = MultipartBody.Part.createFormData("pic", imagefile.getName(),reqBody);
        RequestBody name = createPartFromString(etNama.getText().toString());
        RequestBody email = createPartFromString(etEmail.getText().toString());
        RequestBody password = createPartFromString( etPassword.getText().toString());
        RequestBody phone = createPartFromString(etPhone.getText().toString());

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("password", password);
        map.put("phone", phone);


        Call<ResponseApiModel> upload = mApiService.uploadImage(map,partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @Override
            public void onResponse(Call<ResponseApiModel> call, final Response<ResponseApiModel> response) {
                Log.d( "RETRO", "ON RESPONSE  : " + response.body().toString() );
                mDialog.dismiss();
                builder.setMessage( response.body().getError_msg() );

                if (response.body().getEerror().equals( "false" )) {
                    builder.setMessage( response.body().getError_msg() );
                    Toast.makeText(mContext, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mContext, LoginUserActivity.class));
                    Log.d( "RETRO", "ON SUCCESS : " + response.body().getError_msg() );

                } else {
                    Log.d( "RETRO", "ON ERRORR : " + response.body().getError_msg() );
                    Toast.makeText(mContext, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    builder.setMessage( response.body().getError_msg() );

                }
            }
            @Override
            public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                Log.d( "RETRO", "ON FAILURE : " + t.getMessage() );
            }
        });

    }
    private RequestBody createPartFromString (String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }

    private void requestRegister() {

        builder=new AlertDialog.Builder( mContext );

        mApiService.registerRequest(etNama.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),etPhone.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse:start ");
                            mDialog.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);


                                if (jsonRESULTS.getString("error").equals("false")){
                                    builder.setMessage( jsonRESULTS.getString("error_msg") );
                                    Toast.makeText(mContext, jsonRESULTS.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginUserActivity.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "خطا بالاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri dataimage = data.getData();
                        String[] imageprojection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query( dataimage, imageprojection, null, null, null );

                        if (cursor != null) {
                            cursor.moveToFirst();
                            int indexImage = cursor.getColumnIndex( imageprojection[0] );
                            part_image = cursor.getString( indexImage );

                            if (part_image != null) {
                                actualImageFile = new File( part_image );
                                imgHolder.setImageBitmap( BitmapFactory.decodeFile( actualImageFile.getAbsolutePath() ) );
                            }
                        }
                    }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }

    void openGallry() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }
    void galleryPermissionDialog() {

        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(RegisterUserActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterUserActivity.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;

        } else {
            openGallry();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for READ_EXTERNAL_STORAGE

                boolean showRationale = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    if (perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        // All Permissions Granted
                        galleryPermissionDialog();
                    } else {
                        showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (showRationale) {
                            showMessageOKCancel("Read Storage Permission required for this app ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            galleryPermissionDialog();

                                        }
                                    });
                        } else {
                            showMessageOKCancel("Read Storage Permission required for this app ",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(RegisterUserActivity.this, "Please Enable the Read Storage permission in permission", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, REQUEST_CODE_MANUAL);
                                        }
                                    });

                            //proceed with logic by disabling the related features or quit the app.
                        }


                    }


                } else {
                    galleryPermissionDialog();

                }

            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder( RegisterUserActivity.this)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}



