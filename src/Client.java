import java.sql.*;

public class Client extends User {
    public String  authorization(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM clients LIMIT 100;");
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
        return "False\n";
    }
    public void delete_account(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        String sql=String.format("SELECT * FROM accounts WHERE client_id=%d;",this.user_id);
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String id = String.valueOf(rs.getInt("account_id"));
            String money = String.valueOf(rs.getInt("money"));
            String a=String.format("%s, %s, %s&",money,rs.getString("account_name"),id);
            connect.write(a);
        }
        connect.write("end\n");
        String to_delete=connect.readLine();
        System.out.println(to_delete);
        int id = Integer.parseInt(to_delete);
        sql =String.format("DELETE from accounts where account_id=%d;",id);
        stmt.executeUpdate(sql);
    }
    public void show_account_list(Connect connect) throws SQLException, InterruptedException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        String sql=String.format("SELECT * FROM accounts WHERE client_id=%d;",this.user_id);
        ResultSet rs = stmt.executeQuery(sql);
        int i=0;
        while (rs.next()) {
            String a=" ";
            String id = String.valueOf(rs.getInt("account_id"));
            String money = String.valueOf(rs.getInt("money"));
            a=String.format("Сумма: %s, Имя счета: %s, Номер: %s",money,rs.getString("account_name"),id);
            System.out.println(a);
            System.out.println(i);
            connect.write(a);
            i++;
        }
        connect.write("end");
    }

    public void find_account_list(Connect connect) throws SQLException, InterruptedException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        String name=connect.readLine();
        String sql=String.format("SELECT * FROM accounts WHERE client_id=%d AND account_name='%s';",this.user_id,name);
        ResultSet rs = stmt.executeQuery(sql);
        int i=0;
        while (rs.next()) {
            String a=" ";
            String id = String.valueOf(rs.getInt("account_id"));
            String money = String.valueOf(rs.getInt("money"));
            a=String.format("Сумма: %s, Имя счета: %s, Номер: %s",money,rs.getString("account_name"),id);
            System.out.println(a);
            System.out.println(i);
            connect.write(a);
            i++;
        }
        connect.write("end");
    }

    public void add_account(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts LIMIT 100;");
        String money = connect.readLine();
        String name = connect.readLine();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt("account_id");
        }
        count+=1;
        int money1 = Integer.parseInt(money);
        String new_record = String.format("INSERT INTO accounts (client_id,account_id,money,account_name) VALUES (%d,%d,%d,'%s');",this.user_id, count, money1, name);
        stmt.executeUpdate(new_record);
    }

    public void show_bond(Connect connect) throws SQLException, InterruptedException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bond LIMIT 100;");
        while (rs.next()) {
            if (rs.getInt("user_id")==this.user_id){
            String a=" ";
            String id = String.valueOf(rs.getInt("bond_id"));
            String money = String.valueOf(rs.getInt("cost"));
            String longs = String.valueOf(rs.getInt("long"));
            String date = String.valueOf(rs.getDate("date"));
            a=String.format("Номер: %s,Цена: %s,Срок: %s,Дата: %s",id,money,longs,date);
            connect.write(a);
            }
        }
        connect.write("end");
    }

    public void card(Connect connect) throws SQLException {
    String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
    Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM card LIMIT 100;");
        System.out.println("check");
        while (rs.next()) {
        if (rs.getInt("user_id")==this.user_id){
            int balance=rs.getInt("money");
            String a=" ";
            String money = String.valueOf(balance);
            a=String.format("%s",money);
            System.out.println(a);
            connect.write(a);
            System.out.println(a);
        }

        }
        connect.write("end");
    }

    public void buy_bond(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bond LIMIT 100;");
        while (rs.next()) {
            if (rs.getInt("user_id")==0){
                //int id=rs.getInt("bond_id");
                String a=" ";
                String id = String.valueOf(rs.getInt("bond_id"));
                String money = String.valueOf(rs.getInt("cost"));
                String longs = String.valueOf(rs.getInt("long"));
                String date = String.valueOf(rs.getDate("date"));
                a=String.format("%s, %s, %s, %s&",date,money,longs,id);
                connect.write(a);
            }
        }
        connect.write("end");
        String id = connect.readLine();
        System.out.println(id);
        int id1 = Integer.parseInt(id);
        rs = stmt.executeQuery("SELECT * FROM bond LIMIT 100;");
        while (rs.next()) {
            if (rs.getInt("bond_id")==id1){
                Connection con1 = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
                Statement stmt1 = con1.createStatement();
                String sql =String.format("UPDATE bond set user_id =%d  where bond_id=%d;",this.user_id,id1);
                stmt1.executeUpdate(sql);
                stmt1.close();
                break;
            }
        }
    }
    public void survey(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM survey LIMIT 100;");
        String byn = connect.readLine();
        String usd = connect.readLine();
        String eur = connect.readLine();
        String rub = connect.readLine();
        int count = 0;
        while (rs.next()) {
            count = rs.getInt("survey_id");
        }
        count+=1;
        int byn_int = Integer.parseInt(byn);
        int usd_int = Integer.parseInt(usd);
        int eur_int = Integer.parseInt(eur);
        int rub_int = Integer.parseInt(rub);
        String new_record = String.format("INSERT INTO survey (survey_id,byn,usd,eur,rub,login) VALUES (%d,%d,%d,%d,%d,'%s');",count, byn_int,usd_int, eur_int, rub_int,this.login);
        stmt.executeUpdate(new_record);
    }
    public void take_credit(Connect connect) throws SQLException {
        String mysqlUrl = "jdbc:postgresql://localhost:5432/bank";
        Connection con = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM card LIMIT 100;");
        while (rs.next()) {
            if (rs.getInt("user_id")==0){
                String a=" ";
                String id = String.valueOf(rs.getInt("credit_id"));
                String money = String.valueOf(rs.getInt("money"));
                String longs = String.valueOf(rs.getInt("degree"));
                a=String.format("%s, %s, %s&",money,longs,id);
                connect.write(a);
            }
        }
        connect.write("end");
        String id = connect.readLine();
        System.out.println(id);
        int id1 = Integer.parseInt(id);
        rs = stmt.executeQuery("SELECT * FROM card LIMIT 100;");
        while (rs.next()) {
            if (rs.getInt("credit_id")==id1){
                Connection con1 = DriverManager.getConnection(mysqlUrl, "postgres", "Lipetsk4859");
                Statement stmt1 = con1.createStatement();
                String sql =String.format("UPDATE card set user_id =%d  where credit_id=%d;",this.user_id,id1);
                stmt1.executeUpdate(sql);
                stmt1.close();
                break;
            }
        }
    }
    }

