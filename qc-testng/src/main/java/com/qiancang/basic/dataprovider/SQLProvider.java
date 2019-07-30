package com.qiancang.basic.dataprovider;

import com.qiancang.basic.log.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.regex.Matcher;

public class SQLProvider implements Iterator<Object[]> {
    List<Map<String, String>> sqlList = new ArrayList<Map<String, String>>();
    int rowNum = 0;     //行数
    int curRowNo = 0;   //当前行数
    String sql = null;
    String category = "";


    /**
     * 在TestNG中由@DataProvider(dataProvider = "name")修饰的方法
     * 取SQL数据时，调用此类构造方法（此方法会得到列名并将当前行移到下以后）执行后，转发到
     * TestNG自己的方法中去，然后由它们调用此类实现的hasNext()、next()方法
     * 得到一行数据，然后返回给由@Test(dataProvider = "name")修饰的方法，如此
     * 反复到数据读完为止
     *
     * @param tablename
     */
    public SQLProvider(String tablename, String... key) {
        String ip = null;
        String user = null;
        String pw = null;
        String db = null;
        Properties prop = new Properties();
        try {
            File directory = new File(".");
            String path = ".src.test.resources.properties.";
            String absolutePath = directory.getCanonicalPath() + path.replaceAll("\\.",
                    Matcher.quoteReplacement("\\")) + "conf.properties";
            absolutePath = absolutePath.replace("\\", File.separator).replace("/", File.separator);
            InputStream in = new FileInputStream(absolutePath);
            prop.load(in);
            ip = prop.getProperty("SQLProviderIp").trim();
            user = prop.getProperty("SQLProviderUser").trim();
            pw = prop.getProperty("SQLProviderPw").trim();
            db = prop.getProperty("SQLProviderDB").trim();
        } catch (IOException e) {
            LogUtils.error("【SQLProvider.SQLProvider()】Error occurs : " + e.getMessage(),e);
        }
        if (key.length > 0) {
            for (int i = 0; i < key.length; i++) {
                category += "'" + key[i] + "',";
            }
            category = category.substring(0, category.length() - 1);
            sql = "select * from " + tablename + " where category in (" + category + ")";
        } else {
            sql = "select * from " + tablename;
        }
        sqlList = getConnection(sql, ip, user, pw, db);
        this.rowNum = sqlList.size();
    }

    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (rowNum == 0 || curRowNo >= rowNum) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取一组参数，即一行数据
     */
    public Object[] next() {
        // TODO Auto-generated method stub
        Map<String, String> s = new TreeMap<String, String>();
        s = sqlList.get(curRowNo);
        Object[] d = new Object[1];
        d[0] = s;
        this.curRowNo++;
        return d;
    }

    public void remove() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("remove unsupported");
    }

    /**
     * 测试数据为键值对，使用map存储，通常数据集合为列表
     * @param sql 待执行的sql
     * @param ip
     * @param user
     * @param pw
     * @param db
     * @return
     */
    public static List<Map<String, String>> getConnection(String sql, String ip, String user, String pw, String db) {
        Connection conn = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + "/" + db + "?user=" + user
                    + "&password=" + pw
                    + "&useUnicode=true&characterEncoding=UTF8";
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                Map<String, String> m = new HashMap<String, String>();
                for (int i = 2; i <= result.getMetaData().getColumnCount(); i++) {
                    String k = result.getMetaData().getColumnName(i);
                    String v = result.getString(i);
                    m.put(k, v);
                }
                list.add(m);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            LogUtils.error("【SQLProvider.getConnection()】Error occurs : " + e.getMessage(),e);
        }

        return list;

    }

/*    public static void main (String []args) throws IOException {
        ConnDB d = new ConnDB();
        d.setDb("dataprovider");
        d.setDbUrl("localhost");
        d.setPwd("shen1986");
        d.setUserName("root");
        d.ConnDB();
        System.out.println(getConnection("select * from zfk",d.getConn()));
    }*/
    public static void main(String[] args) {
        String sql = "select * from bill_buy";
        String ip = "129.28.68.45:3307";
        String user = "test_erp";
        String pw = "";
        String db = "";

        List<Map<String, String>> list = SQLProvider.getConnection(sql, ip, user, pw, db);
        for (Map m : list) {
            System.out.println(m);
        }
    }
}