package com.wl.myai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wl.myai.DO.mysqlDO.Appointment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
}
