package project.transforms;

import java.util.HashMap;
import java.util.Map;

public class Simple_Transform extends Transform {

    public Simple_Transform() {
        super();
    }

    @Override
    public void do_transform() {
        Map<String,String> data=new HashMap<>((Map) request.getObjects());
        message.setService_name(data.remove("service_name"));

        message.setParameters(data);

    }
}
