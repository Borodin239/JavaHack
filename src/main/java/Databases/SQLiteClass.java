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
        System.out.println("Start addOrder function");
        boolean result = true;
        try {
            Conn();
            stat = conn.createStatement();

            PreparedStatement statement = conn.prepareStatement
                    ("INSERT INTO orders (id, deadline, price, link, status) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, orderData.getId());
            statement.setString(2, orderData.getDeadline());
            statement.setInt(3, orderData.getPrice());
            statement.setString(4, orderData.getLink());
            statement.setString(5, orderData.getStatus().toString());
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
        System.out.println("Finish addOrder function");
        return true;
    }

    //функция: выдать список заказов
    public static ArrayList<OrderData> orderList() throws SQLException, ClassNotFoundException {
        ArrayList<OrderData> allOrders = new ArrayList<>();
        try{
            Conn();
            stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * from orders");
            while (rs.next()){
                OrderData newOrder = new OrderData(rs.getInt("price"),
                        rs.getString("deadline"),
                        rs.getString("link"),
                        StatusEnum.valueOf(rs.getString("status")));
                allOrders.add(newOrder);
            }
            //здесь надо получать всю строку из бд
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            stat.close();
            CloseDB();
        }
        return allOrders;
    }

    //функция: узнать статус заказа
    public static StatusEnum checkOrderStatus(int orderId) throws SQLException, ClassNotFoundException {
        String status = null;
        try {
            Conn();
            stat = conn.createStatement();
            String command = "SELECT status FROM orders WHERE id = " + orderId + ";";
            ResultSet rs = stat.executeQuery(command);
            while (rs.next()) {
                status = rs.getString("status");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            stat.close();
            CloseDB();
        }
        return StatusEnum.valueOf(status);
    }

    //функция: изменить статус заказа
    public static boolean setOrderStatus(int orderId, StatusEnum status) throws SQLException, ClassNotFoundException {
        boolean result = false;
        try {
            Conn();
            stat = conn.createStatement();
            String command = "UPDATE orders SET status = " + status + " WHERE id = " + orderId + ";";
            PreparedStatement statement = conn.prepareStatement(command);
            statement.execute();
            statement.close();
            result = true;
        }
        catch (Exception e) {
            System.out.println(e);
            result = false;
        }
        finally {
            stat.close();
            CloseDB();
        }

        return result;
    }

    //функция: архивировать заказ
    public static boolean archiveOrder(int orderId){
        return false;
    }

    public static void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
    }
}
