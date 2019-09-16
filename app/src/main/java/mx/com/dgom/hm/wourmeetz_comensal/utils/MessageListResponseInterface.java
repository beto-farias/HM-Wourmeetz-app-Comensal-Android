package mx.com.dgom.hm.wourmeetz_comensal.utils;

public interface MessageListResponseInterface<T> {
    void response(String noInternetError, MessageResponse errorResponse, ListResponse<T> responseMessage);

}
