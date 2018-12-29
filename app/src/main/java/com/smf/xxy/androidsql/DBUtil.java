package com.smf.xxy.androidsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil
{

    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip +":2222/" + db + ";charset=GBK", user, pwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static String QuerySQL(String sql,String name)
    {
        String result = "";
        try
        {
            Connection conn = getSQLConnection("43.243.128.36", "health", "13240670", "health");

            Statement stmt = conn.createStatement();//
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                //String s1 = rs.getString("UserName");
                result = rs.getString(name);
                //result += s1 + "  -  " + s2 + "\n";
                //System.out.println(s1 + "  -  " + s2);
                System.out.println(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }
    public static boolean Record(String sql)
    {
        try
        {
            Connection conn = getSQLConnection("43.243.128.36", "health", "13240670", "health");
            Statement stmt = conn.createStatement();//
            stmt.executeUpdate(sql);

            stmt.close();
            conn.close();
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static String[] FindLot(String sql,String name[])
    {
        String [] result=new String[name.length];
        try
        {
            Connection conn = getSQLConnection("43.243.128.36", "health", "13240670", "health");
            Statement stmt = conn.createStatement();//
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                for(int i=0;i<(name.length-1);i++){
                    String a=name[i];
                    String b = rs.getString(""+a);
                    result[i]=b;
                }
                System.out.println(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            //result += "查询数据异常!" + e.getMessage();
        }
        return result;
    }
    public static void main(String[] args)
    {
        // QuerySQL();
    }
}

