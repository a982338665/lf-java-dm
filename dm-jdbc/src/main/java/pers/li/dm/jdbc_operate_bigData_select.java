package pers.li.dm;

import java.io.*;
import java.sql.*;

public class jdbc_operate_bigData_select {
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
    // 定义保存结果集的对象
    static ResultSet rs = null;
    // 定义输入流对象
    static InputStream in = null;
    // 定义输出流对象
    static FileOutputStream fos = null;
    // 定义输出流对象
    static FileOutputStream fos2 = null;
    // 定义高效字符流对象
    static BufferedReader reader = null;

    public static void main(String[] args) {
        try {
            //1.加载 JDBC 驱动程序
            System.out.println("Loading JDBC Driver...");
            Class.forName(jdbcString);
            //2.连接 DM 数据库
            System.out.println("Connecting to DM Server...");
            conn = DriverManager.getConnection(urlString, userName, password);
//-----------------------------------------------------------------------------------------------
            //3.查询大字段信息 SQL 语句
            String sql_insert = "SELECT * FROM production.BIG_DATA ;";
            pstate = conn.prepareStatement(sql_insert);
            //4.创建 ResultSet 对象保存查询结果集
            rs = pstate.executeQuery();
            //5.解析结果集
            while (rs.next()) {
                //获取第一列 id 信息
                int id = rs.getInt("id");
                //获取第二列 photo 图片信息，并把该图片直接写入到 D:/id_DM8特点.jpg;
                in = rs.getBinaryStream("photo");
                fos = new FileOutputStream("D:/" + id + "_DM8特点.jpg");
                int num = 0;
                //每次从输入流中读取一个字节数据，如果没读到最后指针向下继续循环
                while ((num = in.read()) != -1) {
                    //将每次读取的字节数据，写入到输出流中
                    fos.write(num);
                }
                //获取第三列的 Blob 大字段信息
                //Blob 对象处理的是字节型大字段信息例如图片、视频文件等
                Blob blob = rs.getBlob("describe");
                in = blob.getBinaryStream();
                fos2 = new FileOutputStream("D:/" + id + "_Blob_DM8特点.jpg");
                //每次从输入流中读取一个字节数据，如果没读到最后指针向下继续循环
                while ((num = in.read()) != -1) {
                    //将每次读取的字节数据，写入到输出流中
                    fos.write(num);
                }
                //获取第四列的 Clob 大字段信息
                //Clob 大字段处理的是字符型大字段信息，文本等数据
                Clob clob = rs.getClob("txt");
                reader = new BufferedReader(clob.getCharacterStream());
                String str = null;
                while ((str = reader.readLine()) != null) {
                    //将每次读取的字节数据
                    System.out.println(str.toString());
                }
            }
//-----------------------------------------------------------------------------------------
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭资源
                fos.close();
                in.close();
                rs.close();
                pstate.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
