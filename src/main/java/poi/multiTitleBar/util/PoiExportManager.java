package poi.multiTitleBar.util;

import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import poi.multiTitleBar.domain.CellProperty;
import poi.multiTitleBar.domain.User;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @ClassName PoiExportManager
 * @description: 管理导出的Excel属性
 * @author: li zhi x
 * @create: 2020/10/21
 **/
public class PoiExportManager<T> {


    /**sheet名称 只支持一个sheet sheet名称 只支持一个sheet*/
    private String sheetName = "Sheet";
    /**表格列宽度*/
    private  int DefaultColumnWidth = 15;
    /**多级标题集合 CellProperty单元格属性对象*/
    private List<CellProperty[]> titleRowList;
    /**T类型数据*/
    private List<T> objList;

    /**单元格最大个数，创建表头时计算得出*/
    private int maxCellSize = 0;
    /**数据插入行起始索引*/
    private int startRowIndex = 0;
    /**数据插入起始列索引*/
    private int startColIndex = 0;
    /**多级标题的行数*/
    private int titleRowsNum;
    /**单元格最大列数  有多级标题合并数得出*/
    private int maxColNum;
    /** 存储文件类型 xls)一个sheet最多65536行 xlsx)一个sheet最多1048576行*/
    private String saveSuffix = "xlsx";


    /**不确定最大行数  写死最大行数65500 超出长度在新sheet表中写入数据*/
    private int maxRowNum = 65500;
    /**写入65530行后剩余行数*/
    private int leftRowNum = 0;
    /**sheet集合*/
//    private Sheet[] sheets;
    /**每个sheet插入数据集合*/
    private List<List<T>> sheetDataLists = new ArrayList<>();

    private HSSFCellStyle cellStyle;

    public String getSaveSuffix() {
        return saveSuffix;
    }

    public void setSaveSuffix(String saveSuffix) {
        this.saveSuffix = saveSuffix;
    }

    public PoiExportManager(int defaultColumnWidth, List<CellProperty[]> titleRowList, List<T> objList) {
        DefaultColumnWidth = defaultColumnWidth;
        this.titleRowList = titleRowList;
        this.objList = objList;
        init();
    }

    /**
     * 对数据进行分配sheet
     */
    public void init(){
        if (objList!=null||objList.size()>maxRowNum){
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

    private boolean createExcel(String savePath,String saveFileName){
        // 1. 创建文件
        String savePathWithFileName = createFile(savePath, saveFileName);
        // 2. 创建表格
        HSSFWorkbook workbook = createWorkbook();
        // 3.添加数据
        addDatasToWorkBook(workbook);
        // 5.输出Excel
        return outputExcel(workbook,savePathWithFileName);
    }

    /**
     * 将生成的Excel转成输入流
     * @param savePath 存储到本地的路径
     * @param saveFileName 存储的文件名
     * @param uploadPath
     * @return
     */
    public ByteArrayInputStream createExcelInputStream(String savePath,String saveFileName,String uploadPath){
        // 1.创建Excel对象
        HSSFWorkbook workbook = createWorkbook();
        addDatasToWorkBook(workbook);
        // 创建输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = os.toByteArray();
        return  new ByteArrayInputStream(bytes);
    }

    private void addDatasToWorkBook(HSSFWorkbook workbook){
        System.out.println("分页数据："+sheetDataLists.size());
        // 2.生成数据到Excel对象
        for (int i = 0; i < sheetDataLists.size(); i++) {
            // 3.填充多级标题
            HSSFSheet sheetAt = null;
            if (i==0){ //工作簿会自带一个sheet0
                sheetAt = workbook.getSheetAt(i);
            }else {
                sheetAt = workbook.createSheet();
            }
            createTitleRowList(sheetAt);
            System.out.println(sheetAt.getSheetName());
            // 4.填充数据
            if (objList!=null && objList.size()>0){
                createDataRowList(sheetAt,sheetDataLists.get(i));
            }
        }
    }

    /**
     * 检查标题行格式
     */
    private void checkTitleRows(){
        // 检查标题数据
        if (this.titleRowList==null || this.titleRowList.size()==0){
            throw new RuntimeException("标题参数错误");
        }
        // 初始化标题行数
        this.titleRowsNum = titleRowList.size();
        //检查多级标题 每行总列数需相同
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        // 初始化最大列数
        for (int i = 0; i < titleRowList.size(); i++) {
            // 取出一行数据
            int count = 0;
            for (CellProperty cellProperty : titleRowList.get(i)) {
                // 取出每行的标题数的运算求出每行列数
                if (cellProperty.getEndCol()!=null){
                    count += (cellProperty.getEndCol()-cellProperty.getStartCol()+1);
                }
            }
            // 如果是最后一行标题，不会设置endCol,此时总列数等于数组的长度
            if (i==titleRowList.size()-1){
                count = titleRowList.get(i).length;
            }
            set.add(count);
        }
        if (set.size()>1){
            System.out.println(set);
            throw new RuntimeException("初始化标题最大列数错误,请检查："+set);
        }
        this.maxColNum = (int) set.toArray()[0];
    }

    /**
     * 检查数据列数是否和标题行相同
     */
    private void checkDataColumn(){
        if (this.objList !=null && this.objList.size()>0){
            T t = this.objList.get(0);
            Class<?> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            if (declaredFields.length!=this.maxColNum){
                System.out.println("数据列数和最大列数不匹配,数据列数："+declaredFields.length);
                throw new RuntimeException("初始化标题最大列数错误,请检查declaredFields："+declaredFields.length);
            }
        }

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
        // 2 生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet();
        // 3 设置表格列宽度
        sheet.setDefaultColumnWidth(DefaultColumnWidth);
        // 4 设置表格样式
        HSSFCellStyle style  = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置垂直对齐的样式为居中对齐;

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        this.cellStyle = style;
        return workbook;
    }

    /**
     * 填充标题到sheet
     * @param sheet sheet
     */
    public HSSFSheet createTitleRowList(HSSFSheet sheet){
        for (CellProperty[] cellProperties : this.titleRowList) { //拿到每一行的菜单单元格数组
            // 填充标题内容到sheet
            Row row = sheet.getRow(startRowIndex);
            if(row==null){
                row = sheet.createRow(startRowIndex);
            }
            int beginColIndex = startColIndex;
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
    /**设置表格样式*/
    private void setCellStyle(CellProperty cP ,Cell cell){
        if (cP.getCellStyle() == null) {
            cell.setCellStyle(cellStyle);
        } else {
            cell.setCellStyle(cP.getCellStyle());
        }
    }

    /**
     * 添加列表数据到sheet
     * 将标题行最后一行标题单元格的title和T类型数据的属性一一映射
     * @param sheet sheet
     */
    public HSSFSheet createDataRowList(HSSFSheet sheet,List<T> dataList) {
        System.out.println("填充数据量："+dataList.size());
        // 1. 遍历数据集合
        for (T t : dataList) {
            Class<?> clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
        // 2.获取要插入的每行对象的属性集合进行遍历
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                //拿到一个属性值
                if (!fields[i].isAnnotationPresent(ExcelAnno.class)) {
                    throw new RuntimeException("当前属性："+fields[i]+"缺少ExcelAnno注解");
                }
        // 3.获取要插入单元格的值
                ExcelAnno anno = fields[i].getAnnotation(ExcelAnno.class);
                String value = null;
                try {
                    value = String.valueOf(fields[i].get(t));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Row row = sheet.getRow(startRowIndex);
                if(row==null){
                    row = sheet.createRow(startRowIndex);
                }
                Cell cell = row.createCell(anno.index()); //注解的index即插入的列值
                cell.setCellValue(value);//TODO 这个没做类型判断
                cell.setCellStyle(cellStyle);
            }
            startRowIndex++; //添加一行数据行数递加
        }
        //创建完一个sheet,将其实行复原
        startRowIndex = 0;
        return sheet;
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

            }
           return false;
        }
        return true;
    }




    public static void main(String[] args) throws IllegalAccessException {
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
//      第四行菜单栏
        CellProperty[] cellProperties4 = new CellProperty[10];
        CellProperty cellProperty41 = new CellProperty("第4行菜单栏01");
        CellProperty cellProperty42 = new CellProperty("第4行菜单栏02");
        CellProperty cellProperty43 = new CellProperty("第4行菜单栏03");
        CellProperty cellProperty44 = new CellProperty("第4行菜单栏04");
        CellProperty cellProperty45 = new CellProperty("第4行菜单栏05");
        CellProperty cellProperty46 = new CellProperty("第4行菜单栏06");
        CellProperty cellProperty47 = new CellProperty("第4行菜单栏07");
        CellProperty cellProperty48 = new CellProperty("第4行菜单栏08");
        CellProperty cellProperty49 = new CellProperty("第4行菜单栏09");
        CellProperty cellProperty410 = new CellProperty("第4行菜单栏10");
        cellProperties4[0] = cellProperty41;
        cellProperties4[1] = cellProperty42;
        cellProperties4[2] = cellProperty43;
        cellProperties4[3] = cellProperty44;
        cellProperties4[4] = cellProperty45;
        cellProperties4[5] = cellProperty46;
        cellProperties4[6] = cellProperty47;
        cellProperties4[7] = cellProperty48;
        cellProperties4[8] = cellProperty49;
        cellProperties4[9] = cellProperty410;
        cellProperties.add(cellProperties4);
//
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            users.add( new User("a","b","c","d","e","f","g","h",123.456,i));
        }
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        PoiExportManager manager = new PoiExportManager(15,cellProperties,users);
//        manager.createExcel("D://poitest","123456.xlsx");
        manager.createExcelInputStream("D://poitest","123456.xlsx",null);
        System.out.println("耗时："+(System.currentTimeMillis()-begin)+"ms");

    }

}
