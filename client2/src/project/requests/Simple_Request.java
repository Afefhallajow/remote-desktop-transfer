package project.requests;

import project.project_info;

import java.util.Map;

public class Simple_Request extends Request{

    public Simple_Request() {
        super("simple");
    }

    @Override
    public void add(Object object) {
        objects=object;
    }
}
