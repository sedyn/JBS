package test;

import connect.ConnectionManager;
import constant.Constants;

public class Test {
    public static void main (String[] args) throws Exception{
        ConnectionManager cm = new ConnectionManager();
        cm.start2(Constants.GOAL);
    }
}
