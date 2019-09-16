package mx.com.dgom.hm.wourmeetz_comensal.utils;

public interface MessageResponseInterface<T> {
       void response(String noInternetError, MessageResponse errorResponse, MessageResponse<T> responseMessage);




}
