
DM JDBC 驱动 jar 包在 DM 安装目录 /dmdbms/drivers/jdbc，如下图所示：

D:\installsoft\dmdbms\drivers\jdbc


/***************************************
 * 达梦8 JDBC驱动版本说明
/***************************************
1. DmJdbcDriver16 对应 Jdk1.6 及以上环境
2. DmJdbcDriver17 对应 Jdk1.7 及以上环境
3. DmJdbcDriver18 对应 Jdk1.8 及以上环境



/***************************************
 * 达梦8 hibernate方言包对应版本说明
/***************************************
jar包在dialect目录下:
1. DmDialect-for-hibernate2.0.jar  对应 Jdk1.4及以上, hibernate2.0 环境
2. DmDialect-for-hibernate2.1.jar  对应 Jdk1.4及以上, hibernate2.1 -- 2.X 环境
3. DmDialect-for-hibernate3.0.jar  对应 Jdk1.4及以上, hibernate3.0 环境
4. DmDialect-for-hibernate3.1.jar  对应 Jdk1.4及以上, hibernate3.1 -- 3.5 环境
5. DmDialect-for-hibernate3.6.jar  对应 Jdk1.5及以上, hibernate3.6 -- 3.X 环境
6. DmDialect-for-hibernate4.0.jar  对应 Jdk1.6及以上, hibernate4.0 -- 4.X 环境
7. DmDialect-for-hibernate5.0.jar  对应 Jdk1.7及以上, hibernate5.0 -- 5.2 环境
8. DmDialect-for-hibernate5.3.jar  对应 Jdk1.7及以上, hibernate5.3 环境
8. DmDialect-for-hibernate5.4.jar  对应 Jdk1.7及以上, hibernate5.4 环境
8. DmDialect-for-hibernate5.5.jar  对应 Jdk1.7及以上, hibernate5.5 环境
8. DmDialect-for-hibernate5.6.jar  对应 Jdk1.7及以上, hibernate5.6 环境

注1：以上的hibernate版本指的是hibernate ORM版本，注意区分hibernate search版本



/***************************************
 * Hibernate.cfg.xml配置说明
/***************************************
1、驱动名称
	<property name="connection.driver_class">dm.jdbc.driver.DmDriver</property>

2、方言包名称
	<property name="dialect">org.hibernate.dialect.DmDialect</property>



/***************************************
 * 其他jar包说明
/***************************************
1. DmDictionary.jar    				openjpa1.2方言包，对应 Jdk1.6及以上环境
2. dmjooq-dialect-3.12.3.jar     	jooq方言包，对应 Jdk1.8及以上环境
3. dm8-oracle-jdbc16-wrapper.jar    oracle 到达梦的JDBC驱动桥接，应用中如果使用了非标准的oracle JDBC特有的对象，无需修改应用代码，可以桥接到达梦的JDBC连接达梦数据库，对应 Jdk1.6及以上环境
4. DmHibernateSpatial-1.0.jar       hibernate spatial方言包，对应hibernate spatial 1.0环境，对应 Jdk1.5及以上环境
5. DmHibernateSpatial-1.1.jar       hibernate spatial方言包，对应hibernate spatial 1.1环境，对应 Jdk1.5及以上环境
6. gt-dameng-2.8.jar                GeoServer 2.8环境方言包，对应 Jdk1.6及以上环境
7. gt-dameng-2.11.jar               GeoServer 2.11环境方言包，对应 Jdk1.8及以上环境
8. gt-dameng-2.15.jar               GeoServer 2.15环境方言包，对应 Jdk1.8及以上环境



/***************************************
 * maven仓库下载
/***************************************
group id: com.dameng
maven依赖配置示例: 
        <dependency>
            <groupId>com.dameng</groupId>
            <artifactId>DmJdbcDriver18</artifactId>
            <version>8.1.1.193</version>
        </dependency>
