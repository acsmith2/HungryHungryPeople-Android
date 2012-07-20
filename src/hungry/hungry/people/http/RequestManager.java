package hungry.hungry.people.http;

import com.google.gson.Gson;

import android.content.Context;
import hungry.hungry.people.R;
import hungry.hungry.people.gson.FeedbackRequest;
import hungry.hungry.people.gson.History;
import hungry.hungry.people.gson.SearchRequest;
import hungry.hungry.people.gson.SearchResponse;

public class RequestManager {
	
	public static SearchResponse findFood(Context context, SearchRequest request) {
		String url = context.getString(R.string.server_url) + context.getString(R.string.version) + context.getString(R.string.find_food_uri);
		String jsonString = new Gson().toJson(request, SearchRequest.class);
		String result = HttpUtil.post(url, jsonString);
		SearchResponse response = null;
		if( result != null )
			response = new Gson().fromJson(result, SearchResponse.class);
		return response;
	}
	
	public static boolean feedback(Context context, FeedbackRequest request) {
		String url = context.getString(R.string.server_url) + context.getString(R.string.version) + context.getString(R.string.food_feedback_uri);
		String jsonString = new Gson().toJson(request, FeedbackRequest.class);
		HttpUtil.post(url, jsonString);
		return true;
	}
	
	public static History getHistory(Context context, String userId) {
		String url = context.getString(R.string.server_url) + context.getString(R.string.version) + context.getString(R.string.get_history_uri) + "?userId="
				+ userId;
		String historyString = HttpUtil.get(url);
		History response = null;
		if (historyString != null)
			response = new Gson().fromJson(historyString, History.class);
		return response;
	}
}
