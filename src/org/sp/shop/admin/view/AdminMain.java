package org.sp.shop.admin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.DBManager;

public class AdminMain extends JFrame{
	JPanel p_north;
	String[] naviTitle= {"상품관리","회원관리","주문관리","게시판관리"};
	ArrayList<JLabel> navi;
	JLabel la_login; //로그인 상태 출력 라벨
	JPanel p_center; //각 컨텐츠 페이지들이 들어올 빈 영역 
	
	LoginForm loginForm;
	
	
	//각페이지의 index가 직관성이 없기 때문에 상수로 표현하자 
	public static final int PRODUCT=0;
	public static final int MEMBER=1;
	public static final int ORDER=2;
	public static final int BLOG=3;
	
	//컨텐츠 페이지 
	Page[] pages;
	Connection con;
	
	public AdminMain() {
		
		p_north = new JPanel();
		createNavi();
		la_login = new JLabel("");
		p_center = new JPanel();
		pages=new Page[4];
		
		
		//페이지생성 
		pages[PRODUCT] = new ProductPage();
		pages[MEMBER] = new MemberPage();
		pages[ORDER] = new OrderPage();
		pages[BLOG] = new BlogPage();
		
		//스타일 
		p_center.setBackground(Color.YELLOW);
		
		//조립 
		p_north.add(la_login);
		
		for(int i=0;i<pages.length;i++) {
			p_center.add(pages[i]);
		}
		
		add(p_north, BorderLayout.NORTH);
		add(p_center);
		
		setSize(1100, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		//loginForm = new LoginForm(this);
		
		//최초로 상품페이지는 보여지게..
		showHide(PRODUCT);
		
		//네비게이션에 대한 이벤트 연결 
		for(int i=0;i<navi.size();i++) {
			JLabel obj=navi.get(i);
			
			obj.addMouseListener(new MouseAdapter() {
				//마우스 올려놓으면, 배경색을 적용해서 hover효과내기
				public void mouseEntered(MouseEvent e) {
					JLabel la=(JLabel)e.getSource();
					la.setBackground(Color.BLACK);
					la.setForeground(Color.WHITE);
				}
				//마우스를 내려놓으면, 배경색을 빼버려서, 효과..
				public void mouseExited(MouseEvent e) {
					JLabel la=(JLabel)e.getSource();
					la.setBackground(null);
					la.setForeground(Color.BLACK);
				}
				
				//클릭시 해당 페이지 보여주기
				public void mouseClicked(MouseEvent e) {
					int index=navi.indexOf(e.getSource());//이벤트를 일으킨 JLabel이 몇번째 index에
					//들어있는지 조사 
					
					showHide(index);					
				}
			});
		}
		
		//로그아웃 처리 
		la_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int result=JOptionPane.showConfirmDialog(AdminMain.this, "로그아웃 하시겠어요?");
				if(result==JOptionPane.OK_OPTION) {
					logout();
				}
			}
		});
	}
	
	public void createNavi() {
		navi=new ArrayList<JLabel>();
		
		for(int i=0;i<naviTitle.length;i++) {
			JLabel obj = new JLabel(naviTitle[i]);//네비생성
			navi.add(obj); //리스트에 추가
			p_north.add(obj); //북쪽 패널에 부착 
		}
	}
	
	//관리자가 로그인시 호출될 메서드
	public void login() {
		la_login.setText("로그아웃");
		//프레임의 상단 제목에 로그인 한 관리자의 이름 출력 
		this.setTitle(loginForm.adminDTO.getName()+" 님 로그인 중");
		
		this.setVisible(true);//현재 메인프레임을 안보이게..
		loginForm.setVisible(false);//로그인폼 다시  안보이게
	}
	
	//관리자가 로그아웃시 호출될 메서드 
	public void logout() {
		la_login.setText("로그인");
		this.setTitle("");
		
		//관리자 정보가 들어있었던 DTO를 다시 초기화
		loginForm.adminDTO=null;
		
		this.setVisible(false);//현재 메인프레임을 다시 안보이게..
		loginForm.setVisible(true);//로그인폼 다시 등장
	}
	
	//페이지 전환처리 
	public void showHide(int n) { //보이게하고 싶은 index 남 넘겨받는다
		for(int i=0;i<pages.length;i++) {
			if(i==n) { //넘겨받은 매개변수와 i가 일치할때만 보이게..
				pages[i].setVisible(true); //보이게 처리
			}else {
				pages[i].setVisible(false);//안보이게 처리
			}
		}		
	}
	
	public static void main(String[] args) {
		System.out.println("실행");
		
		new AdminMain();
	}
}











