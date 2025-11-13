package com.insram.worknest.util;

import lombok.AllArgsConstructor;

// ---------------- Simple API Response DTO ----------------

@AllArgsConstructor
public class ApiResponse {
    private String message;
    private int count;

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }
}
