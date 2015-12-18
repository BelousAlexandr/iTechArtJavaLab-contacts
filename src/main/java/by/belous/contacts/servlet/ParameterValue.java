package by.belous.contacts.servlet;

import javax.validation.constraints.NotNull;

public class ParameterValue {

    private Object value;

    public ParameterValue(@NotNull Object value) {
        if (value == null) {
            throw new RuntimeException("value can not be null");
        }
        this.value = value;
    }

    public Object get(){
       return value;
    }

    public Class<?> getType() {
       return value.getClass();
    }

    @Override
    public String toString() {
        return "ParameterValue{" +
                "value=" + value +
                '}';
    }
}
