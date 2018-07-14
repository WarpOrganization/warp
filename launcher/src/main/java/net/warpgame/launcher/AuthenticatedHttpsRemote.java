package net.warpgame.launcher;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hubertus
 *         Created 21.03.17
 */
public class AuthenticatedHttpsRemote implements Remote {


    private String address;
    private AuthData authData = new AuthData();
    private String downloadPrefix = "";

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public AuthenticatedHttpsRemote(String address, AuthData authData) {
        this.address = address;
        this.authData = authData;
    }

    public AuthenticatedHttpsRemote(String address) {
        this.address = address;
    }

    public AuthResponse getAuthToken(String username, String password) {
        HttpPost post = new HttpPost("https://" + address + "/login");
        List<NameValuePair> values = new ArrayList<>();
        values.add(new BasicNameValuePair("username", username));
        values.add(new BasicNameValuePair("password", password));
        CloseableHttpResponse response;
        authData.setAuthToken(null);
        authData.setNickname(null);
        AuthResponse authResponse = new AuthResponse(authData, -1);
        try {
            post.setEntity(new UrlEncodedFormEntity(values));
            response = httpClient.execute(post);
            InputStream input = response.getEntity().getContent();
            String token = new BufferedReader(new InputStreamReader(input))
                    .lines().collect(Collectors.joining("\n"));
            HttpEntity entity2 = response.getEntity();
            EntityUtils.consume(entity2);
            response.close();
            authData.setAuthToken(token);
            authData.setNickname(username);
            authResponse.setStatusCode(response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authResponse;
    }

    public void verifyAuthToken() {
        //TODO
        //why was that needed?
    }

    private HttpGet get;

    @Override
    public void getFile(DownloadTask downloadTask) {
        get = new HttpGet("https://" + address + "/" + downloadPrefix + "/" + downloadTask.getPath());
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(get);
            downloadTask.getCallback().accept(new FileResponse(response.getStatusLine().getStatusCode(), response.getEntity().getContent(), downloadTask.getPath()));
            synchronized (downloadTask) {
                downloadTask.notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    @Override
    public void stop() {
        if (get != null) get.abort();
    }

    public AuthData getAuthData() {
        return authData;
    }

    public void setAuthData(AuthData authData) {
        this.authData = authData;
    }
}
