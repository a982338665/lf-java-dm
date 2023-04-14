package pers.li.dm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class jdbc_prepareStatement{
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
    static PreparedStatement pstate = null;
    public static void main(String[] args) {
        try {
            //1.加载 JDBC 驱动程序
            System.out.println("Loading JDBC Driver...");
            Class.forName(jdbcString);
            //2.连接 DM 数据库
            System.out.println("Connecting to DM Server...");
            conn = DriverManager.getConnection(urlString, userName, password);
//------------------------------------------------------------------------------------------------------------------------------
            //绑定操作：此处操作对应的数据库，为示例库中的 PRODUCTION 模式中的
            //PRODUCT_CATEGORY 表
            //根据指定 id 修改 PRODUCT_CATEGORY 表中的 name 的值
            //这里 name 和 id 不确认用?代替
            String sql_updateNameById = "update PRODUCTION.PRODUCT_CATEGORY "+
            "set name = ? where PRODUCT_CATEGORYID = ? ";
            //3.通过连接对象和修改语句的模板，创建 java.sql.PreparedStatement 对象
            pstate = conn.prepareStatement(sql_updateNameById);
            //4.绑定?对应的参数：理论上有多少个?就要绑定多少个值;
            //把 id 为 3 所对应的 name 值修改为 "魔幻"
            pstate.setString(1, "魔幻");
            pstate.setInt(2, 3);
            //5. 执行 SQL 语句
            pstate.executeUpdate();
//------------------------------------------------------------------------------------------------------------------------------
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstate.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
