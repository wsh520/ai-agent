package com.wl.myai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wl.myai.DO.mysqlDO.Appointment;
import com.wl.myai.mapper.AppointmentMapper;
import com.wl.myai.service.AppointmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    @Resource
    private AppointmentMapper appointmentMapper;

    /**
     * 查询订单是否存在
     *
     * @param appointment
     * @return
     */
    @Override
    public Appointment getOne(Appointment appointment) {

        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getUsername, appointment.getUsername());
        queryWrapper.eq(Appointment::getIdCard, appointment.getIdCard());
        queryWrapper.eq(Appointment::getDepartment, appointment.getDepartment());
        queryWrapper.eq(Appointment::getDate, appointment.getDate());
        queryWrapper.eq(Appointment::getTime, appointment.getTime());

        return baseMapper.selectOne(queryWrapper);
    }

}
