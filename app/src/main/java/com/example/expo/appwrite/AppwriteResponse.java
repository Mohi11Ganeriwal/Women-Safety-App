package com.example.expo.appwrite;

public abstract class AppwriteResponse<T> {
    private AppwriteResponse() {}

    public static class Success<T> extends AppwriteResponse<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static class Error<T> extends AppwriteResponse<T> {
        private final int code;
        private final String message;

        public Error(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
