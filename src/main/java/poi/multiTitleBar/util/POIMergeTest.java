package poi.multiTitleBar.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POIMergeTest {

    public static void main(String[] args) throws IOException {
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//        //垂直居中
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//        HSSFSheet sheet = workbook.createSheet();
//        HSSFRow row = sheet.createRow(0);
//        HSSFCell cell = row.createCell(1);
//        cell.setCellValue("99233");
//        cell.setCellStyle(cellStyle);
//        CellRangeAddress cra = new CellRangeAddress(0, 0, 1, 3);
//        sheet.addMergedRegion(cra);
//        FileOutputStream stream = new FileOutputStream(new File("D://poitest/abc.xls"));
//        workbook.write(stream);
//        stream.close();
//        workbook.close();
        List<Integer> list =new ArrayList<>();
        for (int i = 0; i < 103; i++) {
            list.add(i);
        }
        int count = 1;
        int maxNum = 10;
        int totalPage = (list.size()-1)/maxNum+1;

        while (true){
            int fromIndex = (count-1)*maxNum;
            int toIndex = (list.size()-fromIndex)>maxNum?fromIndex+maxNum:list.size();
            System.out.println(list.subList(fromIndex,toIndex));
            if (count==totalPage){
                break;
            }
            count++;
        }


    }
}
