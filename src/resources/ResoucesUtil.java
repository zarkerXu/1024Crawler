package resources;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xuzhi
 * @createTime 2018-03-04 20:17:04
 * @description
 */
public class ResoucesUtil {

    private static Map<String,String> rmap=new HashMap<>();

    public static String getResouces(String key){
        if(rmap.isEmpty()){
            initResources();
        }
        return rmap.get(key);
    }


    private static void initResources(){
        InputStream pInStream=null;
        try {
            pInStream = ResoucesUtil.class.getResourceAsStream("common.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Properties p = new Properties();
        try {
            p.load(pInStream );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration enu = p.propertyNames();
        while(enu.hasMoreElements())
        {
            String thisKey = (String)enu.nextElement();
            rmap.put(thisKey,p.getProperty(thisKey));
        }
    }
}
