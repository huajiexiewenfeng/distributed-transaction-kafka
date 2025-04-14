package com.csdn.dt.kafka.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface TransactionMessageMapper {

    @Select("SELECT count(id) FROM tx_messages WHERE xid = #{xid} AND user_id = #{userId} AND amount = #{amount}")
    int getTransactionById(@Param("xid") Long id,
                               @Param("userId") Long userId,
                               @Param("amount") Integer amount);

    @Insert("INSERT INTO tx_messages(xid,user_id,amount) values(#{xid}, #{userId}, #{amount})")
    Long addTransactionMessage(@Param("xid") Long id,
                                 @Param("userId") Long userId,
                                 @Param("amount") Integer amount);

}
