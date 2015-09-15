package com.evolve.dylan.oneaday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

        HttpURLConnection connection = null;

        try {
            HttpClient client = new DefaultHttpClient();

            final String testURL = String.format("http://localhost:9001/api/unix");
            HttpGet request = new HttpGet(testURL);

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;

            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            //ClientResource client = new ClientResource(testURL);
            //String response = client.get().getText();
            //System.out.println(response);
            /*
            URL u = new URL("http://localhost:9001/api/unix");
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            System.out.println("" + code);
            // You can determine on HTTP return code received. 200 is success.
            */

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
