package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.shop.admin.domain.SubCategory;

import util.DBManager;

//오직 Subcategory 테이블에 대한 CRUD만을 담당하는 객체
public class SubCategoryDAO {
	DBManager dbManager;
	
	public SubCategoryDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}

	
	//상위 카테고리에 소속된 하위카테고리 가져오기 
	public List selectAllByFkey(int topcategory_idx) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List list = new ArrayList();
		
		try {
			
			con=dbManager.connect();
			
			String sql="select * from subcategory where topcategory_idx=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, topcategory_idx);//바인드변수값 지정
			rs=pstmt.executeQuery();//실행
			
			//rs가 곧 닫히므로, rs를 대신할 수 있는 DTO에 담은 수, 여러건이므로 List에 담자 
			while(rs.next()) {
				 SubCategory sub = new SubCategory();
				 sub.setSubcategory_idx(rs.getInt("subcategory_idx"));
				 sub.setTopcategory_idx(rs.getInt("topcategory_idx"));
				 sub.setSubname(rs.getString("subname"));
				 
				 list.add(sub); //리스트에 추가하기
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		
		return list;
	}
}




