package com.lwm.smarthome.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Rooms {
    private Long id;
    private String roomName;
    private Date createName;
    private SysUser sysUser;
    private Set<AirConditioner> airConditioners = new HashSet<>();
    private Set<Curtain> curtains = new HashSet<>();
    private Set<Freezer> freezers = new HashSet<>();
    private Set<Television> televisions = new HashSet<>();
    private Set<Lighter> lighters = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "room_name", unique = true)
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getCreateName() {
        return createName;
    }

    public void setCreateName(Date createName) {
        this.createName = createName;
    }

    @JoinColumn(name = "sys_user_id")
    @ManyToOne
    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="room")
    public Set<AirConditioner> getAirConditioners() {
        return airConditioners;
    }

    public void setAirConditioners(Set<AirConditioner> airConditioners) {
        this.airConditioners = airConditioners;
    }
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="room")
    public Set<Curtain> getCurtains() {
        return curtains;
    }

    public void setCurtains(Set<Curtain> curtains) {
        this.curtains = curtains;
    }
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="room")
    public Set<Freezer> getFreezers() {
        return freezers;
    }

    public void setFreezers(Set<Freezer> freezers) {
        this.freezers = freezers;
    }
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="room")
    public Set<Television> getTelevisions() {
        return televisions;
    }

    public void setTelevisions(Set<Television> televisions) {
        this.televisions = televisions;
    }
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="room")
    public Set<Lighter> getLighters() {
        return lighters;
    }

    public void setLighters(Set<Lighter> lighters) {
        this.lighters = lighters;
    }
}
