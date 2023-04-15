
> https://eco.dameng.com/document/dm/zh-cn/app-dev/java_Mybatis_frame.html


3.1 数据对象准备

    数据库软件安装、实例的创建本文不做详细介绍，请查阅达梦在线服务平台相关文章。需要注意的是实例初始化时大小写敏感参数设置，快速设置使得在 mybatis 下对象不用添加双引号。
    说明：
        设置大小写不敏感，此方法会影响查询结果集，因为其对结果集匹配也不区分大小写，需要根据业务自行评估是否可行。
        在 mysql 数据库中，对象名默认是小写，达梦对象名默认是大写，在用达梦 DTS 工具迁移的时候，去掉”保持对象名大小写”的勾选，使对象名自动转换成大写，
        在 mybatis 查询的时候，即不需要加双引号强调小写。

3.2 创建数据表空间和用户模式
    
    在进行应用开发时建议创建单独的用户和表空间，为保障数据安全，禁止使用管理员用户 SYSDBA 作为应用系统的数据库用户。
    --创建用户表空间
    CREATE TABLESPACE PRODUCTION2 DATAFILE 'PRODUCTION2.DBF' SIZE 128 ;
    --创建用户
    CREATE USER PRODUCTION2 IDENTIFIED BY aaaaaaaaa;
    --设置用户默认表空间
    ALTER USER PRODUCTION2 DEFAULT TABLESPACE PRODUCTION2 DEFAULT INDEX TABLESPACE PRODUCTION2;
    --给用户给予必要权限
    GRANT RESOURCE,VTI,SOI TO PRODUCTION2;

3.3 创建表对象和初始数据

    --创建表普通对象
    CREATE TABLE PRODUCTION2.PRODUCT_CATEGORY
    (PRODUCT_CATEGORYID INT,
    NAME VARCHAR(100));
    --插入测试数据
    insert into PRODUCTION2.PRODUCT_CATEGORY(PRODUCT_CATEGORYID,NAME)
    values(1,'小说'),(2,'国学'),(3,'魔幻'),(4,'语文'),(5,'体育'),(7,'金融');
    --插入后数据库查询的数据如下：
    select * from PRODUCTION2.PRODUCT_CATEGORY;
