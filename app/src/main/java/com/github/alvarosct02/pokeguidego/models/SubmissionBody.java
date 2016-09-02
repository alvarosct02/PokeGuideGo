package com.github.alvarosct02.pokeguidego.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alvaro on 8/21/2016.
 */
public class SubmissionBody {

    private List<Submission> data;
    private List<Submission> filteredData;
    private boolean success;

    public List<Submission> getAllResults() {
        return data;
    }

    public List<Submission> getVerifiedResults() {
        if (filteredData == null){
            filteredData = new ArrayList<>();
            for (Submission submission: data) {
                if (submission.isVerified()){
                    filteredData.add(submission);
                }
            }
        }
        return filteredData;
    }

    public void setData(List<Submission> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    TODO: Find out which type of Array this call returns
//    List<JsonObject> errors;
}
