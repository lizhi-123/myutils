package concurrent;

import java.util.List;
import java.util.Map;

public class MyThread1 extends Thread {

    private List<String> list;
    private Map<Long,Integer> map;

    public MyThread1(List<String> list,Map<Long,Integer> map){
        this.list = list;
        this.map = map;
    }

    @Override
    public void run() {

        int pcount = Runtime.getRuntime().availableProcessors();
        int i = map.get(Thread.currentThread().getId());

        for(;i<list.size();i+=pcount){
            System.out.println(list.get(i));
        }
    }
}
