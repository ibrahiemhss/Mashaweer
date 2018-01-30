package com.mashaweer.ibrahim.mashaweer.Network;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.BAS_LOGIN;

/**
 * Created by ibrahim on 19/01/18.
 */

public class UtilsApi {
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BAS_LOGIN).create(BaseApiService.class);
    }    }