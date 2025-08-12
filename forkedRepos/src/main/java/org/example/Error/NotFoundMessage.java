package org.example.Error;

public class NotFoundMessage {
    private int statusCode;
    private String message;

    public NotFoundMessage(){
        this.statusCode = 404;
        this.message = "User not found";
    }

    public int getStatusCode() {
        return statusCode;
    }
    public String getMessage() {
        return message;
    }
}
