package com.csdn.dt.kafka.mapper;

import com.csdn.dt.kafka.model.Transaction;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transactions (seller_id,buyer_id,amount) VALUES ( #{sellerId}, #{buyerId}, #{amount})")
    @Options(useGeneratedKeys = true, keyProperty = "xid", keyColumn = "xid")
    Long insertTransaction(Transaction transaction);

    @Select("SELECT * FROM transactions WHERE xid = #{id}")
    Transaction getTransactionById(@Param("id") String id);

    @Update("UPDATE transactions SET status = #{status} WHERE xid = #{id}")
    void updateTransactionStatus(@Param("id") String id, @Param("status") String status);

    @Delete("DELETE FROM transactions WHERE xid = #{id}")
    void deleteTransaction(@Param("id") String id);
}
