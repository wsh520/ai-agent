package com.wl.myai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wl.myai.DO.mysqlDO.Appointment;

public interface AppointmentService extends IService<Appointment> {

    Appointment getOne(Appointment appointment);

}
