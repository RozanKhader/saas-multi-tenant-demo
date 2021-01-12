package com.appngeek.saas_multi_tenant_demo.servicess;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Win8.1 on 3/26/2019.
 */

@Service
public class AsyncService {
    @Autowired
    Environment environment;
    @Autowired
    SyncProcedureService syncProcedureService;
    @Async
    public  void asyncMethodWithVoidReturnType(String productId, long deviceId, long branchId) throws JsonProcessingException, SQLException {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        System.out.println("      date2  "+dateFormat.format(date2)+" \n ");

        syncProcedureService.callProcedureCreate(environment,productId, deviceId,branchId);

           // syncMongoDataService.syncOfferData(companyId,deviceId,branchId);

    }


    @Async
    public  void asyncMethodForSyncProcedure(   long deviceId, long branchId) throws JsonProcessingException, SQLException {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        System.out.println("      date2  "+dateFormat.format(date2)+" \n ");

        syncProcedureService.callSyncProcedure( deviceId,branchId);


    }

/**
    @Async
    public  void asyncUpdaetTasStatus(Environment environment, SyncProcedureService syncProcedureService , long companyId, long deviceId, long branchId) throws JsonProcessingException, SQLException {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = new Date();
        System.out.println("      date2  "+dateFormat.format(date2)+" \n ");

        syncProcedureService.callProductProcedure(environment,companyId, deviceId,branchId);


    }**/
}
