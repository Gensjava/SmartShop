package ua.smartshop.interfaces;

import org.json.JSONArray;

/**
 * Created by Gens on 23.04.2015.
 */
public interface AsyncWorkerInterface {
    void onBegin(); //Асинхронная операция началась

    void onSuccess(JSONArray mPJSONArray); //Получили результат

    void onFailure(Throwable t); //Получили ошибку

    void onEnd(); //Операция закончилась

}
