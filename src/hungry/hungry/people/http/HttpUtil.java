package hungry.hungry.people.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpUtil {
	private static final String TAG = HttpUtil.class.toString();
	private static final Integer TIME_OUT = new Integer(15000);

	public static InputStream getInputStrem(String url)  {
		HttpClient client = new DefaultHttpClient();

		HttpGet get;
		try {
			get = new HttpGet(new URI(url));
		} catch (URISyntaxException e) {
			Log.e(TAG, e.getMessage());
			return null;
		}

		InputStream is = null;
		get.setHeader("Accept", "application/json");

		HttpResponse response;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() > 201) {
				Log.e(TAG, "Bad Response: " + response.getStatusLine());
			}
			is = response.getEntity().getContent();
		} catch (Exception e) {
		}
		
		client.getConnectionManager().shutdown();

		return is;
	}

	public static String get(String url) {
		String responseStr = null;
		InputStream is = getInputStrem(url);
		if (is != null) {
			try {
				responseStr = convertStreamToString(is);
				Log.i(TAG, responseStr);
			} catch (Exception e) {
				Log.e(TAG, "Error in convert post results", e);
			}
		}
		return responseStr;
	}

	public static String post(String url, String object) {
		HttpClient client = new DefaultHttpClient();

		HttpParams params = client.getParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, TIME_OUT);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);

		String responseStr = null;
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		try {
			post.setEntity(new StringEntity(object));
		} catch (UnsupportedEncodingException e2) {
			Log.e(TAG, e2.getMessage());
			return null;
		}

		HttpResponse response;
		InputStream is = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() > 201) {
				Log.e(TAG, "Bad Response: " + response.getStatusLine());
			}
			is = response.getEntity().getContent();
		} catch (IOException e1) {
		}

		client.getConnectionManager().shutdown();

		if (is != null) {
			try {
				responseStr = convertStreamToString(is);
				Log.i(TAG, responseStr);
			} catch (Exception e) {
				Log.e(TAG, "Error in convert post results", e);
			}
		}

		return responseStr;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString();
	}

}
