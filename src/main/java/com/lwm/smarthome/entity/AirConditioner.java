package com.lwm.smarthome.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.Date;

//怎么配置二级缓存的策略怎么指定
//@Cacheable()
@Entity
@Table(name = "air_conditioner")
public class AirConditioner {
    private Long id;
    private String equipmentName;
    private boolean status;
    private Date createTime;
    private String producer;
    private String currTemperature;
    private String expTemperature;
    private SysUser sysUser;
    private Date addTime;
    private String macAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "equipment_name")
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    @Column(name = "status")
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "producer")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Column(name = "currTemperature")
    public String getCurrTemperature() {
        return currTemperature;
    }

    public void setCurrTemperature(String currTemperature) {
        this.currTemperature = currTemperature;
    }

    @Column(name = "addTime")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "expTemperature")
    public String getExpTemperature() {
        return expTemperature;
    }

    public void setExpTemperature(String expTemperature) {
        this.expTemperature = expTemperature;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "macAddress")
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @JoinColumn(name = "sysUser_id")
    @ManyToOne
    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }


}
