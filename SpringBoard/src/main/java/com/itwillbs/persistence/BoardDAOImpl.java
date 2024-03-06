package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
@Repository
public class BoardDAOImpl implements BoardDAO {

	// mapper 접근 가능한 객체(SQL실행객체) 주입
	@Inject
 	private SqlSession sql;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	private static final String NAMESPACE = "com.itwillbs.mapper.BoardMapper";
	
	@Override
	public void BoardCreate(BoardVO vo) throws Exception {
		logger.debug(" BoardCreate(BoardVO vo -> mapper 호출");
		
		sql.insert(NAMESPACE+".createBoard", vo);
		
		logger.debug(" mapper 실행완료 -> 서비스 이동");

	}

	@Override
	public List<BoardVO> boardListSelect() throws Exception {
		logger.debug(" boardListSelect() 호출");
		
		return sql.selectList(NAMESPACE+".selectBoardList");
	}

	@Override
	public BoardVO boardSelect(int bno) throws Exception {
		logger.debug(" boardSelect(int bno) 호출");
		
		return sql.selectOne(NAMESPACE+".getBoard",bno);
	}

	@Override
	public void boardViewCnt(int bno) throws Exception {
		logger.debug("boardViewCnt(int bno) 호출");
		
		sql.update(NAMESPACE+".updateViewCnt", bno);
		
	}

	@Override
	public void boardUpdate(BoardVO vo) throws Exception {
		logger.debug("boardUpdate() 호출");
		
		sql.update(NAMESPACE+".updateBoard", vo);
		
	}

	@Override
	public void boardDelete(int bno) throws Exception {
		logger.debug("boardDelete() 호출");
		
		sql.delete(NAMESPACE+".deleteBoard", bno);
		
	}

	@Override
	public List<BoardVO> boardListPageSelect(int page) throws Exception {
		logger.debug(" boardListPageSelect(int page) 호출");
		
		logger.debug(" 페이징처리 번호 : "+page);
		
		// 페이지 번호 -> SQL 사용될 인덱스로 전환
		// 1페이지 -> 0인덱스 / 2페이지 -> 10인덱스 
		// (page-1)*10
		page = (page - 1) * 10;
		
		
		return sql.selectList(NAMESPACE+".selectBoardListPage", page);
	}

	@Override
	public List<BoardVO> boardListPageSelect(Criteria cri) throws Exception {
		logger.debug("boardListPageSelect(Criteria cri) 호출");
		
		return sql.selectList(NAMESPACE+".selectBoardListCri", cri);
	}

	@Override
	public int boardCount() throws Exception {
		logger.debug("boardCount() 호출");
		
		return sql.selectOne(NAMESPACE+".totalCount");
	}
	
	
	
	
	
	
	
	

}// DAOImpl
