package com.daniels.harry.assignment.jsonobject;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ResultJson {

    @JsonField(name = "Id")
    private String id;

    @JsonField(name = "Name")
    private String name;

    @JsonField(name = "Latitude")
    private double latitude;

    @JsonField(name = "Longitude")
    private double longitude;

    @JsonField(name = "Ground")
    private String ground;

    @JsonField(name = "CrestURL")
    private String crestUrl;
}
