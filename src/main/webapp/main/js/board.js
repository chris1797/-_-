//----------광고 게시판쪽의 js---------
var token = $("#_csrf").attr("content");
var header = $("#_csrf_header").attr("content");

$(function() {
	$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
	});
});


// 게시글 insert
function save() {
	var form = $('#input_form')[0]
	var data = new FormData(form);

	$.ajax({
		url : '/board/'+categoryMac+'/new',
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		method : 'post',
		cache : false,
		data : data,
		dataType : 'json',
		success : function(res) {
			
			if(res.savednum!=0){
				alert('저장성공');	
				location.href='/board/'+categoryMac+'/'+res.savednum;
			};
		},
		error : function(xhr, status, err) {
			alert('Error:' + err);
		}
	});
	return false;
}(jQuery);
	

// 게시글 update
function edit() {
	var form = $('#edit_form')[0]
	var data = new FormData(form);
	var numMac=document.getElementById("numMac").value;
$.ajax({
	url:'/board/'+categoryMac,
	enctype: 'multipart/form-data',
	processData: false,
	contentType: false,
	method:'put',
	cache:false,
	data:data,
	dataType:'json',
	success:function(res){ //res는 object
			// alert(res.updated ? '수정 성공' : '수정실패'); //res object 안에있는 num
			location.href='/board/'+categoryMac+'/'+numMac;
	},
	error:function(xhr,status,err){
		alert("Error : " + err);
	}
});
return false;
}(jQuery);


// 게시글 delete & 게시글에 종속된 comment도 함께 삭제
function remove(){
	if(!confirm('해당 게시물 삭제하시겠어요?')) return;
$.ajax({
	url : '/board/'+categoryMac+'/'+numMac,
	method:'delete',
	cache:false,
	dataType:'json',
	success:function(res){ //res는 object
	
			alert(res.deleted && res.commetdeleted ? '삭제실패' : '삭제성공');
			location.href = '/board/'+categoryMac+'/listPage';
	},
	error:function(xhr,status,err){
		alert(err);
	}
});
return false;
}(jQuery);
	

// 댓글 insert
		function comment(){
			
		if(msg != ""){
			alert(msg);
			location.href='/board/'+categoryMac+'/detail/'+numMac;
		} else {
			$.ajax({
				url:'/board/comment',
				method:'post',
				cache:false,
				data:$('#comment_form').serialize(),
				dataType:'json',
				success:function(res){ //res는 object
						/* alert(res.commented ? '댓글작성성공' : '댓글작성실패'); */
						location.href='/board/'+categoryMac+'/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);
	

// 단일 댓글 delete
		function commentdelete(num){
		if(confirm("해당 댓글을 삭제하시겠습니까?")){
			$.ajax({
				url:'/board/comment/'+num,
				method:'delete',
				cache:false,
				dataType:'json',
				success:function(res){ //res는 object
						alert(res.deleted ? '댓글이 삭제되었습니다.' : '댓글 삭제가 취소되었습니다.');
						location.href='/board/'+categoryMac+'/'+numMac;
				},
				error:function(xhr,status,err){
					alert(err);
				}
			});
		}
		return false;
	}(jQuery);
	
	
// 단일 파일 delete
function filedelete(filenum){
	$.ajax({
		url : '/board/file/'+filenum,
		method:'delete',
		cache:false,
		dataType:'json',
		success:function(res){ //res는 object
				alert(res.filedeleted ? '파일삭제성공' : '파일삭제실패');
				location.href='/board/'+categoryMac+'/'+numMac+'/'+'editPage';
		},
		error:function(xhr,status,err){
			alert(err);
		}
	});
	return false;
}(jQuery);

// 단일 파일 download
function filedownload(filenum){
	$.ajax({
		method:'get',
		async: false,
		cache:false,
		contentType : 'application/json; charset=utf-8',
		success:function(res){
			if(idMac == ''){
				alert("로그인 후 다운로드 가능합니다.");
				return;
			} else {
				location.href='/board/file/'+filenum;
			}
		},
		error:function(xhr,status,err){
			alert(err);
		}
	});
	return;
}(jQuery);