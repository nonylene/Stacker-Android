package net.nonylene.stacker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {

    public final boolean isAnswered;
    public final String ownerName;
    public final int upVoteCount;
    public final Date creationDate;
    public final String body;
    public final String link;
    public final String title;
    public final List<String> tags;

    public Question(JSONObject json) throws JSONException {
        isAnswered = json.getBoolean("is_answered");
        upVoteCount = json.getInt("up_vote_count");
        creationDate = new Date(json.getInt("creation_date") * 1000L);
        body = json.getString("body");
        link = json.getString("link");
        title = json.getString("title");

        JSONArray tagsJson = json.getJSONArray("tags");
        tags = new ArrayList<>();
        for (int i = 0; i < tagsJson.length(); i++) {
            tags.add(tagsJson.getString(i));
        }

        JSONObject owner = json.getJSONObject("owner");
        ownerName = owner.getString("display_name");
    }
}
