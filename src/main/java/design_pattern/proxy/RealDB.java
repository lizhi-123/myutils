package design_pattern.proxy;

public class RealDB implements UserInfo{
    @Override
    public void showInfo() {
        System.out.println("从数据库读取用户id为1的用户信息");
    }
}
