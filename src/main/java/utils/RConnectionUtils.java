package utils;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RConnectionUtils {

    private volatile static RConnectionUtils rConnectionUtils;

    private RConnectionUtils() {

    }

    public static RConnectionUtils getRConnectionUtils() {
        if (rConnectionUtils == null) {
            synchronized (RConnectionUtils.class) {
                if (rConnectionUtils == null) {
                    rConnectionUtils = new RConnectionUtils();
                }
            }
        }
        return rConnectionUtils;
    }

    public RConnection getConnection() throws RserveException {

        RConnection connection = new RConnection();
        ExcuteFunction(connection);
        return connection;
    }

    private void ExcuteFunction(RConnection connection) throws RserveException {
        String rPath = "/Users/lwj/testR.R";
        connection.assign("fileName", rPath);
        connection.eval("source(fileName)");
        int num = 9;
        REXP rexp = connection.eval("myFunc(" + num + ")");
        System.out.println(rexp);
        connection.close();
    }


}
