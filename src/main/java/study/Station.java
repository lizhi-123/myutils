package study;

import study.domain.DisplayForm;
import study.domain.MonitoringIndex;
import study.domain.Pressure;
import study.domain.Temperature;
import study.index_enum.IndexEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 气象站系统需求
 * 1 提供各种天气指标的自动监控功能.(目前有如下几个部分,后续会增加新的检测指标).
 *   温度
 *   大气压力
 *   pm2.5
 *   风速和风向
 *   相对湿度
 *   ...
 * 2 每一种天气指标会有相应的硬件传感器提供api.用于读取数据.(demo可以用随机数来模拟api)
 * 3 每一种天气指标有专门的显示格式.
 * 4 有一个显示器,持续显示所有的天气指标的测量值以及当前的时间和日期, 后续可能升级显示器.
 * 5 天气指标的采样频率可能会变更(例如夏天和冬天对pm25的采样频率不同, 温度和风速的采样频率也会不同).
 */
public class Station {
    private double api = (Math.random()*(100-1)+1);

    public static void main(String[] args) {
        //获取天气指标
        List<MonitoringIndex> monitoringIndices = new ArrayList<>();
        //温度
        MonitoringIndex temperature = new Temperature();
        temperature.setIndexFlag(IndexEnum.TEMPERATURE);
        temperature.setIndexName("温度");
        temperature.setSamplingFrequency(16);
        temperature.setDisplayForm(new DisplayForm("\u2103"));
        monitoringIndices.add(temperature);
        //压力
        MonitoringIndex pressure = new Pressure();
        pressure.setIndexFlag(IndexEnum.TEMPERATURE);
        pressure.setIndexName("大气压力");
        pressure.setSamplingFrequency(10);
        pressure.setDisplayForm(new DisplayForm("Pa"));
        monitoringIndices.add(pressure);

        //数据获取
            //分线程,按照频率请求接口

        //数据展示
        monitoringIndices.forEach(x->{
            new Thread(() -> {
               //线程死循环请求接口
                while (true){
                    System.out.println("名称："+x.getIndexName());
                    x.setIndexValue(apiData());
                    System.out.println("监控数据："+x.getIndexValue()+x.getDisplayForm().getSuffix());
                    try {
                        Thread.sleep(x.getSamplingFrequency()*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            //线程启动

        });
        System.out.println("结束？");
    }
    public static String apiData(){
        Random rand = new Random();
        return rand.nextInt(100) + 1+"";
    }

}
