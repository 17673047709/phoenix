package com.boot.security.server.dao;

import com.boot.security.server.model.PayBaseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayBaseInfoDao {

    @Select("select * from pay_base_info t where t.id = #{id}")
    PayBaseInfo getById(Long id);

    @Delete("delete from pay_base_info where id = #{id}")
    int delete(Long id);

    int update(PayBaseInfo payBaseInfo);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into pay_base_info(create_time, note, out_trade_no, request_xml, response_xml, status, subject, update_time) values(#{createTime}, #{note}, #{outTradeNo}, #{requestXml}, #{responseXml}, #{status}, #{subject}, #{updateTime})")
    int save(PayBaseInfo payBaseInfo);
    
    int count(@Param("params") Map<String, Object> params);

    List<PayBaseInfo> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
