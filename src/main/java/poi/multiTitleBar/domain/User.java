package poi.multiTitleBar.domain;

import poi.multiTitleBar.util.ExcelAnno;

public class User {

    @ExcelAnno(index = 0 ,value = "第4行菜单栏01")
    private String var01;
    @ExcelAnno(index = 1 ,value = "第4行菜单栏02")
    private String var02;
    @ExcelAnno(index = 2 ,value = "第4行菜单栏03")
    private String var03;
    @ExcelAnno(index = 3 ,value = "第4行菜单栏04")
    private String var04;
    @ExcelAnno(index = 4 ,value = "第4行菜单栏05")
    private String var05;
    @ExcelAnno(index = 5 ,value = "第4行菜单栏06")
    private String var06;
    @ExcelAnno(index = 6 ,value = "第4行菜单栏07")
    private String var07;
    @ExcelAnno(index = 7 ,value = "第4行菜单栏08")
    private String var08;
    @ExcelAnno(index = 8 ,value = "第4行菜单栏09")
    private Double var09;
    @ExcelAnno(index = 9 ,value = "第4行菜单栏10")
    private Integer var010;

    public User() {
    }

    public User(String var01, String var02, String var03, String var04, String var05, String var06, String var07, String var08, Double var09, Integer var010) {
        this.var01 = var01;
        this.var02 = var02;
        this.var03 = var03;
        this.var04 = var04;
        this.var05 = var05;
        this.var06 = var06;
        this.var07 = var07;
        this.var08 = var08;
        this.var09 = var09;
        this.var010 = var010;
    }

    public String getVar01() {
        return var01;
    }

    public void setVar01(String var01) {
        this.var01 = var01;
    }

    public String getVar02() {
        return var02;
    }

    public void setVar02(String var02) {
        this.var02 = var02;
    }

    public String getVar03() {
        return var03;
    }

    public void setVar03(String var03) {
        this.var03 = var03;
    }

    public String getVar04() {
        return var04;
    }

    public void setVar04(String var04) {
        this.var04 = var04;
    }

    public String getVar05() {
        return var05;
    }

    public void setVar05(String var05) {
        this.var05 = var05;
    }

    public String getVar06() {
        return var06;
    }

    public void setVar06(String var06) {
        this.var06 = var06;
    }

    public String getVar07() {
        return var07;
    }

    public void setVar07(String var07) {
        this.var07 = var07;
    }

    public String getVar08() {
        return var08;
    }

    public void setVar08(String var08) {
        this.var08 = var08;
    }

    public Double getVar09() {
        return var09;
    }

    public void setVar09(Double var09) {
        this.var09 = var09;
    }

    public Integer getVar010() {
        return var010;
    }

    public void setVar010(Integer var010) {
        this.var010 = var010;
    }
}
