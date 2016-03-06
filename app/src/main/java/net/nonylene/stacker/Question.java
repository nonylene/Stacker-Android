package net.nonylene.stacker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Question {

    public final boolean isAnswered;
    public final String ownerName;
    public final int upVoteCount;
    public final Date creationDate;
    public final String bodyMarkdown;
    public final String link;
    public final String title;

    public Question(JSONObject json) throws JSONException {
        isAnswered = json.getBoolean("is_answered");
        upVoteCount = json.getInt("up_vote_count");
        creationDate = new Date(json.getInt("creation_date") * 1000);
        bodyMarkdown = json.getString("body_markdown");
        link = json.getString("link");
        title = json.getString("title");

        JSONObject owner = json.getJSONObject("owner");
        ownerName = owner.getString("display_name");
    }
}
