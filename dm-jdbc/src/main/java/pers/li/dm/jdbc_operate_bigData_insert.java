//1.插入大字段信息:
package pers.li.dm;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 大字段操作：
 * 创建需要操作的含大字段类型的数据表。
CREATE TABLE "PRODUCTION"."BIG_DATA"
 (
     "ID" INT IDENTITY(1, 1) NOT NULL,
     "PHOTO" IMAGE,
     "DESCRIBE" BLOB,
     "TXT" CLOB,
 NOT CLUSTER PRIMARY KEY("ID")) STORAGE(ON "BOOKSHOP", CLUSTERBTR) ;
 * 在 D 盘根目录下，创建 DM8 特点 .jpg、达梦产品简介 .txt 两个文件，作为大字段存储
 *  ----------------------------------
 * stream 以字节输入流的形式，插入 Image 类型的字段，数据库保存的是图片信息。
 * Clob 以字节输入流的形式，插入 Clob 类型的字段，数据库保存的是图片信息。
 * Blob 以字符输入流的形式，插入 Blob 类型的字段，数据库表中保存的是文本信息。
 *
  select  * from "PRODUCTION"."BIG_DATA"
 */
public class jdbc_operate_bigData_insert {
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
//------------------------------------------------------------------------------------------------------------------------
            //1.插入大字段信息:
            String sql_insert = "INSERT INTO production.BIG_DATA (\"PHOTO\","
                    + "\"describe\",\"txt\")VALUES(?,?,?);";
            pstate = conn.prepareStatement(sql_insert);
            //加载图片为输入流
            String filePath = "./img/1.png";
            File file = new File(filePath);
            String filePath2 = "./img/1.txt";
            File file2 = new File(filePath2);
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            InputStream in2 = new BufferedInputStream(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file2), "UTF-8"));
            //1.绑定 stream 流信息到第一个?
            pstate.setBinaryStream(1, in);
            //2.绑定 Inputstream 对象到第二个?这里
            pstate.setBlob(2, in2);
            //3.绑定 Reader 对象到第三个?
            pstate.setClob(3, reader);
            pstate.executeUpdate();
            //------------------------------------------------------------------------------------------------------------------------
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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
