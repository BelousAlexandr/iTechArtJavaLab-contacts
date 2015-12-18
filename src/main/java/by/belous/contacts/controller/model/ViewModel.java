package by.belous.contacts.controller.model;

import java.util.Map;

public class ViewModel {
    private Map<String, Object> model;
    private String forward;

    public ViewModel() {}

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

}
