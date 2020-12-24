package design_pattern.proxy;

public class ProxyDB implements UserInfo {

    private RealDB realDB;

    @Override
    public void showInfo() {
            new RealDB().showInfo();
    }
}
