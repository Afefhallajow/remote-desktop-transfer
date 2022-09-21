package project.responses;

import project.project_info;
import project.services.servicemessages.ServiceMessage;

public class Simple_Response extends Response {

    public Simple_Response() {
        super("simple");
    }

    @Override
    public void add(ServiceMessage serviceMessage) {
        super.service_response=serviceMessage;
    }
}
