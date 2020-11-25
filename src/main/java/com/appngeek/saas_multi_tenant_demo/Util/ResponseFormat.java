package com.appngeek.saas_multi_tenant_demo.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

/**
 * Created by Win8.1 on 28/05/2018.
 */
public class ResponseFormat {

    public static ResponseEntity<?> responseMessage(String logTag, String responseMessage, String obj, int status, String... field) {

        return ResponseEntity.status(status).body(responseMsg(logTag,responseMessage,obj,status,field));
    }
    public static ResponseEntity<?> responseMessage(String logTag, String responseMessage, String obj, int status, String company, long posNum) {
        return ResponseEntity.status(status).body(responseMsg(logTag,responseMessage,obj,status));
    }

    private static String getObjectResult(Object [] obj)
    {
        ObjectMapper om=new ObjectMapper();
        StringBuilder result= new StringBuilder("[");
        try{
            for(int i=0;i<obj.length;i++){
                if(i+1==obj.length)
                    result.append(om.writeValueAsString(obj[i]));
                else
                    result.append(om.writeValueAsString(obj[i])).append(",");


            }}
        catch (JsonProcessingException e){


        }
        result.append("]");

        return result.toString();
    }
    public static ResponseEntity<?> responseMessageArray(String logTag, String responseMessage, Object[] obj, int status) {

       String result = getObjectResult(obj);
        return ResponseEntity.status(status).body(responseMsg(logTag, responseMessage, result, status));
    }

    public static ResponseEntity<?> responseMessageArray(String logTag, String responseMessage, Object[] obj, int status, long objectsCount) {
        String result = getObjectResult(obj);
        return ResponseEntity.status(status).body(responseMsg(logTag, responseMessage, result, status, objectsCount));
    }


    public static String responseMsg(String logTag,String message,String obj,int status, String... field) {
        if( field.length != 0 && field!=null){
            for(int i=0;i<field.length;i++)
                System.out.println(field[i]);
        }

        String msg= "{\"logTag\":\"" + logTag + "\",\"status\":\""+status+"\",\"responseType\":\"" + message +"\"";
        if( field.length != 0 && field!=null )
        msg+=",\"field\":\"" + field[0] +"\"";
        msg+=", \"responseBody\":";
        return messageConsumer(status, obj, msg);
    }

    public static String responseMsg(String logTag,String message,String obj,int status, long objectsCount) {


        String msg= "{\"logTag\":\"" + logTag + "\",\"status\":\""+status+"\",\"responseType\":\"" + message +"\", \"count\":\""+ objectsCount +"\",\"responseBody\":";
        return messageConsumer(status, obj, msg);

    }


    private static String messageConsumer(int status, String obj, String msg)
    {
        if(status==500)
            msg +="\"" + obj + "\"}";
        else{


            if(obj==null||obj.isEmpty())
                msg += null + "}";

            else if(!obj.contains("{"))
                msg +="\""+ obj +"\""+ "}";

            else
                msg += obj + "}";


        }
        return msg;
    }

    public enum ResponseStatus{
        OK(200),
        NOT_FOUND(404),EXCEPTION(500),INVALID(600),CONFLICT(409),BAD_REQUEST(400);

        private final int status;

        ResponseStatus(int status)
        {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }
    }



}
