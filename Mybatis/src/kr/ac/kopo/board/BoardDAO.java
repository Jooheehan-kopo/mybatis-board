package kr.ac.kopo.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import kr.ac.kopo.MyConfig;

public class BoardDAO {

	private SqlSession session;

	public BoardDAO() {

		session = new MyConfig().getInstance();

	}

	public void work() {

		//selectOne2();
		//selectWhere();
		// selectNos();
		// insert();
		// selectAll();
		// selectOne();
		
		//delete();
		update();
	}
	
	private void update() {
		BoardVO vo = new BoardVO();
		vo.setNo(3);
		vo.setTitle("update");
		vo.setWriter("joohee");
		vo.setContent("it has got updated");
		
		int result = session.update("board.BoardDAO.update",vo);
		session.commit();
		
		System.out.println("update 완료");
		
	}
		
		
	
	
	private void delete() {
		
		BoardVO vo = new BoardVO();
		vo.setNo(2);
		
		int result = session.delete("board.BoardDAO.delete",vo);
		session.commit();//commit을 해줘야함.ㅎ #{no} 로 하는 거 잊지 말기.
		System.out.println(result);
		
	}

	// String으로 검색. statement객체 $로 구성해야함. prepared S => #붙이는거.
	private void selectWhere() {
//List<BoardVO> list = session.selectList("board.BoardDAO.selectWhere", "파일");

		// select, where if 를 통해서 파일이 있는것 아님 user가 작성한 것 나눠서 찾을수있음
		BoardVO vo = new BoardVO();
		vo.setTitle("파일");
		// vo.setWriter("user");
		List<BoardVO> list = session.selectList("board.BoardDAO.selectWhere2", vo);

		for (BoardVO board : list) {
			System.out.println(board);
		}
	}

	private void selectNos() {
		/*
		 * BoardVO board = new BoardVO(); board.setNos(new int[] {1,2,3,4,5, 19,20,21});
		 * //있는것만 나오게끔.
		 * 
		 * List<BoardVO> list = session.selectList("board.BoardDAO.selectNos",board);
		 */

		List<Integer> nos = new ArrayList<>();
		nos.add(1);
		nos.add(2);
		nos.add(3);
		nos.add(4);
		nos.add(5);

		List<BoardVO> list = session.selectList("board.BoardDAO.selectNos2", nos);

		for (BoardVO b : list) {
			System.out.println(b);
		}
	}

	private void selectOne2() {

		BoardVO board = new BoardVO();
		board.setNo(22);
		Map<String, Object> map = session.selectOne("board.BoardDAO.selectOne4", board);

		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + ":" + map.get(key));
		}
	}

	private void selectOne() {
		/*
		 * one의 경우 받는 값이 하나이기떄문에, int로 받아도 됨. #{no}안에는 아무거나 써도됨 그냥 걔만 받아옴 sql로 공통의 쿼리를
		 * 묶을 수 있음. 그걸 include로 넣어줌
		 */

		BoardVO board = new BoardVO();
		board.setNo(22);
		// BoardVO result = session.selectOne("board.BoardDAO.selectOne", board);
		// BoardVO result = session.selectOne("board.BoardDAO.selectOne2", 22);
		BoardVO result = session.selectOne("board.BoardDAO.selectOne3", 22);
		System.out.println(result);
	}

	private void selectAll() {
		// List<BoardVO> list = session.selectList("board.BoardDAO.selectAll");
		List<BoardVO> list = session.selectList("board.BoardDAO.selectAllMap");

		for (BoardVO board : list) {
			System.out.println(board);
		}
	}

	private void insert() {

		BoardVO board = new BoardVO();
		board.setTitle("객체로 삽입...");
		board.setWriter("홍길동아님");
		board.setContent("다시다시다시");

		session.insert("board.BoardDAO.newBoard", board); // namespace까지 포함하여 id 입력
		session.commit();

		System.out.println("삽입완료");
	}

}
