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
		// 변수 선언
		int id = 0;
		String name = "";
		String updated_at = "";
		String contents = "";
		int price = 0;
		int menu = 0;
        String search = "";
        String sql = "";
        String customer = "";
		
        
		System.out.println("===============주문정보 수정 및 조회 프로그램===============");
		 while(true) {
	            System.out.println("1.주문정보입력 2.구매자조회 3.주문취소(삭제) 4.정보수정 5.프로그램종료");
	            System.out.print("메뉴 선택 >> ");
	            menu = sc.nextInt();
	            		
        switch(menu) {

        case 1 : // 1.주문정보입력		
		// 사용자 입력
        	System.out.println();
		System.out.println("상품 id 입력"); id = sc.nextInt();
		System.out.println();
		System.out.println("상품명 입력");	name = sc.next();
		System.out.println();
		System.out.println("주문날짜 입력"); updated_at = sc.next();
		System.out.println();
		System.out.println("메모 입력"); contents = sc.next();
		System.out.println();
		System.out.println("가격 입력"); price = sc.nextInt();
		System.out.println();
		System.out.println("주문자명 입력"); customer = sc.next();
		
		// sql 연동
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement("insert into product values(?,?,?,?,?,?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, updated_at);
			pstmt.setString(4, contents);
			pstmt.setInt(5, price);
			pstmt.setString(6, customer);
			int result = pstmt.executeUpdate();
			if (result > 0)
				System.out.println("삽입 성공");
			else
				System.out.println("삽입 실패");
		} catch (SQLException e) {
			e.printStackTrace(); // 에러의 발생근원지를 찾아서 단계별로 에러를 출력합니다.
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 에러의 발생근원지를 찾아서 단계별로 에러를 출력합니다.
				}
		}
        System.out.println(); break; // case 1 종료
        
   
        // 2. 주문자 조회
        case 2 : 
        	System.out.println(menu+"번 선택");
            System.out.print("주문자명 입력 : ");
            search = sc.next();
            System.out.println("=============== "+search+" ===============");
          
            try {
        		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");

                //3. sql 구문
                sql = " SELECT * FROM product WHERE customer = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, search);

                //4. 결과처리
                ResultSet rs = pst.executeQuery();
                boolean isList = false;
                while(rs.next()) {
                    id = rs.getInt("id");
                    customer = rs.getString("customer");
                    name = rs.getString("name");
                    updated_at = rs.getString("updated_at");
                    contents = rs.getString("contents");
                    price = rs.getInt("price");

                    System.out.print("주문자번호 : "+id+"\t");
                    System.out.print("주문자명 : "+customer+"\t");
                    System.out.print("상품명 : "+name+"\t");
                    System.out.print("구매일 : "+updated_at+"\t");
                    System.out.print("가격 : "+price+"\t");
                    System.out.print("메모: " + contents);

                    System.out.println();
                    isList = true;

                }
                if(isList==false) {
                    System.out.println("검색된 주문정보가 없습니다.");
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
              
        System.out.println(); break; // case 2 종료
        
        // 3. 주문취소(삭제)
        case 3 : 
        	  System.out.println(menu+"번 선택");
        	  try {
  
          		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");
                System.out.print("삭제할 주문번호 : ");
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
                    System.out.println("========= 삭제대상 : "+name+" ===============");
                    System.out.println();
                    System.out.print("주문자번호 : "+id+"\t");
                    System.out.print("주문자명 : "+customer+"\t");
                    System.out.print("상품명 : "+name+"\t");
                    System.out.print("구매일 : "+updated_at+"\t");
                    System.out.print("가격 : "+price+"\t");
                    System.out.print("메모: " + contents);
                    System.out.println();
                    System.out.println();
                    System.out.println("=============== ======= ===============");
                    System.out.println();
                    isList = true;
                }

                if(isList == false) {
                    System.out.println("등록된 대상이 없습니다. ");
                }

                sql = " DELETE FROM product WHERE id = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);

                int cnt = pst.executeUpdate();

                if(cnt > 0) {
                    System.out.println(id +"번 주문자 정보 삭제 ");
                } else {
                    System.out.println(id + "번 주문자 정보 삭제 실패 ");
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
          		
        	System.out.println(); break; // case 3 종료

        // 4. 정보수정
        case 4 : 
        	  System.out.println(menu+"번 선택");

              try {
 
          		con = DriverManager.getConnection(
        				"jdbc:mysql://localhost:3306/de-jdbc", "root", "qwer1234");
                   // 정보 변경할 주문자 불러오기

                  System.out.print("정보변경할 주문정보 확인 : ");
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
                      System.out.print("주문자번호 : "+id+"\t");
                      System.out.print("주문자명 : "+customer+"\t");
                      System.out.print("상품명 : "+name+"\t");
                      System.out.print("구매일 : "+updated_at+"\t");
                      System.out.print("가격 : "+price+"\t");
                      System.out.print("메모: " + contents);
                      System.out.println();
                      System.out.println();
                      System.out.println("=============== ======= ===============");
                      System.out.println();
                  }

                  // 무엇을 변경할지 선택
                  System.out.print("변경하실 내용 : 선택 [1]이름, [2]상품명, [3]가격, [4]전체변경 :");
                  int sel = sc.nextInt();
                  if(sel == 1) {
                      System.out.print("변경할 이름 : ");
                      customer = sc.next();
                  } else if(sel == 2) {
                      System.out.print("변경할 상품명 : ");
                      name = sc.next();
                  } else if(sel == 3) {
                      System.out.print("변경할 가격 : ");
                      price = sc.nextInt();
                  } else if(sel == 4) {
                      System.out.print("변경할 이름 : ");
                      customer = sc.next();
                      System.out.print("변경할 상품명 : ");
                      name = sc.next();
                      System.out.print("변경할 가격 : ");
                      price = sc.nextInt();
                  }

                  //3. sql 구문 준비 객체( PreparedStatement ) 생성
                  //studentSeq.nextval : 시퀀스
                  //? : 바인드 변수

                  stmt = con.createStatement();
                  sql = "UPDATE product SET";
                  sql += " name = ?, ";
                  sql += " updated_at = ?, ";
                  sql += " contents = ?, ";
                  sql += " price = ?, ";
                  sql += " customer = ? ";
                  sql += " WHERE id = ? ";
                  pst = con.prepareStatement(sql);

                  //4. 바인드 변수를 채운다.
                  pst.setString(1, name);
                  pst.setString(2, updated_at);
                  pst.setString(3, contents);
                  pst.setInt(4, price);
                  pst.setString(5, customer);
                  pst.setInt(6, id);
                  //5. sql문 실행하여 결과 처리
                  //executeUpdate : insert, delete, update ( 테이블의 변화가 생길때 사용되는 명령 )
                  int cnt = pst.executeUpdate();
                  if(cnt > 0) {
                      System.out.println("정보수정 성공");
                  } else {
                      System.out.println("정보수정 실패");
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
        System.out.println(); break; // case 4 종료
        
        // 5. 프로그램 종료
        case 5 : 
            System.out.println(menu+"번 선택");
            System.out.println();
            break;

        } // switch 종료
        if(menu == 5) {
            System.out.println("프로그램종료");
            break; // case 5 종료
        }

		 } // while문 종료     		
		 sc.close();
	} // main method end
} // class end
