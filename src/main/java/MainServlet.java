import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

import Data.OrderData;
import Data.UserData;
import Databases.SQLiteClass;
import org.json.JSONObject;

public class MainServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        super.service(req, resp);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder jb = new StringBuilder();
        String line = null;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        try {
            JSONObject jsonObject = new JSONObject(jb.toString());

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            int command = jsonObject.getInt("command");

            switch (command) {

                case 4:
                    //команда для создания новой платежки - переход в новое окно
                    //переход в новое окно - там создание платежки и ее добавление в бд
                    JSONObject jsonToReturn4 = new JSONObject();
                    jsonToReturn4.put("answer", "goOrder");
                    out.println(jsonToReturn4.toString());

                    break;

                case 41:
                    //команда для создание новой платежки - из нового окна сохранение в базу данных
                    //взять дату, цену и ссылку из окошка - создать новую строку в БД
                    //вернуться в главное окно

                    String name = jsonObject.getString("name");
                    Integer price = jsonObject.getInt("price");
                    String link = jsonObject.getString("link");

                    break;

                case 5:
                    //команда для получения списка действующих заказов
                    ArrayList<OrderData> orderList = SQLiteClass.orderList();
                    break;

                case 6:
                    //команда для уведомление о выполнении заказа
                    break;

                case 0: //show all names

                    /*ArrayList<String> names = SQLiteClass.getAllNames();
                    
                    JSONObject jsonToReturn0 = new JSONObject();
                    jsonToReturn0.put("answer", "names");
                    jsonToReturn0.put("list", names.toString());
                    out.println(jsonToReturn0.toString());*/

                    break;

                case 1: //add new name

                    String data = jsonObject.getString("name");

                    SQLiteClass.addName(data);

                    JSONObject jsonToReturn1 = new JSONObject();
                    jsonToReturn1.put("answer", "ok");
                    out.println(jsonToReturn1.toString());

                    break;

                case 3: //authorization
                    //поиск в базе данных пользователя с данным телефоном
                    String telephoneString = jsonObject.getString("telephone");
                    UserData user;
                    try {
                        long telephone = Long.parseLong(telephoneString);
                        user = SQLiteClass.getUserData(telephone);
                    } catch (Exception e){
                        System.out.println(e.toString());
                        break;
                    }

                    //высылка ему пароля на телефон
                    final int sendedPassword = user.sentPassword();

                    //окно для ввода
                    //проверка соответствия введенному паролю
                    //вход
                    break;

                default:
                    System.out.println("default switch");
                    break;

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}