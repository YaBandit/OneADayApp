package com.evolve.dylan.oneaday.guiFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evolve.dylan.oneaday.R;
import com.evolve.dylan.oneaday.serverHandling.GetPhotoRestAdapter;
import com.evolve.dylan.oneaday.serverHandling.PictureData;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dylan on 09/09/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private Button getData, getPhoto, getRetroImage;
    private TextView showData;
    private ImageView imageView, retroImage;

    private JSONObject jsonObject = new JSONObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setGuiComponents(rootView);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getDataButton:
                getData();
                break;
            case R.id.getPhotoButton:
                getPhoto();
                break;
            case R.id.retroImageButton:
                getRetroImage();
                break;
        }
    }

    public void setGuiComponents(View v) {
        getData = (Button) v.findViewById(R.id.getDataButton);
        getPhoto = (Button) v.findViewById(R.id.getPhotoButton);
        showData = (TextView) v.findViewById(R.id.showDataTextbox);
        imageView = (ImageView) v.findViewById(R.id.serverPhoto);
        getRetroImage = (Button) v.findViewById(R.id.retroImageButton);
        retroImage = (ImageView) v.findViewById(R.id.retroImage);

        getData.setOnClickListener(this);
        getPhoto.setOnClickListener(this);
        getRetroImage.setOnClickListener(this);
    }

    public void getData() {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int which) {

            }
        });

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        ASyncServerPing aSyncServerPing = new ASyncServerPing("http://192.168.1.105:9001/api/unix", 9001, progressDialog);
        aSyncServerPing.execute();

    }

    public void getPhoto() {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int which) {

            }
        });

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        ASyncServerPing aSyncServerPing = new ASyncServerPing("http://192.168.1.105:9001/api/photo", 9001, progressDialog);
        aSyncServerPing.execute();

    }

    public void getRetroImage() {

        DoPhotoClass doPhotoClass = new DoPhotoClass();
        doPhotoClass.runRetrofitTestAsync();
        PictureData pictureData = doPhotoClass.getPictureDataReal();
    }

    private class DoPhotoClass {

        public PictureData getPictureDataReal() {
            return pictureDataReal;
        }

        private PictureData pictureDataReal;
        private GetPhotoRestAdapter getPhotoRestAdapter = new GetPhotoRestAdapter();

        Callback<PictureData> pictureDataCallback = new Callback<PictureData>() {
            @Override
            public void success(PictureData pictureData, Response response) {
                pictureDataReal = pictureData;
            }

            @Override
            public void failure(RetrofitError error) {
                //Nothing
                int i = 1;
            }
        };

        public void runRetrofitTestAsync () {
            getPhotoRestAdapter.getPhoto(pictureDataCallback);
        }


    }

    class ASyncServerPing extends AsyncTask <List<String>, String, String> {

        final String url;
        final int port;

        Bitmap bitmap;
        String result = "";

        private ProgressDialog progressDialog;

        public ASyncServerPing(String url, int port, ProgressDialog progressDialog) {
            this.url = url;
            this.port = port;
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(List<String>... params) {
            try {
                //String uri = "http://192.168.1.101:9001/api/unix";

                URL urlO = new URL(url);

                HttpURLConnection connection = (HttpURLConnection) urlO.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                final JSONObject jsonObject1 = new JSONObject(br.readLine());
                final String data = jsonObject1.getString("photo");
                byte[] dataArray = android.util.Base64.decode(data, android.util.Base64.URL_SAFE);
                //byte[] imageDataBytes = Base64.decodeBase64(data);

                bitmap = BitmapFactory.decodeByteArray(dataArray, 0, dataArray.length);


                /*
                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    result = output;
                }
                */
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            imageView.setImageBitmap(bitmap);
            //showData.setText(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
