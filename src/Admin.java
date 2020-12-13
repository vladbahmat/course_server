import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Admin extends User {
    public void show_user_list(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clients LIMIT 100;");
        while (rs.next()) {
            String id = String.valueOf(rs.getInt("id"));
            String a=String.format("Логин: %s,Пароль: %s,ID: %s",rs.getString("login"),rs.getString("password"),id);
            connect.write(a);
        }
        connect.write("end");
    }



    public void add_bond(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bond LIMIT 100;");
        String cost = connect.readLine();
        String bond_long = connect.readLine();
        int count = 0;
        int control=0;
        while (rs.next()) {
            control = rs.getInt("bond_id");
            count++;
        }
        count++;
        int cost1 = Integer.parseInt(cost);
        int bond_long1 = Integer.parseInt(bond_long);
        String new_record = String.format("INSERT INTO bond (bond_id,cost,long,user_id,date) VALUES (%d,%d,%d,0,current_date);", count, cost1, bond_long1);
        stmt.executeUpdate(new_record);
    }

    public String  authorization(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users LIMIT 10;");
        String login = connect.readLine();
        String password = connect.readLine();
        while (rs.next()) {
            if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
                System.out.println("good");
                this.login = login;
                this.password = password;
                this.user_id = rs.getInt("id");
                return "True\n";
            }
        }
        System.out.println("ebalovo s avtorizacieei");
        return "False\n";
    }



    public void update_user_info(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clients LIMIT 100;");
        while (rs.next()) {
            String id = String.valueOf(rs.getInt("id"));
            String a=String.format("%s, %s, %s",rs.getString("login"),rs.getString("password"),id);
            connect.write(a);
        }
        connect.write("end");
        String login = connect.readLine();
        String new_login = connect.readLine();
        rs = stmt.executeQuery("SELECT * FROM clients LIMIT 100;");
        while (rs.next()) {
            if (rs.getString("login").equals(login)){
                Connection con1 = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
                Statement stmt1 = con1.createStatement();
                String sql =String.format("UPDATE clients set login ='%s'  where login='%s';",new_login,login);
                stmt1.executeUpdate(sql);
                stmt1.close();
                break;
            }
        }

    }
    public void add_credit(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM card LIMIT 100;");
        String cost = connect.readLine();
        String degree = connect.readLine();
        int count = 0;
        int control=0;
        while (rs.next()) {
            control = rs.getInt("credit_id");
            count++;
        }
        count++;
        int cost1 = Integer.parseInt(cost);
        int degree1 = Integer.parseInt(degree);
        String new_record = String.format("INSERT INTO card (credit_id,money,degree,user_id) VALUES (%d,%d,%d,0);", count, cost1, degree1);
        stmt.executeUpdate(new_record);
    }

}

