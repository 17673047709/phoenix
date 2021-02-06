package com.boot.security.server.dao;

import com.boot.security.server.model.ProductBaseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductBaseInfoDao {

    @Select("select * from product_base_info t where t.id = #{id}")
    ProductBaseInfo getById(Long id);

    @Delete("delete from product_base_info where id = #{id}")
    int delete(Long id);

    int update(ProductBaseInfo productBaseInfo);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into product_base_info(body, create_time, note, notify_url, pay_type, pay_way, product_id, status, tenant_uuid, total_fee, update_time) values(#{body}, #{createTime}, #{note}, #{notifyUrl}, #{payType}, #{payWay}, #{productId}, #{status}, #{tenantUuid}, #{totalFee}, #{updateTime})")
    int save(ProductBaseInfo productBaseInfo);
    
    int count(@Param("params") Map<String, Object> params);

    List<ProductBaseInfo> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
