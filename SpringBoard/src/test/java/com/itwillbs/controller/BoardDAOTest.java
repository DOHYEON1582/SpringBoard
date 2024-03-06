package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itwillbs.domain.BoardVO;
import com.itwillbs.domain.Criteria;
import com.itwillbs.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);
	
	// boardDAO 객체주입
	@Inject
	private BoardDAO bdao;
	
	// DAO 객체 주입 완료
	//@Test
	public void dao테스트() throws Exception{
		logger.debug("@@@@@@@@@@ bdao : "+bdao);
		
	}
	
	// 페이징 처리 동작
	@Test
	public void 페이징처리_리스트() throws Exception{
		
		Criteria cri = new Criteria(); // page 1, pageSize 10 
		
		List<BoardVO> boardList = bdao.boardListPageSelect(cri);
		
		for(BoardVO vo : boardList) {
			logger.debug(vo.getBno()+" : "+vo.getTitle());
		}
	}
	
	
	
	
	
	
	
	
	

}
