package mx.com.dgom.hm.wourmeetz_comensal.utils;

import java.io.Serializable;

public class MessageResponse<T> implements Serializable {
    private T data;
    private String message;
    private int responseCode;

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int i) {
        this.responseCode = i;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }
}
