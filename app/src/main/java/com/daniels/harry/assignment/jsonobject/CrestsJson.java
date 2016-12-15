package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CrestsJson {

    @JsonField(name = "prevCrest")
    private String prevCrest;

    @JsonField(name = "nextCrest")
    private String nextCrest;

    public String getPrevCrest() {
        return prevCrest;
    }

    public void setPrevCrest(String prevCrest) {
        this.prevCrest = prevCrest;
    }

    public String getNextCrest() {
        return nextCrest;
    }

    public void setNextCrest(String nextCrest) {
        this.nextCrest = nextCrest;
    }
}
