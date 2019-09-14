package Databases;

import Data.OrderData;
import Data.StatusEnum;
import Data.UserData;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteClass {
    public static Connection conn;
    public static Statement stat;
    public static ResultSet rs;

    public static void Conn() throws ClassNotFoundException, SQLException, NamingException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:SimpleDatabase");
    }

    public static void addName(String name) throws ClassNotFoundException, SQLException {
        try {
            Conn();
            stat = conn.createStatement();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO names (name) VALUES (?)");
            statement.setString(1, name);
            statement.execute();
            statement.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            stat.close();
            CloseDB();
        }
    }

    public static UserData getUserData(long telephone){
        //Получение данных пользователя
        return new UserData((long) 11, "sda");
    }

    /*public static ArrayList<String> getAllNames() throws ClassNotFoundException, SQLException, NamingException
    {
        ArrayList<String> names = new ArrayList<String>();

        Conn();
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select name from names");
        
        while (rs.next()) {
            names.add(rs.getString("name"));
        }

        rs.close();
        stat.close();
        CloseDB();

        return names;
    }*/


    //функция: добавить заказ
    public static boolean addOrder(OrderData orderData) throws SQLException, NamingException, ClassNotFoundException {
        boolean result = false;
        try {
            Conn();
            stat = conn.createStatement();

            PreparedStatement statement = conn.prepareStatement
                    ("INSERT INTO orders (id, deadline, price, link) VALUES (?, ?, ?, ?)");
            statement.setString(1, Integer.toString(orderData.getId()));
            statement.setString(2, orderData.getDeadline().toString());
            statement.setString(3, Integer.toString(orderData.getPrice()));
            statement.setString(4, orderData.getLink());
            statement.execute();
            statement.close();
            result = true;
        } catch (Exception e) {
            System.out.println(e);
            result = false;
        }
        finally {
            stat.close();
            CloseDB();
        }
        return result;
    }

    //функция: выдать список заказов
    public static ArrayList<OrderData> orderList(){
        return null;
    }

    //функция: узнать статус заказа
    public static StatusEnum checkOrderStatus(int orderId){
        return null;
    }

    //функция: изменить статус заказа
    public static boolean setOrderStatus(int orderId, StatusEnum status){
        return false;
    }

    //функция: архивировать заказ
    public static boolean archiveOrder(int orderId){
        return false;
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
    }
}
