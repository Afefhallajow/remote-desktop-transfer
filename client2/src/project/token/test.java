package project.token;

public class test {
    public static void main(String[] args) throws InterruptedException {
        GetToken.get_instance().getTokenString();
        Thread.sleep(10000);
        GetToken.get_instance().getTokenString();
        Thread.sleep(60000);
        GetToken.get_instance().getTokenString();
    }
}
