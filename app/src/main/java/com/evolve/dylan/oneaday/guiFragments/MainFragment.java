package com.evolve.dylan.oneaday.guiFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evolve.dylan.oneaday.MainActivity;
import com.evolve.dylan.oneaday.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by dylan on 09/09/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private Button getData;
    private TextView showData;

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
        }
    }

    public void setGuiComponents(View v) {
        getData = (Button) v.findViewById(R.id.getDataButton);
        showData = (TextView) v.findViewById(R.id.showDataTextbox);

        getData.setOnClickListener(this);
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

        ASyncServerPing aSyncServerPing = new ASyncServerPing("http://192.168.1.101:9001/api/unix", 9001, progressDialog);
        aSyncServerPing.execute();

    }

    class ASyncServerPing extends AsyncTask <List<String>, String, String> {

        final String url;
        final int port;

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
                String uri = "http://192.168.1.101:9001/api/unix";
                URL url = new URL(uri);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                    result = output;
                }
                connection.disconnect();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            showData.setText(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
