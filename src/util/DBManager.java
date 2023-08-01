package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//어플리케이션이 가동될 때 접속을 하고 끝날대 접속을 해제하기 위해
//데이터베이스 접속 및 끝내는 처리를 담당할 객체를 별도로 정의해서
//공토코드화 시키자
public class DBManager {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="shop";
	String password="1234";
	
	
	//커넥션 만들기
	public Connection connect() {
		Connection con=null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;

	}
	
	//데이터베이스와 관련된 자원 해제
	

	//con, pstmt를 모두 닫는 경우(DML)
	public void release(Connection con,PreparedStatement pstmt) {

		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
	//con, pstmt, rs를 모두 닫는 경우(Select)
	public void release(Connection con,PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}
}
