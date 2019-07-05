package com.aikudwo.ccy.headFirst.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author wls
 * @date 2019-07-02 13:25
 */
public class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherData(){}

    public void measurementsChanged(){
        setChanged();
        //这里不传递对象，说明才用的方式是拉去，而非推送
        notifyObservers();
    }

    public WeatherData(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
