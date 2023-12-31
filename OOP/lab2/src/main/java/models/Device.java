package main.java.models;

public class Device {
    private String id;
    private String name;
    private String origin; //country
    private Integer price;
    private Boolean critical; //is it critical to have a computer component for operation?
    private Types types;

    public Device() {}
    public String getName() {return name;}
    public void setName(String value) {this.name = value;}

    public String getOrigin() {return origin;}
    public void setOrigin(String value) {this.origin = value;}

    public Integer getPrice() {return price;}
    public void setPrice(Integer value) {this.price = value;}
    public Types getTypes() {
        if (types == null) {
            types = new Types();
        }
        return types;
    }
    public void setTypes(Types value) {this.types = value;}

    public boolean isCritical() {return critical;}
    public void setCritical(boolean value) {this.critical = value;}

    public String getId() {return id;}
    public void setId(String value) {this.id = value;}
}
