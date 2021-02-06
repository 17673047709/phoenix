package com.boot.security.server.controller;

import com.boot.security.server.dao.TenantBaseInfoDao;
import com.boot.security.server.model.TenantBaseInfo;
import com.boot.security.server.page.table.PageTableHandler;
import com.boot.security.server.page.table.PageTableHandler.CountHandler;
import com.boot.security.server.page.table.PageTableHandler.ListHandler;
import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.page.table.PageTableResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tenantBaseInfos")
public class TenantBaseInfoController {

    @Autowired(required = false)
    private TenantBaseInfoDao tenantBaseInfoDao;

    @PostMapping
    @ApiOperation(value = "保存")
    public TenantBaseInfo save(@RequestBody TenantBaseInfo tenantBaseInfo) {
        String uuid = UUID.randomUUID().toString();
        tenantBaseInfo.setUuid(uuid);
        tenantBaseInfoDao.save(tenantBaseInfo);

        return tenantBaseInfo;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public TenantBaseInfo get(@PathVariable Long id) {
        return tenantBaseInfoDao.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    public TenantBaseInfo update(@RequestBody TenantBaseInfo tenantBaseInfo) {
        tenantBaseInfoDao.update(tenantBaseInfo);

        return tenantBaseInfo;
    }

    @GetMapping
    @ApiOperation(value = "列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return tenantBaseInfoDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<TenantBaseInfo> list(PageTableRequest request) {
                return tenantBaseInfoDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        tenantBaseInfoDao.delete(id);
    }
}
