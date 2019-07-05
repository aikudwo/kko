package com.aikudwo.ccy.headFirst.observer;

/**
 * @author wls
 * @date 2019-07-02 13:48
 */
public class TestObserver {

    public static void main(String[] args) {
        //模拟数据的改变
        WeatherData weatherData = new WeatherData(80, 65, 30.4f);
        //调用发布消息
        weatherData.measurementsChanged();
        //实现观察者，注册观察
        CurrentConditionsDisplay currentDisplay =
                new CurrentConditionsDisplay(weatherData);
        //调用信息
        currentDisplay.update(weatherData,weatherData);
    }
}
