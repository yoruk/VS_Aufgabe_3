package mware_lib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NameServiceImplTest {
    public static void main(String[] args) {
        Map<String, Object> index = new HashMap<String, Object>();
        NameServiceImpl nameService = null;
        System.out.println("vor dem try");
        try {
            nameService = new NameServiceImpl("192.168.100.101", 6666, index);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ObjectRef tmp_objRef_1 = new ObjectRef("1.2.3.4", 0, "test");
        nameService.rebind(tmp_objRef_1, "test");
        System.out.println(index.get("test"));    
        ObjectRef tmp_objRef_2 = (ObjectRef)nameService.resolve("test");
        System.out.println(tmp_objRef_2.getObjId());
    }
}
