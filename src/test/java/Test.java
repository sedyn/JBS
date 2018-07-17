import message.Version;

public class Test {
    @org.junit.Test
    public void VersionTest() throws Exception{
        Version a = new Version("123.125.123.123", 8333);
        System.out.println(a.toString());
        Version b = new Version(a.serialize());
        System.out.println(b.toString());
    }
}
