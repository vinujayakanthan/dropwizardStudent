package com.ninja.student.resource;

import javax.ws.rs.core.Response;

public class ApiResponse<T> {

    private T data;
    private boolean success = true;

    public ApiResponse(T data) {
        this.data = data;
    }

}
