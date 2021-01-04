package test;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import utils.RConnectionUtils;

public class TestR {
    public static void main(String[] args) throws REngineException {
        RConnection connection = new RConnection();
        String rPath = "/Users/lwj/testR.R";
        connection.assign("fileName", rPath);
        //执行test.R脚本，执行这一步才能调用里面的自定义函数myFunc，如果不行，就在R工具上也执行一下test.R脚本
        connection.eval("source(fileName)");
        int num = 9;
        REXP rexp = connection.eval("myFunc(" + num + ")");
        System.out.println(rexp);
        connection.close();

    }
}
