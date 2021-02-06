package com.boot.security.server.dao;

import com.boot.security.server.model.TenantBaseInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface TenantBaseInfoDao {

    @Select("select * from tenant_base_info t where t.id = #{id}")
    TenantBaseInfo getById(Long id);

    @Delete("delete from tenant_base_info where id = #{id}")
    int delete(Long id);

    int update(TenantBaseInfo tenantBaseInfo);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into tenant_base_info(api_key, app_id, create_time, mch_id, mch_name, note, status, update_time, uuid) values(#{apiKey}, #{appId}, #{createTime}, #{mchId}, #{mchName}, #{note}, #{status}, #{updateTime}, #{uuid})")
    int save(TenantBaseInfo tenantBaseInfo);
    
    int count(@Param("params") Map<String, Object> params);

    List<TenantBaseInfo> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
