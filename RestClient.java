package edu.fvtc.tictactoegame;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestClient {

    private static final String BASE_URL = "https://fvtcdp.azurewebsites.net/GameHub";
    private final RequestQueue requestQueue;

    public RestClient(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getItem(String endpoint, final VolleyCallback<Game> callback) {
        String url = BASE_URL + endpoint;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Game game = new Game(
                                    response.getInt("id"),
                                    response.getInt("connectionId"),
                                    response.getString("name"),
                                    response.getString("player1"),
                                    response.getString("player2"),
                                    response.getString("winner"),
                                    response.getString("gameState"),
                                    response.getString("lastUpdate"),
                                    response.getString("nextTurn"),
                                    response.getString("photo")
                            );
                            callback.onSuccess(game);
                        } catch (JSONException e) {
                            callback.onError(new IOException("JSON parsing error", e));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(new IOException("Volley error", error));
                    }
                });
        requestQueue.add(request);
    }
    // Retrieve  all Users
    public void get(String endpoint, final VolleyCallback<List<Game>> callback) {
        String url = BASE_URL + endpoint;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Game> games = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Game game = new Game(
                                        jsonObject.getInt("id"),
                                        jsonObject.getInt("connectionId"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("player1"),
                                        jsonObject.getString("player2"),
                                        jsonObject.getString("winner"),
                                        jsonObject.getString("nextTurn"),
                                        jsonObject.getString("completed"),
                                        jsonObject.getString("gameState"),
                                        jsonObject.getString("photo")



                                );
                                games.add(game);
                            }
                            callback.onSuccess(games);
                        } catch (JSONException e) {
                            callback.onError(new IOException("JSON parsing error", e));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(new IOException("Volley error", error));
                    }
                });
        requestQueue.add(request);
    }
    // add new item
    public void post(String endpoint, String userName, Game game, final VolleyCallback<JSONObject> callback) {
        String url = BASE_URL + endpoint;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", game.getId());
            jsonObject.put("connectionId", game.getConnectionId());
            jsonObject.put("name", game.getName());
            jsonObject.put("player1", game.getPlayer1());
            jsonObject.put("player2", game.getPlayer2());
            jsonObject.put("userName", userName);
            jsonObject.put("lastUpdateDate", game.getLastUpdateDate());
            jsonObject.put("gameState",game.getGameState());
            jsonObject.put("winner", game.getWinner());
            jsonObject.put("completed", game.getCompleted());
            jsonObject.put("nextTurn", game.getNextTurn());
        } catch (JSONException e) {
            callback.onError(new IOException("Error creating JSON object", e));
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(new IOException("Volley error", error));
                    }
                });
        requestQueue.add(request);
    }
    // Update item
    public void put(String endpoint, String userName, Game game, final VolleyCallback<JSONObject> callback) {
        String url = BASE_URL + endpoint;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", game.getId());
            jsonObject.put("connectionId", game.getConnectionId());
            jsonObject.put("name", game.getName());
            jsonObject.put("player1", game.getPlayer1());
            jsonObject.put("player2", game.getPlayer2());
            jsonObject.put("userName", userName);
            jsonObject.put("lastUpdateDate", game.getLastUpdateDate());
            jsonObject.put("gameState",game.getGameState());
            jsonObject.put("winner", game.getWinner());
            jsonObject.put("completed", game.getCompleted());
            jsonObject.put("nextTurn", game.getNextTurn());

        } catch (JSONException e) {
            callback.onError(new IOException("Error creating JSON object", e));
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(new IOException("Volley error", error));
                    }
                });
        requestQueue.add(request);
    }
    // Delete item
    public void delete(String endpoint, final VolleyCallback<String> callback) {
        String url = BASE_URL + endpoint;
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(new IOException("Volley error", error));
                    }
                });
        requestQueue.add(request);
    }

    public interface VolleyCallback<T> {
        void onSuccess(T result);
        void onError(IOException e);
    }
}