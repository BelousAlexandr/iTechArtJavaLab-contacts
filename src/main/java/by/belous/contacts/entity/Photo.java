package by.belous.contacts.entity;

public class Photo {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "name = " + name +
                '}';
    }
}
