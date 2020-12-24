package duplicateremoval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**去除集合对象中的重复数据*/
public class DuplicateRemoval {

    //    如果对象的姓名和出生日期相同则视为重复数据 并将年龄相加
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Student a = new Student("zs",18,df.parse("2020-10-01"));
        Student b = new Student("zs",2,df.parse("2020-10-01"));
        Student c = new Student("ls",18,df.parse("2020-10-03"));
        Student d = new Student("ww",18,df.parse("2020-10-04"));
        Student e = new Student("zl",18,df.parse("2020-10-05"));
        List<Student> students = Arrays.asList(a,b,c,d,e);
        List<Student> signStudents = new ArrayList<>();
        students.parallelStream().collect(Collectors.groupingBy(o->(o.getName()+o.getBirth()),Collectors.toList())).forEach(
                (id,transfer)->{
                    transfer.stream().reduce((x,y)-> new Student(x.getName(),x.getAge()+y.getAge(),x.getBirth())).ifPresent(
                            signStudents::add
                    );
                }
        );
        System.out.println(signStudents);
    }
}
