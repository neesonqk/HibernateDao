package test.test.pojo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Nesson on 3/29/2014.
 */
@Entity
@Table(name="master")
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer master_id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="master", cascade = CascadeType.ALL,orphanRemoval=true)
    private List<Pet> pets;

    private String master_name;

    public Integer getMaster_id() {
        return master_id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void setMaster_id(Integer master_id) {
        this.master_id = master_id;
    }

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }
}
