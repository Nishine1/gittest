package entity;

import java.util.Date;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-05-18 18:38:09
 **/
public class Goods {
    private int id;
    private String name;
    private Double price;
    private Date launch_date;

    public Goods(int id, String name, Double price, Date launch_date) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.launch_date = launch_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", launch_date=" + launch_date +
                '}';
    }
}
