package com.insram.worknest.util;

// ---------------- Simple API Response DTO ----------------
public class ApiResponse {
    private String message;
    private int count;

    public ApiResponse(String message, int count) {
        this.message = message;
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }
}
