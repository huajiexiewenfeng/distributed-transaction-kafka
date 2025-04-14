package com.csdn.dt.kafka.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Update("UPDATE users SET amt_sold = amt_sold + #{amt} WHERE id = #{id}")
    void updateAmountSold(@Param("id") Long id, @Param("amt") Integer amt);

    @Update("UPDATE users SET amt_bought = amt_bought + #{amt} WHERE id = #{id}")
    void updateAmountBought(@Param("id") Long id, @Param("amt") Integer amt);

}
