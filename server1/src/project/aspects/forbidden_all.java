package project.aspects;

import project.errors.Error;

import java.util.List;
import java.util.Map;

public class forbidden_all extends Aspect{
    public forbidden_all(List<String> before_services, List<String> after_services, boolean before_all_services, boolean after_all_services, List<String> except) {
        super(before_services, after_services, before_all_services, after_all_services, except);
    }

    @Override
    public Object before(Object parameters) {
        return generate_error_map(Error.Forbidden.toString());
    }

    @Override
    public Object after(Object result) {
        return null;
    }
}
