2021-02-06 09:13:52,286 [http-nio-8090-exec-1] ==>  Preparing: select * from sys_user t where t.username = ? 
2021-02-06 09:13:52,295 [http-nio-8090-exec-1] ==> Parameters: admin(String)
2021-02-06 09:13:52,360 [http-nio-8090-exec-1] <==      Total: 1
2021-02-06 09:13:52,370 [http-nio-8090-exec-1] ==>  Preparing: select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = ? order by p.sort 
2021-02-06 09:13:52,370 [http-nio-8090-exec-1] ==> Parameters: 1(Long)
2021-02-06 09:13:52,404 [http-nio-8090-exec-1] <==      Total: 42
2021-02-06 09:13:52,550 [taskExecutor-1] ==>  Preparing: insert into sys_logs(userId, module, flag, remark, createTime) values(?, ?, ?, ?, now()) 
2021-02-06 09:13:52,555 [taskExecutor-1] ==> Parameters: 1(Long), 登陆(String), true(Boolean), null
2021-02-06 09:13:52,610 [taskExecutor-1] <==    Updates: 1
2021-02-06 09:13:52,776 [http-nio-8090-exec-6] ==>  Preparing: select count(1) from t_notice t left join t_notice_read r on r.noticeId = t.id and r.userId = ? where t.status = 1 and r.userId is null 
2021-02-06 09:13:52,776 [http-nio-8090-exec-6] ==> Parameters: 1(Long)
2021-02-06 09:13:52,803 [http-nio-8090-exec-6] <==      Total: 1
2021-02-06 09:13:55,627 [http-nio-8090-exec-8] ==>  Preparing: select count(1) from file_info t 
2021-02-06 09:13:55,628 [http-nio-8090-exec-8] ==> Parameters: 
2021-02-06 09:13:55,654 [http-nio-8090-exec-8] <==      Total: 1
2021-02-06 09:13:55,674 [http-nio-8090-exec-8] ==>  Preparing: select * from file_info t order by updateTime desc limit ?, ? 
2021-02-06 09:13:55,674 [http-nio-8090-exec-8] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:13:55,700 [http-nio-8090-exec-8] <==      Total: 1
2021-02-06 09:14:05,623 [http-nio-8090-exec-8] ==>  Preparing: select * from t_dict t where t.type = ? 
2021-02-06 09:14:05,624 [http-nio-8090-exec-8] ==> Parameters: noticeStatus(String)
2021-02-06 09:14:05,651 [http-nio-8090-exec-8] <==      Total: 2
2021-02-06 09:14:05,723 [http-nio-8090-exec-10] ==>  Preparing: select count(1) from t_notice t 
2021-02-06 09:14:05,724 [http-nio-8090-exec-10] ==> Parameters: 
2021-02-06 09:14:05,750 [http-nio-8090-exec-10] <==      Total: 1
2021-02-06 09:14:05,751 [http-nio-8090-exec-10] ==>  Preparing: select * from t_notice t order by updateTime desc, title desc limit ?, ? 
2021-02-06 09:14:05,752 [http-nio-8090-exec-10] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:14:05,780 [http-nio-8090-exec-10] <==      Total: 1
2021-02-06 09:14:08,230 [http-nio-8090-exec-2] ==>  Preparing: select * from t_dict t where t.type = ? 
2021-02-06 09:14:08,231 [http-nio-8090-exec-2] ==> Parameters: userStatus(String)
2021-02-06 09:14:08,258 [http-nio-8090-exec-2] <==      Total: 3
2021-02-06 09:14:08,305 [http-nio-8090-exec-7] ==>  Preparing: select count(1) from sys_user t 
2021-02-06 09:14:08,306 [http-nio-8090-exec-7] ==> Parameters: 
2021-02-06 09:14:08,331 [http-nio-8090-exec-7] <==      Total: 1
2021-02-06 09:14:08,333 [http-nio-8090-exec-7] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2021-02-06 09:14:08,333 [http-nio-8090-exec-7] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:14:08,362 [http-nio-8090-exec-7] <==      Total: 3
2021-02-06 09:16:12,949 [http-nio-8090-exec-3] ==>  Preparing: select count(1) from sys_user t 
2021-02-06 09:16:12,949 [http-nio-8090-exec-3] ==> Parameters: 
2021-02-06 09:16:12,975 [http-nio-8090-exec-3] <==      Total: 1
2021-02-06 09:16:12,976 [http-nio-8090-exec-3] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2021-02-06 09:16:12,977 [http-nio-8090-exec-3] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:16:13,004 [http-nio-8090-exec-3] <==      Total: 3
2021-02-06 09:16:30,355 [http-nio-8090-exec-7] ==>  Preparing: select count(1) from sys_user t 
2021-02-06 09:16:30,356 [http-nio-8090-exec-7] ==> Parameters: 
2021-02-06 09:16:30,407 [http-nio-8090-exec-7] <==      Total: 1
2021-02-06 09:16:30,410 [http-nio-8090-exec-7] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2021-02-06 09:16:30,412 [http-nio-8090-exec-7] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:16:30,443 [http-nio-8090-exec-7] <==      Total: 3
2021-02-06 09:17:11,080 [http-nio-8090-exec-5] ==>  Preparing: select count(1) from t_notice t left join t_notice_read r on r.noticeId = t.id and r.userId = ? where t.status = 1 and r.userId is null 
2021-02-06 09:17:11,080 [http-nio-8090-exec-5] ==> Parameters: 1(Long)
2021-02-06 09:17:11,106 [http-nio-8090-exec-5] <==      Total: 1
2021-02-06 09:17:15,139 [http-nio-8090-exec-6] ==>  Preparing: select count(1) from sys_user t 
2021-02-06 09:17:15,139 [http-nio-8090-exec-6] ==> Parameters: 
2021-02-06 09:17:15,192 [http-nio-8090-exec-6] <==      Total: 1
2021-02-06 09:17:15,193 [http-nio-8090-exec-6] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2021-02-06 09:17:15,193 [http-nio-8090-exec-6] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:17:15,220 [http-nio-8090-exec-6] <==      Total: 3
2021-02-06 09:19:18,153 [http-nio-8090-exec-7] ==>  Preparing: select count(1) from t_notice t left join t_notice_read r on r.noticeId = t.id and r.userId = ? where t.status = 1 and r.userId is null 
2021-02-06 09:19:18,162 [http-nio-8090-exec-7] ==> Parameters: 1(Long)
2021-02-06 09:19:18,198 [http-nio-8090-exec-7] <==      Total: 1
2021-02-06 09:19:26,126 [http-nio-8090-exec-4] ==>  Preparing: select count(1) from sys_user t 
2021-02-06 09:19:26,126 [http-nio-8090-exec-4] ==> Parameters: 
2021-02-06 09:19:26,154 [http-nio-8090-exec-4] <==      Total: 1
2021-02-06 09:19:26,157 [http-nio-8090-exec-4] ==>  Preparing: select * from sys_user t order by username desc, nickname asc limit ?, ? 
2021-02-06 09:19:26,157 [http-nio-8090-exec-4] ==> Parameters: 0(Integer), 10(Integer)
2021-02-06 09:19:26,188 [http-nio-8090-exec-4] <==      Total: 3
2021-02-06 10:13:14,657 [http-nio-8090-exec-7] ==>  Preparing: select count(1) from t_notice t left join t_notice_read r on r.noticeId = t.id and r.userId = ? where t.status = 1 and r.userId is null 
2021-02-06 10:13:14,721 [http-nio-8090-exec-7] ==> Parameters: 1(Long)
2021-02-06 10:13:14,746 [http-nio-8090-exec-7] <==      Total: 1
