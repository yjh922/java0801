package org.sp.shop.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class OrderPage extends Page{
	JPanel p_west; 
	JPanel p_center;
	String[] subTitle= {"주문목록","매출통계","결제관리"};
	JLabel[] subNavi;
	
	public OrderPage() {
		p_west = new JPanel();
		p_center = new JPanel();
		createNavi();
		
		//스타일
		p_west.setPreferredSize(new Dimension(150, 600));
		p_west.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1),"상품관리"));
		p_center.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1),"상품정보"));
		
		setLayout(new BorderLayout());
		//조립
		add(p_west, BorderLayout.WEST);
		add(p_center);
	}
	
	public void createNavi() {
		subNavi = new JLabel[subTitle.length];//배열생성 
		
		for(int i=0;i<subTitle.length;i++) {
			subNavi[i] = new JLabel(subTitle[i]);
			p_west.add(subNavi[i]);
		}
	}
}





