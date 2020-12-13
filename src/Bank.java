
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;

public class Bank {
    Connect connect;
    public Bank() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1280);
        connect = new Connect(serverSocket);
    }

    public void menu() throws IOException, SQLException, InterruptedException {
        String buf="0";
        Admin admin=new Admin();
        Client client=new Client();
        while (true){
            buf=connect.readLine();
            System.out.println("main menu rcv");
            if (buf.equals("1")){
                String check=admin.authorization(connect);
                System.out.println(check);
                connect.write(check);
            }
            if (buf.equals("3")){
                Bank.register_new_user(connect);
            }
            if (buf.equals("4")){
                admin.show_user_list(connect);
            }
            if (buf.equals("5")){
                admin.add_bond(connect);
            }
            if (buf.equals("6")){
                admin.update_user_info(connect);
            }
            if (buf.equals("8")){
                String check= client.authorization(connect);
                connect.write(check);
            }
            if (buf.equals("9")){
                client.show_account_list(connect);
            }
            if (buf.equals("10")){
                client.delete_account(connect);
            }
            if(buf.equals("11")){
                client.add_account(connect);
            }
            if (buf.equals("12")){
                client.show_bond(connect);
            }
            if (buf.equals("13")){
                client.buy_bond(connect);
            }
            if (buf.equals("14")){
                client.survey(connect);
            }
            if (buf.equals("15")){
                client.card(connect);
            }
            if (buf.equals("16")){
                admin.add_credit(connect);
            }
            if (buf.equals("17")){
                client.take_credit(connect);
            };
            if(buf.equals("18")){
                Bank.show_surveys(connect);
            }
            if(buf.equals("19")){
                client.find_account_list(connect);
            }
            if(buf.equals("20")){
                Bank.otchet();
            }
        }
    }

    public static void register_new_user(Connect connect) throws IOException,SQLException {
        //Admin admin=new Admin();
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clients LIMIT 100;");
        String login = connect.readLine();
        String password = connect.readLine();
        int count = 0;
        int control=0;
        while (rs.next()) {
            control = rs.getInt("id");
            count++;
        }
        count++;
        //int number = String.valueOf(count);
        String new_record = String.format("INSERT INTO clients (id,login,password) VALUES (%d,'%s','%s');", count, login, password);
        stmt.executeUpdate(new_record);
    }

    public static void show_surveys(Connect connect) throws IOException,SQLException {
        //Admin admin=new Admin();
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM survey LIMIT 100;");
        while (rs.next()) {
            String a=" ";
            String byn = String.valueOf(rs.getInt("byn"));
            String usd = String.valueOf(rs.getInt("usd"));
            String eur = String.valueOf(rs.getInt("eur"));
            String login=rs.getString("login");
            int b=rs.getInt("rub");
            String rub;
            if (b==0){
                rub="o";
            }
            else{
                rub=String.valueOf(b);
            }
            a=String.format("Логин:%s, BYN:%s, USD:%s, EUR:%s, RUB:%s",login,byn,usd,eur,rub);
            connect.write(a);
        }
        connect.write("end");
    }

    public static void otchet(){
        try(FileWriter writer = new FileWriter("othet.txt", false))
        {
            String text = "Динамо Банк\n" +
                    "Директор Шрек\n" +
                    "Клиенты довольны обслуживанием, я доволен жизнью\n" +
                    "Считаю этот курсовой гениальным\n" +
                    "Дата Подпись Инициалы.";
            writer.write(text);
            writer.append('\n');
            writer.append('E');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }



    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        Bank bank=new Bank();
        bank.menu();
    }

}

