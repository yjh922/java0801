package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.shop.admin.domain.TopCategory;

import util.DBManager;

//Topcategory에 대한 CRUD만을 수행하는 DAO 객체 
public class TopCategoryDAO {
	
	DBManager dbManager;
	
	public TopCategoryDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	
	//모든 레코드 가져오기 
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<TopCategory> list=new ArrayList<TopCategory>();
		
		try {
			
			con=dbManager.connect();
			if(con==null) {
				System.out.println("접속실패");
			}else {
				String sql="select * from topcategory";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				//rs는 곧 닫히므로, rs의 레코드들을 한건,한건 DTO에 옮겨놓자
				while(rs.next()) {
					TopCategory dto = new TopCategory();
					//넣을때는 setter로...
					dto.setTopcategory_idx(rs.getInt("topcategory_idx"));
					dto.setTopname(rs.getString("topname"));
					
					list.add(dto); //리스트에 채워넣기
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con,pstmt, rs);
		}
		return list;
	}
	
}
