package com.boot.security.server.controller;

import com.boot.security.server.dao.ProductBaseInfoDao;
import com.boot.security.server.model.ProductBaseInfo;
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
@RequestMapping("/productBaseInfos")
public class ProductBaseInfoController {

    @Autowired(required = false)
    private ProductBaseInfoDao productBaseInfoDao;

    @PostMapping
    @ApiOperation(value = "保存")
    public ProductBaseInfo save(@RequestBody ProductBaseInfo productBaseInfo) {
        String uuid = UUID.randomUUID().toString();
        productBaseInfo.setProductId(uuid);
        productBaseInfoDao.save(productBaseInfo);

        return productBaseInfo;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public ProductBaseInfo get(@PathVariable Long id) {
        return productBaseInfoDao.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "修改")
    public ProductBaseInfo update(@RequestBody ProductBaseInfo productBaseInfo) {
        productBaseInfoDao.update(productBaseInfo);

        return productBaseInfo;
    }

    @GetMapping
    @ApiOperation(value = "列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return productBaseInfoDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<ProductBaseInfo> list(PageTableRequest request) {
                return productBaseInfoDao.list(request.getParams(), request.getOffset(), request.getLimit());
            }
        }).handle(request);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        productBaseInfoDao.delete(id);
    }
}
