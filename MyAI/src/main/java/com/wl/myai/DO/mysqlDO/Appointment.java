package com.wl.myai.DO.mysqlDO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointment")
public class Appointment {

    @TableId(type = IdType.AUTO)

    private Long id;

    private String username;

    private String idCard;

    private String department;

    private String date;

    private String time;

    private String doctorName;
}
