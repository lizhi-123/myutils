package qianmi;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.DefaultOpenClient;
import com.qianmi.open.api.OpenClient;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ApiDemo {

    public static void main(String[] args) {
        startConsume();
    }

    public static void startConsume(){
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                Console.log("消费数据中,当前时间>>>{}", DateUtil.now());
            }
        };
        System.out.println("aaaa");
        // 启动定时任务，每10秒钟消费一次
        new Timer("timer", false).schedule(task, 10 * 1000, 10 * 1000);
    }



}
