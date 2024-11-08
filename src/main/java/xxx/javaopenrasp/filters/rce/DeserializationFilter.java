package xxx.javaopenrasp.filters.rce;

import xxx.javaopenrasp.config.Config;
import xxx.javaopenrasp.filters.SecurityFilterI;
import xxx.javaopenrasp.util.Console;
import xxx.javaopenrasp.util.StackTrace;

import java.io.ObjectStreamClass;

/**
 * 
 */
public class DeserializationFilter implements SecurityFilterI {

    @Override
    public boolean filter(Object forCheck) {
        String moudleName = "java/io/ObjectInputStream";
        ObjectStreamClass desc = (ObjectStreamClass) forCheck;
        String className = desc.getName();
        String mode = (String) Config.moudleMap.get(moudleName).get("mode");
        switch (mode) {
            case "block":
                Console.log("block: " + className);
                return false;
            case "white":
                if (Config.isWhite(moudleName, className)) {
                    Console.log("pass: " + className);
                    return true;
                }
                Console.log("block:" + className);
                return false;
            case "black":
                if (Config.isBlack(moudleName, className)) {
                    Console.log("block: " + className);
                    return false;
                }
                Console.log("pass: " + className);
                return true;
            case "log":
            default:
                Console.log("pass: " + className);
                Console.log("log stack trace:\r\n" + StackTrace.getStackTrace());
                return true;
        }
    }
}
