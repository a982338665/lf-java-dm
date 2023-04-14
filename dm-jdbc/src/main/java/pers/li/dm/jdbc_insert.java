package pers.li.dm;

import java.sql.*;

public class jdbc_insert {
    // 定义 DM JDBC 驱动串
    static String jdbcString = "dm.jdbc.driver.DmDriver";
    // 定义 DM URL 连接串
    static String urlString = "jdbc:dm://localhost:5237";
    // 定义连接用户名
    static String userName = "SYSDBA";
    // 定义连接用户口令
    static String password = "password2";
    // 定义连接对象
    static Connection conn = null;
    // 定义 SQL 语句执行对象
    static Statement state = null;
    // 定义结果集对象
    static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            //1.加载 JDBC 驱动程序
            System.out.println("Loading JDBC Driver...");
            Class.forName(jdbcString);
            //2.连接 DM 数据库
            System.out.println("Connecting to DM Server...");
            conn = DriverManager.getConnection(urlString, userName, password);
            //3.通过连接对象创建 java.sql.Statement 对象
            state = conn.createStatement();
//-----------------------------------------------------------------------------
            //基础操作：此处对应的操作代码为示例库中 PRODUCTION 模式中的
            //PRODUCT_CATEGORY 表
            //增加
            //定义增加的 SQL 这里由于此表中的结构为主键，自增，只需插入 name 列的值
            String sql_insert = "insert into PRODUCTION.PRODUCT_CATEGORY" +
                    "(name)values('厨艺')";
            //执行添加的 SQL 语句
            state.execute(sql_insert);
            //删除
            //定义删除的 SQL 语句
            String sql_delete = "delete from PRODUCTION.PRODUCT_CATEGORY " +
                    "where name = '厨艺'";
            //执行删除的 SQL 语句
            state.execute(sql_delete);
            //修改
            String sql_update = "update PRODUCTION.PRODUCT_CATEGORY set " +
                    "name = '国学' where name = '文学'";
            //查询表中数据
            //定义查询 SQL
            String sql_selectAll = "select * from PRODUCTION.PRODUCT_CATEGORY";
            //执行查询的 SQL 语句
            rs = state.executeQuery(sql_selectAll);
            displayResultSet(rs);
//----------------------------------------------------------------------------
            state.executeUpdate(sql_update);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                rs.close();
                state.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //显示结果集
    public static void displayResultSet(ResultSet rs) throws SQLException {
        while (rs.next()) {
            int i = 1;
            Object id = rs.getObject(i++);
            Object name = rs.getObject(i++);
            System.out.println(id + "  " + name);
        }
    }
}
