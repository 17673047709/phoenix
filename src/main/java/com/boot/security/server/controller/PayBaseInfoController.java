package com.boot.security.server.controller;

import com.boot.security.server.dao.PayBaseInfoDao;
import com.boot.security.server.model.PayBaseInfo;
import com.boot.security.server.page.table.PageTableHandler;
import com.boot.security.server.page.table.PageTableHandler.CountHandler;
import com.boot.security.server.page.table.PageTableHandler.ListHandler;
import com.boot.security.server.page.table.PageTableRequest;
import com.boot.security.server.page.table.PageTableResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payBaseInfos")
public class PayBaseInfoController {

    @Autowired(required = false)
    private PayBaseInfoDao payBaseInfoDao;

    @PostMapping
    @ApiOperation(value = "保存")
    public PayBaseInfo save(@RequestBody PayBaseInfo payBaseInfo) {
        payBaseInfoDao.save(payBaseInfo);

        return payBaseInfo;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public PayBaseInfo get(@PathVariable Long id) {
        return payBaseInfoDao.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    public PayBaseInfo update(@RequestBody PayBaseInfo payBaseInfo) {
        payBaseInfoDao.update(payBaseInfo);

        return payBaseInfo;
    }

    @GetMapping
    @ApiOperation(value = "列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return payBaseInfoDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<PayBaseInfo> list(PageTableRequest request) {
                return payBaseInfoDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        payBaseInfoDao.delete(id);
    }
}
