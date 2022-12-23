package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Myproduct {

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");
        PreparedStatement pst = null;
		Scanner sc = new Scanner(System.in);
		Statement stmt = con.createStatement();
		// ���� ����
		int id = 0;
		String name = "";
		String updated_at = "";
		String contents = "";
		int price = 0;
		int menu = 0;
        String search = "";
        String sql = "";
        String customer = "";
		
        
		System.out.println("===============�ֹ����� ���� �� ��ȸ ���α׷�===============");
		 while(true) {
	            System.out.println("1.�ֹ������Է� 2.��������ȸ 3.�ֹ����(����) 4.�������� 5.���α׷�����");
	            System.out.print("�޴� ���� >> ");
	            menu = sc.nextInt();
	            		
        switch(menu) {

        case 1 : // 1.�ֹ������Է�		
		// ����� �Է�
		System.out.println("��ǰ id �Է�"); id = sc.nextInt();
		System.out.println("��ǰ�� �Է�");	name = sc.nextLine();
		System.out.println("�ֹ���¥ �Է�"); updated_at = sc.nextLine();
		System.out.println("�޸� �Է�"); contents = sc.nextLine();
		System.out.println("���� �Է�"); price = sc.nextInt();

		
		// sql ����
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into produ values(?,?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, updated_at);
			pstmt.setString(4, contents);
			pstmt.setInt(5, price);
			
			int result = pstmt.executeUpdate();
			if (result > 0)
				System.out.println("���� ����");
			else
				System.out.println("���� ����");
		} catch (SQLException e) {
			e.printStackTrace(); // ������ �߻��ٿ����� ã�Ƽ� �ܰ躰�� ������ ����մϴ�.
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(); // ������ �߻��ٿ����� ã�Ƽ� �ܰ躰�� ������ ����մϴ�.
				}
		}
        System.out.println(); break; // case 1 ����
        
   
        // 2. �ֹ��� ��ȸ
        case 2 : 
        	System.out.println(menu+"�� ����");
            System.out.print("�ֹ��ڸ� �Է� : ");
            search = sc.next();
            System.out.println("=============== "+search+" ===============");
          
            try {
        		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");

                //3. sql ����
                sql = " SELECT * FROM product WHERE customer = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, search);

                //4. ���ó��
                ResultSet rs = pst.executeQuery();
                boolean isList = false;
                while(rs.next()) {
                    id = rs.getInt("id");
                    customer = rs.getString("customer");
                    name = rs.getString("name");
                    updated_at = rs.getString("updated_at");
                    contents = rs.getString("contents");
                    price = rs.getInt("price");

                    System.out.print("�ֹ��ڹ�ȣ : "+id+"\t");
                    System.out.print("�ֹ��ڸ� : "+customer+"\t");
                    System.out.print("��ǰ�� : "+name+"\t");
                    System.out.print("������ : "+updated_at+"\t");
                    System.out.print("���� : "+price+"\t");
                    System.out.print("�޸�: " + contents);

                    System.out.println();
                    isList = true;

                }
                if(isList==false) {
                    System.out.println("�˻��� �ֹ������� �����ϴ�.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pst.close();
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
              
        System.out.println(); break; // case 2 ����
        
        // 3. �ֹ����(����)
        case 3 : 
        	  System.out.println(menu+"�� ����");
        	  try {
  
          		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");
                System.out.print("������ �ֹ���ȣ : ");
                id = sc.nextInt();
       
                sql = " SELECT * FROM product WHERE id = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, name);
                ResultSet rs = pst.executeQuery();
                boolean isList = false; 
                while(rs.next()) {
                    id = rs.getInt("id");
                    customer = rs.getString("customer");
                    name = rs.getString("name");
                    updated_at = rs.getString("updated_at");
                    contents = rs.getString("contents");
                    price = rs.getInt("price");
                    System.out.println("========= ������� : "+name+" ===============");
                    System.out.println();
                    System.out.print("�ֹ��ڹ�ȣ : "+id+"\t");
                    System.out.print("�ֹ��ڸ� : "+customer+"\t");
                    System.out.print("��ǰ�� : "+name+"\t");
                    System.out.print("������ : "+updated_at+"\t");
                    System.out.print("���� : "+price+"\t");
                    System.out.print("�޸�: " + contents);
                    System.out.println();
                    System.out.println();
                    System.out.println("=============== ======= ===============");
                    System.out.println();
                    isList = true;
                }

                if(isList == false) {
                    System.out.println("��ϵ� ����� �����ϴ�. ");
                }

                sql = " DELETE FROM product WHERE id = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);

                int cnt = pst.executeUpdate();

                if(cnt > 0) {
                    System.out.println(id +"�� �ֹ��� ���� ���� ");
                } else {
                    System.out.println(id + "�� �ֹ��� ���� ���� ���� ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    pst.close();
                    con.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
          		
        	System.out.println(); break; // case 3 ����

        // 4. ��������
        case 4 : 
        	  System.out.println(menu+"�� ����");

              try {
 
          		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");
                   // ���� ������ �ֹ��� �ҷ�����

                  System.out.print("���������� ������ Ȯ�� : ");
                  customer = sc.next();

                  sql = " SELECT * FROM product WHERE customer LIKE ? ";
                  pst = con.prepareStatement(sql);

                  String customers = '%'+customer+'%';
                  pst.setString(1, customers);
                  ResultSet rs = pst.executeQuery();

                  while(rs.next()) {
                	  id = rs.getInt("id");
                      customer = rs.getString("customer");
                      name = rs.getString("name");
                      updated_at = rs.getString("updated_at");
                      contents = rs.getString("contents");
                      price = rs.getInt("price");
                      System.out.println("=============== "+customer+" ===============");
                      System.out.println();
                      System.out.print("�ֹ��ڹ�ȣ : "+id+"\t");
                      System.out.print("�ֹ��ڸ� : "+customer+"\t");
                      System.out.print("��ǰ�� : "+name+"\t");
                      System.out.print("������ : "+updated_at+"\t");
                      System.out.print("���� : "+price+"\t");
                      System.out.print("�޸�: " + contents);
                      System.out.println();
                      System.out.println();
                      System.out.println("=============== ======= ===============");
                      System.out.println();
                  }

                  //�� �����Ұ��� �������.
                  System.out.print("�����Ͻ� ���� : ���� [1]�̸�, [2]��ǰ��, [3]����, [4]��ü���� :");
                  int sel = sc.nextInt();
                  if(sel == 1) {
                      System.out.print("������ �̸� : ");
                      customer = sc.next();
                  } else if(sel == 2) {
                      System.out.print("������ ��ǰ�� : ");
                      name = sc.next();
                  } else if(sel == 3) {
                      System.out.print("������ ���� : ");
                      price = sc.nextInt();
                  } else if(sel == 4) {
                      System.out.print("������ �̸� : ");
                      customer = sc.next();
                      System.out.print("������ ��ǰ�� : ");
                      name = sc.next();
                      System.out.print("������ ���� : ");
                      price = sc.nextInt();
                  }

                  //3. sql ���� �غ� ��ü( PreparedStatement ) ����
                  //studentSeq.nextval : ������
                  //? : ���ε� ����

                  stmt = con.createStatement();
                  sql = "UPDATE product SET";
                  sql += " name = ?, ";
                  sql += " updated_at = ?, ";
                  sql += " contents = ?, ";
                  sql += " price = ?, ";
                  sql += " customer = ? ";
                  sql += " WHERE id = ? ";
                  pst = con.prepareStatement(sql);

                  //4. ���ε� ������ ä���.
                  pst.setString(1, name);
                  pst.setString(2, updated_at);
                  pst.setString(3, contents);
                  pst.setInt(4, price);
                  pst.setString(5, customer);
                  pst.setInt(6, id);
                  //5. sql�� �����Ͽ� ��� ó��
                  //executeUpdate : insert, delete, update ( ���̺��� ��ȭ�� ���涧 ���Ǵ� ��� )
                  int cnt = pst.executeUpdate();
                  if(cnt > 0) {
                      System.out.println("�������� ����");
                  } else {
                      System.out.println("�������� ����");
                  }

              //} catch (ClassNotFoundException e) {
              } catch (Exception e) {
                  e.printStackTrace();
              } finally {
                  try {
                      pst.close();
                      con.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
        System.out.println(); break; // case 4 ����
        
        // 5. ���α׷� ����
        case 5 : 
            System.out.println(menu+"�� ����");
            System.out.println();
            break;

        } // switch ����
        if(menu == 5) {
            System.out.println("���α׷�����");
            break; // case 5 ����
        }

		 } // while�� ����     		
		 sc.close();
	} // main method end
} // class end