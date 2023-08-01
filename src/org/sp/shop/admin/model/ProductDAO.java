package org.sp.shop.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.shop.admin.domain.Product;
import org.sp.shop.admin.domain.SubCategory;

import util.DBManager;

//오직 Product 테이블에 대한 CRUD를 담당할 DAO 객체 정의
public class ProductDAO {
	DBManager dbManager;
	public ProductDAO(DBManager dbManager) {
		this.dbManager=dbManager;
	}
	
	//상품 1건 등록
	public int insert(Product product) {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=dbManager.connect();
		
		
		StringBuilder sb=new StringBuilder();
		
		sb.append("insert into product(product_idx, product_name");
		sb.append(", brand, price, filename, detail");
		sb.append(", subcategory_idx) values(seq_product.nextval,?,?,?,?,?,?)");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			
			//실행전에 바인드변수 값이 먼저 할당되어야 한다.
			pstmt.setString(1, product.getProduct_name());
			pstmt.setString(2, product.getBrand());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getFilename());
			pstmt.setString(5, product.getDetail());
			pstmt.setInt(6, product.getSubCategory().getSubcategory_idx());
			
			result=pstmt.executeUpdate();//쿼리수행
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		return result;
	}
	
	//모든 상품 가져오기(하위 카테고리도 함께-join)
	public List selectAll() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Product> list=new ArrayList<Product>();
		
		
		con=dbManager.connect();
		
		StringBuilder sb = new StringBuilder();
		sb.append("select subname, product_idx, product_name, brand, price, filename");
		sb.append(" from subcategory s, product p");
		sb.append(" where s.subcategory_idx=p.subcategory_idx");
		sb.append(" order by product_idx asc");
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();//쿼리 실행 후 표 반환
			while(rs.next()) {
				Product product = new Product();
				SubCategory sub = new SubCategory();
				//서브카테고리명을 subCategory DTO에 심고 다시 이 DTO를 Product DTO에 심자
				sub.setSubname(rs.getString("subname"));
				product.setSubCategory(sub);
				
				product.setProduct_idx(rs.getInt("product_idx"));
				product.setProduct_name(rs.getString("product_name"));
				product.setBrand(rs.getString("brand"));
				product.setPrice(rs.getInt("price"));
				product.setFilename(rs.getString("filename"));
				
				list.add(product);//리스트에 추가
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}
		return list;
	}
}




