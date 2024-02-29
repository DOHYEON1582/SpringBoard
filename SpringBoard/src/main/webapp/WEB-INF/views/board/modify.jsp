<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>

${boardVO }

<h1>/board/modify.jsp</h1>
<h2>글 수정하기</h2>


<div class="box-header with-border">
	<h3 class="box-title">게시판 글 수정하기</h3>
</div>

<form role="form" method="post">
	<input type="hidden" name="bno" value="${boardVO.bno }">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">제 목</label> 
			<input type="text" class="form-control" name="title" id="exampleInputEmail1" value="${boardVO.title }">
		</div>

		<div class="form-group">
			<label>이 름</label> 
			<input type="text" class="form-control" name="writer" value="${boardVO.writer }">
		</div>
		
		<div class="form-group">
			<label>내 용</label>
			<textarea class="form-control" name="content" rows="3" >${boardVO.content }</textarea>
		</div>


	</div>

	<div class="box-footer">
		<button type="submit" class="btn btn-primary">글 수정</button>
	</div>
</form>


<%@include file="../include/footer.jsp"%>
