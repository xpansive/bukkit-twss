package com.xpansive.bukkit.twss;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.bukkit.Bukkit;

public class TwssJsClient {
    private final String host;
    private final double threshold;
    private final String algorithm;
    private final int numNeighbours;
    private final int numWordsInNgram;

    public TwssJsClient(String host, String algorithm, double threshold, int numWordsInNgram, int numNeighbours) {
        this.host = host;
        this.algorithm = algorithm;
        this.threshold = threshold;
        this.numWordsInNgram = numWordsInNgram;
        this.numNeighbours = numNeighbours;
    }

    public boolean is(String text) {
        try {
            URL url = new URL(getUrl("is", text));

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage());
            }

            InputStream stream = connection.getInputStream();
            byte[] buffer = new byte[32];
            StringBuilder sb = new StringBuilder();
            int bytesRead;
            int totalBytes = 0;
            do {
                bytesRead = stream.read(buffer);
                totalBytes += bytesRead;
                sb.append(new String(buffer));
            } while (bytesRead > 0);
            stream.close();

            // The server should return a single byte, so if we read more than that we can assume its an error.
            if (totalBytes > 1) {
                throw new IOException(sb.toString());
            }

            return sb.toString().codePointAt(0) == 0 ? false : true;
        } catch (MalformedURLException e) {
            e.printStackTrace(); // Should never happen
        } catch (IOException e) {
            Bukkit.getLogger().warning("ThatsWhatSheSaid cannot reach the server! Make sure it's configured properly!");
            e.printStackTrace();
        }
        return false;
    }

    private String getUrl(String what, String query) {
        try {
            return "http://" + host + "/" +
                    what +
                    "?q=" + URLEncoder.encode(query, "UTF-8") +
                    "&t=" + threshold +
                    "&a=" + URLEncoder.encode(algorithm, "UTF-8") +
                    "&nn=" + numNeighbours +
                    "&nwin=" + numWordsInNgram;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(); // Should never happen
        }
        return null;
    }
}
