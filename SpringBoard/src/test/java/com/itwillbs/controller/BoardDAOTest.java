package com.itwillbs.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
	//@Test
	public void 페이징처리_리스트() throws Exception{
		
		Criteria cri = new Criteria(); // page 1, pageSize 10 
		
		List<BoardVO> boardList = bdao.boardListPageSelect(cri);
		
		for(BoardVO vo : boardList) {
			logger.debug(vo.getBno()+" : "+vo.getTitle());
		}
	}
	
	@Test
	public void 엑셀테스트() throws Exception{
	
	        try {
	            // 엑셀 파일을 읽을 FileInputStream 생성
	            FileInputStream file = new FileInputStream(new File("src/main/webapp/resources/StationInfo_2024-03-08.xls"));

	            // 워크북 객체 생성
	            Workbook workbook = WorkbookFactory.create(file);

	            // 첫 번째 시트를 가져옴
	            Sheet sheet = workbook.getSheetAt(0);

	            // 각 행을 반복하여 데이터 출력
	            for (Row row : sheet) {
	                for (Cell cell : row) {
	                    switch (cell.getCellType()) {
	                        case STRING:
	                            System.out.print(cell.getStringCellValue() + "\t");
	                            break;
	                        case NUMERIC:
	                            System.out.print(cell.getNumericCellValue() + "\t");
	                            break;
	                        case BOOLEAN:
	                            System.out.print(cell.getBooleanCellValue() + "\t");
	                            break;
	                        default:
	                            System.out.print("\t");
	                    }
	                }
	                System.out.println();
	            }

	            // 워크북과 파일 스트림 닫기
	            workbook.close();
	            file.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	
	
	
	
	
	
	

}
