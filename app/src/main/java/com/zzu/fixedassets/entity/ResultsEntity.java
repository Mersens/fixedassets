package com.zzu.fixedassets.entity;

import java.util.List;

/**
 * Created by Mersens on 2017/6/22 17:44
 * Email:626168564@qq.com
 */

public class ResultsEntity {
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    private List<Results> results;



}
