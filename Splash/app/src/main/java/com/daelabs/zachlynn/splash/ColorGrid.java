package com.daelabs.zachlynn.splash;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach Lynn on 1/14/2017.
 */

public class ColorGrid extends Activity{

    String _feed;
    int _limit;
    TextView titleTV;
    TextView summaryTV;
    String title;
    String summary;
    String link;
    ArrayList titles;
    ArrayList summaries;
    ArrayList links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_grid);
        String feed = "http://mlb.mlb.com/partnerxml/gen/news/rss/";
        String selection = getIntent().getExtras().getString("team");
        //_limit = getIntent().getExtras().getInt("limit");
        _feed = feed + selection + ".xml";
        titles = new ArrayList<String>();
        summaries = new ArrayList<String>();
        links = new ArrayList<String>();
        loadPage();
    }

    // Uses AsyncTask to download the XML feed from mlb.com.
    public void loadPage() {
        new DownloadXmlTask().execute(_feed);
    }

    // Implementation of AsyncTask used to download XML feed from mlb.com
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return loadXmlFromNetwork(urls[0]);
            } catch (IOException e) {
                return  getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return  getResources().getString(R.string.xml_error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            titleTV = (TextView) findViewById(R.id.title);
            //String href = "<a href='"+link+"'>"+title+"</a>";
            titleTV.setText(Html.fromHtml("<a href="+links.get(0).toString()+">"+titles.get(0).toString()+"</a>"));
            titleTV.setMovementMethod(LinkMovementMethod.getInstance());
            summaryTV = (TextView) findViewById(R.id.description);
            summaryTV.setText(summaries.get(0).toString());
        }
    }

    // Uploads XML from mlb.com, parses it, and combines it with
    // HTML markup. Returns HTML string.
    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        HandleXml handleXml = new HandleXml();
        List<HandleXml.Entry> entries = null;

        try {
            stream = downloadUrl(urlString);
            entries = handleXml.parse(stream, _limit);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // HandleXml returns a List (called "entries") of Entry objects.
        // Each Entry object represents a single post in the XML feed.
        // This section processes the entries list to combine each entry with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summary.
        for (HandleXml.Entry entry : entries) {
            titles.add(entry.title);
            summaries.add(entry.summary);
            links.add(entry.link);
            //title = entry.title;
            //link = entry.link;
            //summary = entry.summary;
        }
        return "";
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}



