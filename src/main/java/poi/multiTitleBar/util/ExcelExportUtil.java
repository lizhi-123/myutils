package poi.multiTitleBar.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import poi.multiTitleBar.domain.CellProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExcelExportUtil
 * @description: Excel导出多级菜单工具类
 * @author: li zhi x
 * @create: 2020/10/20
 **/
public class ExcelExportUtil {

    /**多级标题行*/
    private List<CellProperty[]> titleRowList;
    /**总数据*/
    private List<Map<String,Object>> objList;
    /**多个sheet分配的数据*/
    private List<List<Map<String,Object>>> sheetDataLists = new ArrayList<>();

    /**手动定义最大插入行--只计算数据，未计算标题行*/
    private static int maxRowNum = 65530;

    /**表格样式*/
    private HSSFCellStyle cellStyle;
    /**起始行*/
    private static int startRowIndex = 0;
    /**起始列*/
    private static int startColIndex = 0;

    /***/
    private String saveSuffix = "xlsx";

    public ExcelExportUtil(List<CellProperty[]> titleRowList, List<Map<String,Object>> objList){
        this.titleRowList = titleRowList;
        this.objList = objList;
        /*分配数据到sheet*/
        sheetInit();
    }



    private void sheetInit(){
        if (objList!=null){
            if (objList.size()>maxRowNum){
                int count = 1;
                int maxNum = maxRowNum;
                int totalPage = (objList.size()-1)/maxNum+1;
                while (true){
                    int fromIndex = (count-1)*maxNum;
                    int toIndex = (objList.size()-fromIndex)>maxNum?fromIndex+maxNum:objList.size();
                    sheetDataLists.add(objList.subList(fromIndex,toIndex));
                    System.out.println(sheetDataLists.get(count-1).size());
                    if (count==totalPage){
                        break;
                    }
                    count++;
                }
            }else {
                sheetDataLists.add(objList);
            }
        }

    }



    /**
     * 创建Excel并获取输入流
     * @return 输入流
     */
    public ByteArrayInputStream createExcelInputStream(){
        HSSFWorkbook workbook = createWorkbook();
        return getExcelInputStream(workbook);
    }

    public boolean createExcel(String savePath,String saveFileName){
        // 1. 创建文件
        String savePathWithFileName = createFile(savePath, saveFileName);
        // 2. 创建表格
        HSSFWorkbook workbook = createWorkbook();
        // 3.添加数据
        addDatasToWorkBook(workbook);
        // 5.输出Excel
        return outputExcel(workbook,savePathWithFileName);
    }

    private ByteArrayInputStream getExcelInputStream(HSSFWorkbook workbook){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = os.toByteArray();
        return  new ByteArrayInputStream(bytes);
    }

    private void saveExcel(ByteArrayInputStream inputStream,String savePath,String saveFileName){
        String fileRealPath = createFile(savePath, saveFileName);
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileRealPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while ((index = inputStream.read(bytes)) > -1) {
                outputStream.write(bytes, 0, index);
            }
            outputStream.flush();
            outputStream.close();
            System.out.println("存入数据成功");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private boolean outputExcel(HSSFWorkbook workbook,String savePathWithFileName){

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(savePathWithFileName);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
            try {
                outputStream.close();
                workbook.close();
            } catch (IOException ignored) {
                System.out.println("流关闭错误");
            }
            return false;
        }
        return true;
    }

    public String createFile(String savePath,String saveFileName){
        if (StringUtils.isBlank(savePath) || StringUtils.isBlank(saveFileName)){
            throw new RuntimeException("存储文件路径不能为空");
        }
        // 存储类型默认xlsx
        String savePathWithFileName;
        String filePath = savePath.endsWith("/") ? savePath.substring(0,savePath.length()-1):savePath;
        if (!(saveFileName.endsWith(".xls") || saveFileName.endsWith(".xlsx"))){
            savePathWithFileName = filePath+File.separator +saveFileName+"."+saveSuffix;
        }else {
            savePathWithFileName = filePath+File.separator+saveFileName;
        }
        System.out.println("文件存储位置:"+filePath);
        System.out.println("文件位置:"+savePathWithFileName);

        //创建目录
        File file=new File(filePath);
        if(!file.isDirectory()){
            file.mkdirs();
        }
        return savePathWithFileName;

    }

    private HSSFWorkbook createWorkbook(){
        // 1 声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2.设置样式
        createCellStyle(workbook);
        // 3.将多级标题和数据导入工作簿
        addDatasToWorkBook(workbook);
        return workbook;
    }

    private void addDatasToWorkBook(HSSFWorkbook workbook){
        System.out.println("分页大小："+sheetDataLists.size());
        // 2.生成数据到Excel对象
        for (int i = 0; i < sheetDataLists.size(); i++) {
            // 3.填充多级标题
            HSSFSheet sheetAt = workbook.createSheet();
            sheetAt.setDefaultColumnWidth(15);
            createTitleRowList(sheetAt);
            System.out.println(sheetAt.getSheetName());
            // 4.填充数据
            if (objList!=null && objList.size()>0){
                createDataRowList(sheetAt,sheetDataLists.get(i));
            }
        }
    }

    private void createDataRowList(HSSFSheet sheet,List<Map<String,Object>> dataList){
        // 获取最后一行标题栏作为映射参数
        ArrayList<String> titleList = new ArrayList<>();
        CellProperty[] cellProperties = this.titleRowList.get(titleRowList.size() - 1);
        for (CellProperty cellProperty : cellProperties) {
            titleList.add(cellProperty.getTitle());
        }
        System.out.println(titleList);
       //拿到一个map 判断map中的key和title是否相等，如果相等则将参数放到cell中
        for (Map<String, Object> objectMap : dataList) {
            Row row = sheet.getRow(startRowIndex);
            if(row==null){
                row = sheet.createRow(startRowIndex);
            }
            for (int i = 0; i < titleList.size(); i++) {
                Cell cell = row.createCell(i);
                System.out.println("插入数据：位置--i="+i+",标题名title=="+titleList.get(i)+",value=="+objectMap.get(titleList.get(i)));
                cell.setCellValue(objectMap.get(titleList.get(i))+"");
                cell.setCellStyle(cellStyle);
            }
            startRowIndex++; //添加一行数据行数递加
        }
        //创建完一个sheet,将起始行复原
        startRowIndex = 0;
    }



    /**
     * 填充标题到sheet
     * @param sheet sheet
     */
    private HSSFSheet createTitleRowList(HSSFSheet sheet){
        for (CellProperty[] cellProperties : this.titleRowList) { //拿到每一行的菜单单元格数组
            // 填充标题内容到sheet
            Row row = sheet.getRow(startRowIndex);
            if(row==null){
                row = sheet.createRow(startRowIndex);
            }
            int beginColIndex = 0;
            for (int i = 0; i < cellProperties.length; i++) {
                // 拿到一个单元格
                CellProperty cP = cellProperties[i];
                Cell cell = null;
                if (cP.isMergeCell()){ //要合并单元格 创建并赋值
                    // 创建单元格和要合并的单元格 j = 0,1
                    for (Integer j = cP.getStartCol(); j <= cP.getEndCol(); j++) {
                        cell = row.createCell(j);// 通过起始行来创建cell
                        if (j.equals(cP.getStartCol())){ //有值的单元格复制，要合并的单元格值为空
                            cell.setCellValue(cP.getTitle()); // 通过起始行来创建cell
                        }
                        setCellStyle(cP,cell);
                    }
                    sheet.addMergedRegion(new CellRangeAddress(cP.getStartRow(),cP.getEndRow(), cP.getStartCol(), cP.getEndCol()));
                }else { //不合并的单元格  从左到右依次创建
                    cell = row.createCell(beginColIndex);
                    cell.setCellValue(cP.getTitle());
                    setCellStyle(cP,cell);
                    beginColIndex++;
                }
            }
            startRowIndex++; //每创建一行 起始行递加
        }
        return sheet;
    }


    /**为sheet创建样式*/
    private void createCellStyle(HSSFWorkbook workbook){
        // 4 设置表格样式
        HSSFCellStyle style  = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直对齐的样式为居中对齐;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        this.cellStyle = style;
    }

    /**设置表格样式*/
    private void setCellStyle(CellProperty cP ,Cell cell){
        if (cP.getCellStyle() == null) {
            cell.setCellStyle(cellStyle);
        } else {
            cell.setCellStyle(cP.getCellStyle());
        }
    }

    public static void main(String[] args) {
        List<CellProperty[]> cellProperties = new ArrayList<>();
//        第一行菜单栏 合并10行
        CellProperty[] cellProperties1 = new CellProperty[1];
        CellProperty cellProperty11 = new CellProperty("第一行菜单栏",0,0,0,9);
        cellProperties1[0] =  cellProperty11;
        cellProperties.add(cellProperties1);
//        第二行菜单栏
        CellProperty[] cellProperties2 = new CellProperty[3];
        CellProperty cellProperty21 = new CellProperty("第2行菜单栏01",1,1,0,1);
        CellProperty cellProperty22 = new CellProperty("第2行菜单栏02",1,1,2,5);
        CellProperty cellProperty23 = new CellProperty("第2行菜单栏03",1,1,6,9);
        cellProperties2[0] =  cellProperty21;
        cellProperties2[1] = cellProperty22;
        cellProperties2[2] = cellProperty23;
        cellProperties.add(cellProperties2);
        //      第三行菜单栏
        CellProperty[] cellProperties3 = new CellProperty[10];
        CellProperty cellProperty31 = new CellProperty("第3行菜单栏01");
        CellProperty cellProperty32 = new CellProperty("第3行菜单栏02");
        CellProperty cellProperty33 = new CellProperty("第3行菜单栏03");
        CellProperty cellProperty34 = new CellProperty("第3行菜单栏04");
        CellProperty cellProperty35 = new CellProperty("第3行菜单栏05");
        CellProperty cellProperty36 = new CellProperty("第3行菜单栏06");
        CellProperty cellProperty37 = new CellProperty("第3行菜单栏07");
        CellProperty cellProperty38 = new CellProperty("第3行菜单栏08");
        CellProperty cellProperty39 = new CellProperty("第3行菜单栏09");
        CellProperty cellProperty310 = new CellProperty("第3行菜单栏10");
        cellProperties3[0] = cellProperty31;
        cellProperties3[1] = cellProperty32;
        cellProperties3[2] = cellProperty33;
        cellProperties3[3] = cellProperty34;
        cellProperties3[4] = cellProperty35;
        cellProperties3[5] = cellProperty36;
        cellProperties3[6] = cellProperty37;
        cellProperties3[7] = cellProperty38;
        cellProperties3[8] = cellProperty39;
        cellProperties3[9] = cellProperty310;
        cellProperties.add(cellProperties3);

        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("第3行菜单栏01","值01");
        map1.put("第3行菜单栏02","值02");
        map1.put("第3行菜单栏03","值03");
        map1.put("第3行菜单栏04","值04");
        map1.put("第3行菜单栏05","值05");
        map1.put("第3行菜单栏06","值06");
        map1.put("第3行菜单栏07","值07");
        map1.put("第3行菜单栏08","值08");
        map1.put("第3行菜单栏09",123);
        map1.put("第3行菜单栏10",123456.78950);
        mapList.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("第3行菜单栏01","值21");
        map2.put("第3行菜单栏02","值22");
        map2.put("第3行菜单栏03","值23");
        map2.put("第3行菜单栏04","值24");
        map2.put("第3行菜单栏05","值25");
        map2.put("第3行菜单栏06","值26");
        map2.put("第3行菜单栏07","值27");
        map2.put("第3行菜单栏08","值28");
        map2.put("第3行菜单栏09",123);
        map2.put("第3行菜单栏10",123456.78950);
        mapList.add(map2);


        ExcelExportUtil util = new ExcelExportUtil(cellProperties,mapList);
        ByteArrayInputStream excelInputStream = util.createExcelInputStream();
        util.saveExcel(excelInputStream,"D://3214","mmm.xls");


    }

}