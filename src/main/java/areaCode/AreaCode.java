package areaCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AreaCode
 * @description: 爬取区域
 * @author: li zhi x
 * @create: 2020/11/18
 **/
public class AreaCode {
    private static String index = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/index.html";

    public static void main(String[] args) throws IOException {
//        try{
//          Document  doc = Jsoup.connect(index)
//                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")
//                    .header("Connection", "close")//如果是这种方式，这里务必带上
//                    .timeout(8000)//超时时间
//                    .get();
//            System.out.println(doc);
//        } catch (Exception e) {//可以精确处理timeoutException
//            //超时处理
//        }
//        Document document = Jsoup.parse(new URL(index).openStream(), "GBK", index);
        // 1.解析获取所有省级的名称以及跳转的地址
        Area area = new Area();
        area.setLaaLevel(1);
//        area.setName("全国");
        area.setRegionCode("0");
        area.setSubUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/11.html");
        List<Area> provinceArea = getSubRegion(area);
        System.out.println("总数据大小："+provinceArea.size());
        for (Area area1 : provinceArea) {
            System.out.println(area1.toString());
        }
        //测试村级
//        String url = index;
//        url = url.substring(0,index.lastIndexOf("/"));
//        System.out.println(url);
//        System.out.println(url.substring(0,url.lastIndexOf("/")+1));

    }


    public static List<Area> getSubRegion(Area pArea) throws IOException {
        String areaClassName = null;
        List<Area> areas = new ArrayList<>();
        int level = pArea.getLaaLevel()+1;
        // 1.获取地址
        Document document = null;
        try{
              document = Jsoup.connect(pArea.getSubUrl())
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0")
                    .header("Connection", "close")//如果是这种方式，这里务必带上
                    .timeout(8000)//超时时间
                    .get();
            System.out.println(document);
        } catch (Exception e) {//可以精确处理timeoutException
            //超时处理
        }
//        Document document = Jsoup.parse(new URL(index).openStream(), "GBK", index);
        // 2.获取要操作的所有节点 并对节点进行判断
        switch(level){
            case 1: //省级
                areaClassName = "provincetr"; break;
            case 2: //市级
                areaClassName = "citytr"; break;
            case 3: //县级
                areaClassName = "countytr"; break;
            case 4: //乡镇级
                areaClassName = "towntr"; break;
            case 5: //村级  特殊 一个村三个td
                areaClassName = "villagetr"; break;
        }
        Elements elements = document.getElementsByClass(areaClassName); //拿到所有的
        //     2.1 省级每个节点下一个a标签对应一个省
        for (Element element : elements) {
            if (level==1){
                List<Area> provinces = queryProvince(pArea, element);
                for (Area province : provinces) {
                    areas.add(province);
                    System.out.println(province);
                    getSubRegion(province);

                }
            }
            //     2.5 村级节点下没有a标签,要从节点下取下标为 0 2 的td标签的值
            if (level==5){ //递归的结束点,不再调用getSubRegion
                Area area = queryVillage(pArea, element);
                areas.add(area);
            }
            //     2.2 市级每个节点下两个a标签代表一个市  但是要判断节点下是否有a标签，没有的话就从td标签拿市辖区
            //     2.3 县级每个节点下两个a标签代表一个市  但是要判断节点下是否有a标签，没有的话就从td标签拿市辖区
            //     2.4 乡镇级节点下两个a标签代表一个市  但是要判断节点下是否有a标签，没有的话就从td标签拿市辖区
            Area area = querySubRegion(pArea, element);
            areas.add(area);
            System.out.println(area);
            getSubRegion(area);
        }
        return areas;
    }

    private static Area querySubRegion(Area pArea,Element element){
        System.out.println(element);
        Area area = new Area();
        area.setLaaLevel(pArea.getLaaLevel()+1);
        area.setPRegionCode(pArea.getRegionCode());
        Elements tagAs = element.getElementsByTag("a");
        if (tagAs==null || tagAs.size()==0){ //说明为市辖区,没有超链接 需要从td标签中拿数据
            Elements tds = element.getElementsByTag("td");
            area.setRegionCode(tds.get(0).text());
            area.setName(tds.get(1).text());
            return area;
        }
        System.out.println("0------"+tagAs.get(0));
        System.out.println("1--------------"+tagAs.get(1));
        area.setRegionCode(tagAs.get(0).text());
        String url = pArea.getSubUrl();
        url = url.substring(0,url.lastIndexOf("/")) ;
        area.setSubUrl(url.substring(0,url.lastIndexOf("/")+1)+tagAs.get(0).attr("href"));
        area.setName(tagAs.get(1).text());
        return area;
    }

    private static Area queryVillage(Area pArea,Element element){
        Area village = new Area();
        village.setLaaLevel(pArea.getLaaLevel()+1);
        village.setPRegionCode(pArea.getRegionCode());
        Elements tds = element.getElementsByTag("td");
        village.setRegionCode(tds.get(0).text());
        village.setName(tds.get(2).text());
        return village;
    }

    private static List<Area> queryProvince(Area pArea,Element element){
        List<Area> provinces = new ArrayList<>();
        //遍历provincetr下所有a标签
        for (Element tagProvince : element.getElementsByTag("a")) {
            Area province = new Area();
            province.setPRegionCode(pArea.getRegionCode());
            String url = pArea.getSubUrl();
            province.setSubUrl(url.substring(0,url.lastIndexOf("/")+1)+tagProvince.attr("href"));
            province.setName(tagProvince.text());
            province.setLaaLevel(pArea.getLaaLevel()+1);
            provinces.add(province);
            System.out.println(province);
        }
        return provinces;
    }


}
